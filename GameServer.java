//networking
import java.net.Socket;
import java.net.ServerSocket;
//io
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//util.concurrent
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
//JSON library
// import javax.json.*;

class GameServer implements Runnable{

    public static void main(String[] args){
        try{
            GameServer srv = new GameServer(5739, 2);
            srv.run();
        }catch(IOException e){e.printStackTrace();}
    }


    private final ServerSocket serverSocket;
    private final ExecutorService exec;
    private final int maxNumClients;
    private int acceptedClients = 0;

    public GameServer(int port, int size) throws IOException{
        this.serverSocket = new ServerSocket(port);
        this.maxNumClients = size;
        this.exec = Executors.newFixedThreadPool(maxNumClients);
    }

    public void run(){
        Socket s;
        try{
            while(acceptedClients < maxNumClients){
                if((s = acceptedClient(serverSocket.accept())) != null){
                // if((s = testAccept(serverSocket.accept())) != null){
                    exec.execute(new AcceptHandler(s));
                    acceptedClients++;
                    System.out.print("acceptedClients: " + acceptedClients);
                }
            }
        }catch(IOException e){System.out.println("Something went horribly wrong. Oops!");}
        exec.shutdown();
    }

    // Socket testAccept(Socket s){return s;}
    Socket acceptedClient(Socket s){
        String inputLine;

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            inputLine = in.readLine();
            if (inputLine.equals("brinkman")){
                System.out.println("THEY'RE IN THE MAINFRAME");
                return s;
            }
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Incorrect Secret");
            return null;
        }catch(IOException e){return null;}
    }
}

class AcceptHandler implements Runnable{
    private final Socket socket;
    AcceptHandler(Socket socket){this.socket=socket;}

    private BufferedReader in;
    private PrintWriter out;
    private Player play;

    public void run(){
        grabIO();
        play = new Player(in, out);
        String inputLine;
        try{
            out.println("Hello!");
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                out.println(inputLine);
            }
        }catch(IOException e){e.printStackTrace();}
    }

    private void grabIO(){
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){e.printStackTrace();}
    }
}

