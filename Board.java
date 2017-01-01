//make so you can't guess previous guesses

enum Shiptype {Carrier, Battleship, Cruiser, Submarine, Destroyer};

class Board{
    int [][] board;
    int [] guesses;
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

    }

    void printBoard(){
        for(int i = 0; i< 10; i++){
            for(int j = 0; j<10; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    String guess(int location){
        int guess = board[location/10][location%10];
        if(guess == 0){
            return "Miss!";
        }else{
            for(Ship s: shipArray)
                if(s.getMarker() == guess){
                    s.hit();
                    if(s.isSunk()){
                        removeShip(s);
                        return "Hit! You sunk my " + s.getType() + "!";
                    }
                }
            return "Hit!";
        }
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

    boolean finished(){
        for(int i = 0; i< 10; i++)
            for(int j = 0; j<10; j++)
                if(board[i][j] != 0)
                    return false;
        return true;
    }

    boolean shipsPlaced(){
        for(Ship s: shipArray)
            if(!s.isPlaced())
                return false;
        return true;
    }

}
