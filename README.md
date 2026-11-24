<h1>Java Networking & Concurrency Lab</h1>

A hands-on Java project exploring TCP socket programming, client-server communication, multithreading, and thread-pool-based request handling.

This repository contains three different server implementations, each demonstrating a different approach to handling client connections. It also includes client applications to experiment with concurrent connections and observe server behaviour.

The main objective of this project is to understand how Java handles network communication and how different threading approaches affect server-side request processing.

<h2>Project Overview</h2>

The project explores three server architectures:

<h2>1. Single-Threaded Server</h2>

A basic TCP server that handles client connections sequentially.

Concepts explored:

- "ServerSocket" and "Socket"
- TCP client-server communication
- InputStream and OutputStream
- "PrintWriter" and "BufferedReader"
- Connection handling and resource management

<h2>2. Multithreaded Server</h2>

An improved implementation that creates a separate thread for each accepted client connection.

<h3>Concepts explored:</h3>

- Java "Thread" class
- "Runnable" interface
- Lambda expressions
- Concurrent client connections
- Thread creation and execution
- Automatic flushing in PrintWriter

<h2>3. Thread-Pool Server</h2>

A server implementation using Java's "ExecutorService" and a fixed thread pool to manage client connections.

<h3>Concepts explored:</h3>

- "ExecutorService"
- "Executors.newFixedThreadPool()"
- Task submission using "execute()"
- Worker thread management
- Thread reuse
- Try-with-resources
- Concurrent request processing

Unlike the multithreaded implementation, this approach reuses a fixed number of worker threads instead of creating a new thread for every incoming connection.

<h3>Client-Side Request Testing</h3>

The ThreadPool directory also contains an experimental client designed to generate multiple requests and observe server behaviour under concurrent workloads.

<h2>Current configuration:</h2>

<table>
  <tr>
    <th>Configuration</th>
    <th>Value</th>
  </tr>
  <tr>
    <td>Total requests</td>
    <td>10,000</td>
  </tr>
  <tr>
    <td>Target request rate</td>
    <td>500 requests/second</td>
  </tr>
  <tr>
    <td>Client worker threads</td>
    <td>500</td>
  </tr>
  <tr>
    <td>Request scheduling</td>
    <td>ScheduledExecutorService</td>
  </tr>
</table>

<h2>The client uses two separate executors:</h2>

- ScheduledExecutorService: Schedules request submissions at configured intervals.
- ExecutorService: Executes individual client requests using a fixed thread pool.

It also uses "AtomicInteger" to track successful and failed requests.

<h2>At the end of execution, it displays:</h2>

- Total requests
- Successful requests
- Failed requests
- Total execution time
- Calculated actual requests per second

This is an experimental learning tool for observing concurrency and request handling, not a production-grade benchmarking framework.

<h2>Project Structure</h2>

The repository is organized into three directories, each containing its respective server and client implementation.

- SingleThreaded
  
  - "Server.java"
  - "Client.java"

- MultiThreaded
  
  - "Server.java"
  - "Client.java"

- ThreadPool
  
  - "Server.java"
  - "Client.java"

- "README.md" — Project documentation.

<h2>Architecture Comparison</h2>

<table>
<tr>
  <th>Feature</th>
  <th>Single-Threaded</th>
  <th>Multithreaded</th>
  <th>Thread Pool</th>
</tr>
<tr>
  <td>Client handling</td>
  <td>Sequential</td>
  <td>Concurrent</td>
  <td>Concurrent</td>
</tr>
<tr>
  <td>Thread creation</td>
  <td>Main server thread</td>
  <td>New thread per client</td>
  <td>Reusable worker threads</td>
</tr>
<tr>
  <td>Thread management</td>
  <td>Basic</td>
  <td>Manual</td>
  <td>ExecutorService</td>
</tr>
<tr>
  <td>Resource management</td>
  <td>Simple</td>
  <td>Can become expensive</td>
  <td>Controlled worker count</td>
</tr>
<tr>
  <td>Scalability</td>
  <td>Limited</td>
  <td>Depends on thread resources</td>
  <td>Better controlled</td>
</tr>
</table>

<h2>Technologies Used</h2>

- Language: Java
- Networking: Java Socket API
- Concurrency: Java Threads and Runnable
- Thread Management: ExecutorService
- Scheduling: ScheduledExecutorService
- Thread Safety: AtomicInteger
- I/O: Java I/O Streams

<h2>Learning Objectives</h2>

Through these implementations, I am exploring:

- How TCP socket communication works in Java.
- How servers accept and handle client connections.
- The differences between sequential and concurrent request processing.
- How Java threads execute independent tasks.
- How ExecutorService manages and reuses worker threads.
- How scheduled tasks can generate requests at controlled intervals.
- How atomic variables help manage shared counters across multiple threads.
- How I/O operations behave during concurrent client-server communication.

<h2>Future Improvements</h2>

Some areas I intend to explore and improve:

- ThreadPoolExecutor configuration and bounded task queues.
- Synchronization and thread safety.
- Race conditions and deadlocks.
- Callable and Future.
- Better exception handling and graceful shutdown.
- Java NIO and non-blocking communication.
- More detailed performance comparisons between server architectures.

<h2>About This Project</h2>

This project is part of my ongoing Java backend development learning journey.

Most implementations originated from tutorial-based learning, followed by my own modifications and experiments to better understand the concepts and their behaviour.

The ThreadPool client was created with AI assistance and used as an experimental tool for testing request handling and observing server behaviour.

The focus of this repository is practical learning, experimentation, and developing a deeper understanding of Java networking and concurrency.
