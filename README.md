# Token_Bucket

This is a simple implementation of a token bucket algorithm in Python. The token bucket algorithm is a rate limiting algorithm that can be used to control the rate of traffic sent or received on a network. The token bucket algorithm works by adding tokens to a bucket at a fixed rate. When a packet is sent or received, a token is removed from the bucket. If there are no tokens in the bucket, the packet is dropped or delayed.

The token bucket algorithm is commonly used in network traffic shaping and quality of service (QoS) implementations. It can be used to control the rate of traffic sent or received on a network, ensuring that the network does not become overloaded with traffic.

This implementation of the token bucket algorithm is a simple Python class that can be used to control the rate of traffic sent or received on a network. The class provides methods for adding tokens to the bucket, removing tokens from the bucket, and checking if a packet can be sent or received based on the number of tokens in the bucket.

## Usage

To use the token bucket algorithm, create an instance of the `TokenBucket` class and specify the rate at which tokens should be added to the bucket and the maximum number of tokens that the bucket can hold. For example:

```python
bucket = TokenBucket(1, 10)
```

This will create a token bucket with a rate of 1 token per second and a maximum capacity of 10 tokens. To add tokens to the bucket, call the `add_tokens` method with the number of tokens to add. For example:

```python
bucket.add_tokens(5)
```

This will add 5 tokens to the bucket. To check if a packet can be sent or received, call the `can_consume` method with the number of tokens required. For example:

```python
if bucket.can_consume(3):
    print("Packet sent")
else:
    print("Packet dropped")
```

This will check if there are at least 3 tokens in the bucket. If there are, the packet is sent. If there are not, the packet is dropped.
