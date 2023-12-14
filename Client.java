import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;


    public Client(){
        try{
            System.out.println("Sending request ");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection Done ");

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
            Runnable r1 = ()->{
                System.out.println("reading started");
                System.out.println("Type exit to leave the chatRoom");
                while(true){
                    String message = null;
                    try{
                        message = br.readLine();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if(message.equals("exit")){
                        System.out.println("Client has stopped");
                        break;
                    }
                    System.out.println("Server:"+message);
                }
            };
            new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2 = ()->{
            while(true){
                try{
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args){
        System.out.println("This is client");
        new Client();
    }


}
