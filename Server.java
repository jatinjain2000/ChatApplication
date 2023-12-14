import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Socket socket;
    ServerSocket server;
    BufferedReader br;
    PrintWriter out;

    public Server(){

        try{
            server = new ServerSocket(7777);
            System.out.println("Ready to accept connection");
            System.out.println("waiting..");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void startReading(){
        //thread to read data from the client
        Runnable r1= ()->{
            System.out.println("reading started");
            while(true){
                String message = null;
                try {
                    message = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(message.equals("exit")){
                    System.out.println("client has stopped");
                    break;
                }
                System.out.println("Client:"+message);
            }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread to write data and send to client
        Runnable r2= ()->{
            while (true) {
                try{
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                }
                catch (Exception e){
                        e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args){
        System.out.println("This is client");
        new Server();
    }

}

