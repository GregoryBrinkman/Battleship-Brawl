import java.net.*;
import java.io.*;

class Server{
    public static void main(String [] args){
        try{
        ServerSocket serverSocket = new ServerSocket(5739);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                readInput(inputLine);
                if (inputLine.equals("Bye."))
                    break;
            }
        }catch(IOException e){e.printStackTrace();}
    }

    void readInput(String inputLine){
        try{
            System.out.println(Integer.parseInt(inputLine));
        }catch(NumberFormatException e){
            System.out.println("Input an Integer ya silly");
        }
    }
}
