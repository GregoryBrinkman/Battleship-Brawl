import java.util.*;

class Player{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Player p = new Player();
        p.board.printBoard();
        while(!p.board.finished()){
            System.out.println(p.board.guess(scan.nextInt()));
        }
    }

    Board board;

    public Player(){
        this.board = new Board();
    }

}
