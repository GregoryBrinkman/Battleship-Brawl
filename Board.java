enum Shiptype {Carrier, Battleship, Cruiser, Submarine, Destroyer};

class Board{
    char [][] board;
    Ship [] shipArray;

    public Board(){
        int index = 0;
        shipArray = new Ship[5];
        for(Shiptype s: Shiptype.values())
            shipArray[index++] = new Ship(s);

        board = new char[10][10];
        for(int i = 0; i< 10; i++)
            for(int j = 0; j<10; j++)
                board[i][j] = '0';

        placeShip(shipArray[0], 39, false);
        placeShip(shipArray[1], 98, false);
        placeShip(shipArray[2], 78, false);
        placeShip(shipArray[3], 68, false);
        placeShip(shipArray[4], 98, true);
    }

    void printBoard(){
        for(int i = 0; i< 10; i++){
            for(int j = 0; j<10; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }

    }

    boolean placeShip(Ship s, int start, boolean horizontal){
        //add direction later, just horizontal for now

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
            if(overlap(i)){
                System.out.println("Ships on ships on ship, Underhulling is bad");
                return s.setPlaced(false);
            }
        }

        //checks passed, time to place a ship!
        for(int i: places){
            board[i/10][i%10] = 'X';
        }

        System.out.println();
        return s.setPlaced(true);
    }

    boolean overlap(int location){
        System.out.println(location);
        if(board[location/10][location%10] == '0')
            return false;
        return true;
    }

    private class Ship{
        private Shiptype type;
        private int length;
        private boolean placed;

        public Ship(Shiptype ship){
            this.type = ship;
            placed = false;
            shipDetails();
            System.out.print(getType() + " ");
            System.out.print(getLength() + " ");
            System.out.print(isPlaced() + " ");
            System.out.println();
        }

        boolean setPlaced(boolean placed){this.placed = placed; return placed;}

        Shiptype getType(){return this.type;}
        int getLength(){return this.length;}
        boolean isPlaced(){return this.placed;}

        void shipDetails(){
            switch(type){
            case Carrier:
                this.length = 5;
                break;
            case Battleship:
                this.length = 4;
                break;
            case Cruiser:
                this.length = 3;
                break;
            case Submarine:
                this.length = 3;
                break;
            case Destroyer:
                this.length = 2;
                break;
            default:
                System.out.println("Ummm something went wrong");
                break;
            }
        }
    }
}
