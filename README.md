#Java Networking & Concurrency Lab

A hands-on Java project exploring TCP socket programming, client-server communication, multithreading, and thread-pool-based request handling.

This repository contains three different server implementations, each demonstrating a different approach to handling client connections. It also includes client applications to experiment with concurrent connections and observe server behaviour.

The main objective of this project is to understand how Java handles network communication and how different threading approaches affect server-side request processing.

Project Overview

The project explores three server architectures:

1. Single-Threaded Server

A basic TCP server that handles client connections sequentially.

Concepts explored:

- "ServerSocket" and "Socket"
- TCP client-server communication
- InputStream and OutputStream
- "PrintWriter" and "BufferedReader"
- Connection handling and resource management

2. Multithreaded Server

An improved implementation that creates a separate thread for each accepted client connection.

Concepts explored:

- Java "Thread" class
- "Runnable" interface
- Lambda expressions
- Concurrent client connections
- Thread creation and execution
- Automatic flushing in PrintWriter

3. Thread-Pool Server

A server implementation using Java's "ExecutorService" and a fixed thread pool to manage client connections.

Concepts explored:

- "ExecutorService"
- "Executors.newFixedThreadPool()"
- Task submission using "execute()"
- Worker thread management
- Thread reuse
- Try-with-resources
- Concurrent request processing

Unlike the multithreaded implementation, this approach reuses a fixed number of worker threads instead of creating a new thread for every incoming connection.

Client-Side Request Testing

The ThreadPool directory also contains an experimental client designed to generate multiple requests and observe server behaviour under concurrent workloads.

Current configuration:

Configuration| Value
Total requests| 10,000
Target request rate| 500 requests/second
Client worker threads| 500
Request scheduling| ScheduledExecutorService

The client uses two separate executors:

- ScheduledExecutorService: Schedules request submissions at configured intervals.
- ExecutorService: Executes individual client requests using a fixed thread pool.

It also uses "AtomicInteger" to track successful and failed requests.

At the end of execution, it displays:

- Total requests
- Successful requests
- Failed requests
- Total execution time
- Calculated actual requests per second

This is an experimental learning tool for observing concurrency and request handling, not a production-grade benchmarking framework.

Project Structure

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

Architecture Comparison

Feature| Single-Threaded| Multithreaded| Thread Pool
Client handling| Sequential| Concurrent| Concurrent
Thread creation| Main server thread| New thread per client| Reusable worker threads
Thread management| Basic| Manual| ExecutorService
Resource management| Simple| Can become expensive| Controlled worker count
Scalability| Limited| Depends on thread resources| Better controlled

Technologies Used

- Language: Java
- Networking: Java Socket API
- Concurrency: Java Threads and Runnable
- Thread Management: ExecutorService
- Scheduling: ScheduledExecutorService
- Thread Safety: AtomicInteger
- I/O: Java I/O Streams

Learning Objectives

Through these implementations, I am exploring:

- How TCP socket communication works in Java.
- How servers accept and handle client connections.
- The differences between sequential and concurrent request processing.
- How Java threads execute independent tasks.
- How ExecutorService manages and reuses worker threads.
- How scheduled tasks can generate requests at controlled intervals.
- How atomic variables help manage shared counters across multiple threads.
- How I/O operations behave during concurrent client-server communication.

Future Improvements

Some areas I intend to explore and improve:

- ThreadPoolExecutor configuration and bounded task queues.
- Synchronization and thread safety.
- Race conditions and deadlocks.
- Callable and Future.
- Better exception handling and graceful shutdown.
- Java NIO and non-blocking communication.
- More detailed performance comparisons between server architectures.

About This Project

This project is part of my ongoing Java backend development learning journey.

Most implementations originated from tutorial-based learning, followed by my own modifications and experiments to better understand the concepts and their behaviour.

The ThreadPool client was created with AI assistance and used as an experimental tool for testing request handling and observing server behaviour.

The focus of this repository is practical learning, experimentation, and developing a deeper understanding of Java networking and concurrency.
