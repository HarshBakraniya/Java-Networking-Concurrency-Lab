import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ExecutorService threadPool;

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        try( 
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            toClient.println("Hello from thread pool server" + clientSocket.getInetAddress());
            String line = fromClient.readLine();
            System.out.println("Server received: " + line + " | Thread: " + Thread.currentThread().getName());
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {

        int port = 8010;
        int poolSize = 100;
        Server server = new Server(poolSize);
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening st port " + port);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            server.threadPool.shutdown();
        }
    }
}

