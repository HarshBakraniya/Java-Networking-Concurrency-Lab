import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 8010;

    // Total number of requests to send
    private static final int TOTAL_REQUESTS = 10000;

    // Requests per second
    private static final int RPS = 500;

    // Maximum number of client threads running at the same time
    private static final int CLIENT_THREADS = 500;

    private static final AtomicInteger success = new AtomicInteger(0);
    private static final AtomicInteger failed = new AtomicInteger(0);

    public static void sendRequest(int requestNumber) {

        try {
            InetAddress address = InetAddress.getByName(HOST);

            Socket socket = new Socket(address, PORT);

            try (
                PrintWriter toServer =
                    new PrintWriter(socket.getOutputStream(), true);

                BufferedReader fromServer =
                    new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
            ) {

                // Send request
                toServer.println(
                    "Request " + requestNumber +
                    " from client " +
                    socket.getLocalSocketAddress()
                );

                // Wait for server response
                String response = fromServer.readLine();

                if (response != null) {
                    success.incrementAndGet();

                    System.out.println(
                        "Request " + requestNumber +
                        " -> " + response
                    );
                }

            }

        } catch (IOException ex) {

            failed.incrementAndGet();

            System.out.println(
                "Request " + requestNumber +
                " -> FAILED: " + ex.getMessage()
            );
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Starting client...");
        System.out.println("Total requests : " + TOTAL_REQUESTS);
        System.out.println("Target RPS      : " + RPS);
        System.out.println("Client threads  : " + CLIENT_THREADS);

        ExecutorService clientPool =
            Executors.newFixedThreadPool(CLIENT_THREADS);

        ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

        long startTime = System.currentTimeMillis();

        /*
         * Calculate delay between requests.
         *
         * Example:
         *
         * RPS = 20
         *
         * 1000 / 20 = 50 ms
         *
         * So approximately one request starts every 50 ms.
         */
        long intervalMs = 1000L / RPS;

        AtomicInteger requestCounter = new AtomicInteger(0);

        ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(() -> {

            int requestNumber =
                requestCounter.incrementAndGet();

            if (requestNumber > TOTAL_REQUESTS) {
                return;
            }

            // Submit request to another thread
            clientPool.submit(() ->
                sendRequest(requestNumber)
            );

        }, 0, intervalMs, TimeUnit.MILLISECONDS);

        /*
         * Wait until all requests have been scheduled.
         */
        while (requestCounter.get() < TOTAL_REQUESTS) {
            Thread.sleep(10);
        }

        task.cancel(false);

        /*
         * Wait for all actual requests to finish.
         */
        clientPool.shutdown();

        clientPool.awaitTermination(
            5,
            TimeUnit.MINUTES
        );

        scheduler.shutdown();

        long endTime = System.currentTimeMillis();

        double seconds =
            (endTime - startTime) / 1000.0;

        System.out.println();
        System.out.println("==============================");
        System.out.println("Load Test Finished");
        System.out.println("==============================");

        System.out.println(
            "Total requests : " + TOTAL_REQUESTS
        );

        System.out.println(
            "Successful     : " + success.get()
        );

        System.out.println(
            "Failed         : " + failed.get()
        );

        System.out.println(
            "Time taken     : " + seconds + " seconds"
        );

        System.out.println(
            "Actual RPS     : " +
            (TOTAL_REQUESTS / seconds)
        );
    }
}