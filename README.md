# Token_Bucket

This is a simple implementation of a token bucket algorithm in JAVA. 

The token bucket algorithm is a rate limiting algorithm that can be used to control the rate of traffic sent or received on a network. The token bucket algorithm works by adding tokens to a bucket at a fixed rate. When a packet is sent or received, a token is removed from the bucket. If there are no tokens in the bucket, the packet is dropped or delayed.

The token bucket algorithm is commonly used in network traffic shaping and quality of service (QoS) implementations. It can be used to control the rate of traffic sent or received on a network, ensuring that the network does not become overloaded with traffic.

This implementation of the token bucket algorithm is a simple implementation that can be used to control the rate of traffic sent or received on a network. It is written in JAVA having 2 options to run the token bucket algorithm. first being the terminal and second being the UI.

## Running the program

There are two ways to run the program:

1. To use the token bucket algorithm, you can run the program using the following command:

```bash
make run
```

2. Else you can run the program using the following command:

```bash
javac terminal/TerminalMain.java src/*.java
java terminal.TerminalMain <parameter_1> <parameter_2> <parameter_3>
```

This will start the program and you can enter the following parameters:

- Bucket size: The maximum number of tokens that the bucket can hold.
- Token rate: The rate at which tokens are added to the bucket.
- Packet rate: The rate at which packets are sent or received.

Once you have entered these parameters, the program will simulate the token bucket algorithm and display the results.

