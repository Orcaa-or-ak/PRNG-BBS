# Blum Blum Shub (BBS) Random Number Generator

This project demonstrates the implementation of a Blum Blum Shub (BBS) random number generator in Java.

## Overview

The BBS algorithm is defined by the recurrence relation:

$$
X_{n+1} = X_n^2 \mod m
$$

Where:
- $X$ is the sequence of pseudorandom values.
- $X_0$ is the seed or starting value.
- $X_{n+1}$ is the next number in the sequence.
- $m$ is the product of two large prime numbers $p$ and $q$.

## Implementation

The Java implementation of the BBS is encapsulated in a class named `BlumBlumShub`. The BigInteger class of the java.math package is used to handle large integers, and the SecureRandom class of the java.security package is used to generate strong numbers. These two classes are suitable for cryptographic purposes.

### Class: `BlumBlumShub`

- **Constructor**:
  ```java
  public BlumBlumShub(int bitLength, BigInteger seed) {
        SecureRandom secureRandom = new SecureRandom();

        p = BigInteger.probablePrime(bitLength / 2, secureRandom);
        q = BigInteger.probablePrime(bitLength / 2, secureRandom);

        while (!p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
            p = BigInteger.probablePrime(bitLength / 2, secureRandom);
        }

        while (!q.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
            q = BigInteger.probablePrime(bitLength / 2, secureRandom);
        }

        m = p.multiply(q);

        // Initialize state
        state = seed.mod(m);
    }
  ```

- **Method**:
  - `public BigInteger next()`: Computes and returns the next number in the sequence using the BBS formula.

### Main Method

The `main` method demonstrates how to use the `BlumBlumShub` class to generate and print 10 pseudorandom numbers.

```java
public static void main(String[] args) {
        int bitLength = 512;

        // Seed value
        BigInteger seed = new BigInteger("3141592654");

        BlumBlumShub bbs = new BlumBlumShub(bitLength, seed);

        // Generate and print 10 random numbers
        for (int i = 0; i < 10; i++) {
            System.out.println(bbs.next());
            System.out.println();
        }
    }
```

This will generate and print 10 pseudorandom numbers based on the BBS algorithm.
