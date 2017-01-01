import java.net.*;
import java.io.*;

class Server{

    static BufferedReader in;
    static PrintWriter out;

    public static void main(String [] args){
        while(true){
            socketInit();
            String inputLine;

            try{

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Hit!");
                    if (inputLine.equals("brinkman")){
                        System.out.println("Correct!");
                        out.println("Hello World!");
                    }
                    break;
                }

            }catch(IOException e){e.printStackTrace();}
        }
    }



    static void socketInit(){
        try{
            ServerSocket serverSocket = new ServerSocket(5739);
            Socket clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch(IOException e){e.printStackTrace();}
    }

}
