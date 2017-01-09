package com.gb.shipApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
// import com.google.gson.*;
import java.util.*;
import java.net.*;
import java.io.*;

class Test{
    Board board;
    Scanner scan;

    public Test(String ip, int port){
        socketInit(ip, port);
        this.scan = new Scanner(System.in);
        this.board = new Board();
    }

    void socketInit(String ip, int port){
        try{
            Socket sock = new Socket(ip, port);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println(in.readLine());
            }
        }catch(Exception e){e.printStackTrace();}
    }

    void setShips(String test){
        board.placeShip(checkShipName("Carrier"), 0, 'v');
        board.placeShip(checkShipName("Battleship"), 1, 'v');
        board.placeShip(checkShipName("Cruiser"), 2, 'v');
        board.placeShip(checkShipName("Submarine"), 3, 'v');
        board.placeShip(checkShipName("Destroyer"), 4, 'v');
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
        //connect message
        //if reset and ready, enter loop
        OutputStreamWriter guessWrite = null;
        OutputStreamWriter conWrite = null;
        OutputStreamWriter resWrite = null;
            try{
                conWrite  = new OutputStreamWriter(new FileOutputStream("Connect.json"), "UTF-8");
        guessWrite = new OutputStreamWriter(new FileOutputStream("Guess.json"), "UTF-8");
        resWrite = new OutputStreamWriter(new FileOutputStream("Response.json"), "UTF-8");
        }catch(Exception e){}

        connect(conWrite, true, true);
        String str;
        int guess;
        while(true){ //not gameover
            //guess
            //read response

            while(true){
                try{
                    str = scan.next();
                    guess = Integer.parseInt(str);
                    if(guess >=  0 && guess < 100)
                        break;
                    System.out.print("Integer must be between 0-99: ");
                }catch(Exception e){
                    System.out.println("Input an integer, silly");
                }
            }
            guess(guessWrite, guess);


            //gameover? Break.

            //read guess
            //respond
            respond(resWrite, "Hit!", "", false);

            //gameover? Break.
            break;
        }

        //reset?? call gameloop()
        try{
        resWrite.close();
        guessWrite.close();
        conWrite.close();
        }catch(Exception e){}

    }

    void respond(OutputStreamWriter write, String hit, String sunk, boolean gameover){
        Response msg = new Response(hit, sunk, gameover);
        try{
            Gson g = new GsonBuilder().create();
            System.out.println(msg);
            String str = g.toJson(msg);
            String retStr = str + "\n";
            System.out.print(retStr);
            System.out.println(str);
            write.append(retStr);
        }catch(Exception e){}
    }


    void guess(OutputStreamWriter write, int num){
        Guess guess = new Guess(num);
        try{
            Gson g = new GsonBuilder().create();
            System.out.println(guess);
            String str = g.toJson(guess);
            String retStr = str + "\n";
            System.out.print(retStr);
            System.out.println(str);
            write.append(retStr);
        }catch(Exception e){}
    }


    void connect(OutputStreamWriter write, boolean reset, boolean ready){
        Connection con = new Connection(ready, reset);
        try{
            Gson g = new GsonBuilder().create();
            System.out.println(con);
            String str = g.toJson(con);
            String retStr = str + "\n";
            System.out.print(retStr);
            System.out.println(str);
            write.append(retStr);
        }catch(Exception e){}
    }

}



//Json classes
class Guess{
    int guess;

    public Guess(int x){
        this.guess = x;
    }
    @Override
    public String toString() {
        return "" + guess;
    }
}
class Response{
    String response;
    String sunk;
    boolean gameover;

    public Response(String res, String ship, boolean over){
        this.response = res;
        this.sunk = ship;
        this.gameover = over;
    }
    @Override
    public String toString() {
        return response + " - " + sunk + " - " + gameover;
    }
}

class Connection{
    boolean reset_requested;
    boolean ready_to_play;

    public Connection(boolean reset, boolean ready){
        this.reset_requested = reset;
        this.ready_to_play = ready;
    }

    @Override
    public String toString() {
        return reset_requested + " - " + ready_to_play;
    }
}

        // try{
        //     Gson g = new GsonBuilder().create();
        //     Guess obj = new Guess(20);
        //     System.out.println(obj);
        //     OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("Guess.json"), "UTF-8");
        //     g.toJson(obj, write);
        //     write.append("\n");
        //     String jsonStr = g.toJson(obj);
        //     System.out.println(jsonStr);
        //     Guess guess = g.fromJson(jsonStr, Guess.class);
        //     System.out.println(guess);
        //     obj = new Guess(99);
        //     System.out.println(obj);
        //     // OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("Guess.json"), "UTF-8");
        //     g.toJson(obj, write);
        //     jsonStr = g.toJson(obj);
        //     System.out.println(jsonStr);
        //     guess = g.fromJson(jsonStr, Guess.class);
        //     System.out.println(guess);
        //     write.append("\n");
        //     write.close();
        // }catch(Exception e){System.out.println("OOPS!"); e.printStackTrace();}
