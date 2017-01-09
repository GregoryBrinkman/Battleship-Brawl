package com.gb.shipApp;
import java.util.*;
import java.io.*;

class Player{

    private Board board;
    private BufferedReader in;
    private PrintWriter out;

    public Player(BufferedReader in, PrintWriter out){
        this.board = new Board();
        this.in = in;
        this.out = out;
        out.println("Welcome!");
        // setShips();
    }

    String input(){
        String str;
        try{
            str = in.readLine();
        }catch(Exception e){str = "";}
        return str;
    }

    Ship checkShipName(String str){
        for(int i = 0; i < board.shipArray.length; i++)
            if(str.equals(board.shipArray[i].getType().toString()))
                return board.shipArray[i];
        return null;
    }

}

    // void setShips(){
    //     Ship s;
    //     String str, name;
    //     int start;
    //     char direction;

    //     board.printBoard();
    //     do{
    //         out.println("Ships are Carrier, Battleship, Cruiser, Submarine, and Destroyer");
    //         out.println("Enter Ship to place: ");
    //         while(true){
    //             str = input();
    //             s = checkShipName(str);
    //             System.out.println(s);
    //             if(s != null)
    //                 break;
    //             out.println("Incorrect Ship Name, Try Again: ");
    //         }
    //         name = str;
    //         out.println("Enter starting location (00-99): ");
    //         while(true){
    //             try{
    //                 str = input();
    //                 start = Integer.parseInt(str);
    //                 if(start >=  0 && start < 100)
    //                     break;
    //                 out.println("Integer must be between 0-99: ");
    //             }catch(Exception e){
    //                 out.println("Input an integer, silly");
    //             }
    //         }

    //         out.println("Enter direction ('h'/'v'): ");
    //         while(true){
    //             try{
    //                 str = input();
    //                 direction = str.charAt(0);
    //                 if(direction == 'h' || direction == 'v' || direction == 'H' || direction == 'V')
    //                     break;
    //                 out.println("Character start with an 'h' or a 'v': ");
    //             }catch(Exception e){
    //                 out.println("Input a character, silly");
    //             }
    //         }
    //         if(board.placeShip(s, start, direction)){
    //             out.println(s.getType() + " placed. Denoted by " + s.getMarker());
    //         }
    //         else
    //             out.println(s.getType() + " not placed");
    //     }while(!board.shipsPlaced());
    // }

