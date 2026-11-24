import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);  // true is used for auto flush 
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                toClient.println("Hello from the multithreaded server...");
                String line = fromClient.readLine();
                System.out.println("Message received from client: " + line);
                toClient.close();
                fromClient.close();
                clientSocket.close();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        };
    }
    public static void main(String args[]) {
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening at port " + port);
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
