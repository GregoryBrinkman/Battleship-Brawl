enum Shiptype {Carrier, Battleship, Cruiser, Submarine, Destroyer};

class Board{
    int [][] board;
    Ship [] shipArray;

    public Board(){
        int index = 0;
        shipArray = new Ship[5];
        for(Shiptype s: Shiptype.values())
            shipArray[index++] = new Ship(s);

        board = new int[10][10];
        for(int i = 0; i< 10; i++)
            for(int j = 0; j<10; j++)
                board[i][j] = 0;

        placeShip(shipArray[0], 39, 'V');
        placeShip(shipArray[1], 1, 'H');
        placeShip(shipArray[1], 98, 'V');
        placeShip(shipArray[2], 78, 'v');
        placeShip(shipArray[3], 68, 'v');
        placeShip(shipArray[4], 98, 'h');

        printBoard();
        removeShip(shipArray[0]);
    }

    void printBoard(){
        for(int i = 0; i< 10; i++){
            for(int j = 0; j<10; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    boolean placeShip(Ship s, int start, char direction){
        if(s.isPlaced()){
            System.out.println("ship is placed already");
            return false;
        }
        boolean horizontal;
        if(Character.toUpperCase(direction) == 'H')
            horizontal = true;
        else if(Character.toUpperCase(direction) == 'V')
            horizontal = false;
        else{
            System.out.println("Unrecognized direction");
            return false;
        }

        int [] places = new int[s.getLength()];
        if(horizontal)
            for(int i = 0; i < places.length; i++)
                places[i] = start + i;
        else
            for(int i = 0; i < places.length; i++)
                places[i] = start + i*10;

        int line;
        if(horizontal)
            line = start/10;
        else
            line = start%10;

        //checks
        for(int i: places){
            if(i > 99){
                System.out.println("Too high, sober up bro");
                return s.setPlaced(false);
            }
            if(horizontal){
                if(line != i/10){
                    System.out.println("Ship wrapped around the world, generally a bad thing");
                    return s.setPlaced(false);
                }
            }else{
                if(line != i%10){
                    System.out.println("Ship wrapped around the world, generally a bad thing");
                    return s.setPlaced(false);
                }
            }
            if(occupied(i)){
                System.out.println("Ships on ships on ship, Underhulling is bad");
                return s.setPlaced(false);
            }
        }

        //checks passed, time to place a ship!
        int marker = s.getMarker();
        for(int i: places){
            board[i/10][i%10] = marker;
        }
        return s.setPlaced(true);
    }

    boolean removeShip(Ship s){
        if(!s.isPlaced()){
            System.out.println("ship is not placed");
            return false;
        }

        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                if(board[i][j] == s.getMarker())
                    board[i][j] = 0;

        return !s.setPlaced(false);
    }

    boolean occupied(int location){
        if(board[location/10][location%10] == 0)
            return false;
        return true;
    }

    private class Ship{
        private Shiptype type;
        private int length;
        private int marker;
        private int hits;
        private boolean placed;
        private boolean sunk;

        public Ship(Shiptype ship){
            this.type = ship;
            this.placed = false;
            this.sunk = false;
            this.hits = 0;
            shipDetails();
        }

        boolean setPlaced(boolean placed){this.placed = placed; return placed;}
        Shiptype getType(){return this.type;}
        int getMarker(){return this.marker;}
        int getLength(){return this.length;}
        int getHits(){return this.hits;}
        boolean isPlaced(){return this.placed;}
        boolean isSunk(){return this.sunk;}
        void hit(){this.hits++; this.sunk = (getHits() == getLength() ? true: false);}

        void shipDetails(){
            switch(type){
            case Carrier:
                this.length = 5;
                this.marker = 5;
                break;
            case Battleship:
                this.length = 4;
                this.marker = 4;
                break;
            case Cruiser:
                this.length = 3;
                this.marker = 3;
                break;
            case Submarine:
                this.length = 3;
                this.marker = 2;
                break;
            case Destroyer:
                this.length = 2;
                this.marker = 1;
                break;
            default:
                System.out.println("Ummm something went wrong");
                break;
            }
        }
    }
}
