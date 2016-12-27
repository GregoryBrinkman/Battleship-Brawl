class Player{
    public static void main(String[] args){
        Player p = new Player();
        p.board.printBoard();
    }

    Board board;
    // Ship 

    public Player(){
        this.board = new Board();
    }

}
