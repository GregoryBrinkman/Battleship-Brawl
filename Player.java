import java.util.*;
import java.net.*;
import java.io.*;

class Player{
    public static void main(String[] args){
        Player p = new Player("127.0.0.1");
        p.setShips();
        p.gameLoop();
    }

    Board board;
    Scanner scan;

    public Player(String ip){
        socketInit(ip);
        scan = new Scanner(System.in);
        this.board = new Board();
    }

    void socketInit(String ip){
        try{
        Socket sock = new Socket("127.0.0.1", 5739);
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        // String userInput;
        // while ((userInput = stdIn.readLine()) != null) {
        //     out.println(userInput);
        // }
        }catch(Exception e){e.printStackTrace();}
    }

    void setShips(){
        Ship s;
        String str, name;
        int start;
        char direction;

        board.printBoard();
        while(!board.shipsPlaced()){
            System.out.println("Ships are Carrier, Battleship, Cruiser, Submarine, and Destroyer");
            System.out.print("Enter Ship to place: ");
            while(true){
                str = scan.next();
                s = checkShipName(str);
                if(s != null)
                    break;
                System.out.print("Incorrect Ship Name, Try Again: ");
            }
            name = str;
            System.out.print("Enter starting location (00-99): ");
            while(true){
                try{
                    str = scan.next();
                    start = Integer.parseInt(str);
                    if(start >=  0 && start < 100)
                        break;
                    System.out.print("Integer must be between 0-99: ");
                }catch(Exception e){
                    System.out.println("Input an integer, silly");
                }
            }

            System.out.print("Enter direction ('h'/'v'): ");
            while(true){
                try{
                    str = scan.next();
                    direction = str.charAt(0);
                    if(direction == 'h' || direction == 'v' || direction == 'H' || direction == 'V')
                        break;
                    System.out.print("Character start with an 'h' or a 'v': ");
                }catch(Exception e){
                    System.out.println("Input a character, silly");
                }
            }
            if(board.placeShip(s, start, direction)){
                board.printBoard();
                System.out.println(s.getType() + " placed. Denoted by " + s.getMarker());
            }
            else
                System.out.println(s.getType() + " not placed");
        }
    }

    Ship checkShipName(String str){
        for(int i = 0; i < board.shipArray.length; i++)
            if(str.equals(board.shipArray[i].getType().toString()))
                return board.shipArray[i];
        return null;
    }

   void gameLoop(){
        while(!board.finished()){

        }
    }
}
