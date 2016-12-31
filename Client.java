import java.net.*;
import java.io.*;

class Client{
    public static void main(String [] args){
        try{
            Socket sock = new Socket("127.0.0.1", 5739);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }
        }catch(IOException e){e.printStackTrace();}
    }
}
