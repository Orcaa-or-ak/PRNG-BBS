import java.math.BigInteger;
import java.security.SecureRandom;

public class BlumBlumShub {
    private BigInteger p;
    private BigInteger q;
    private BigInteger m;
    private BigInteger state;

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

    public BigInteger next() {
        state = state.modPow(BigInteger.valueOf(2), m);
        return state;
    }

    public static void main(String[] args) {
        int bitLength = 2048;

        // Seed value
        BigInteger seed = new BigInteger("3141592654");

        BlumBlumShub bbs = new BlumBlumShub(bitLength, seed);

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();

        // Generate and print 10 random numbers along with CPU, RAM, and time cost
        for (int i = 0; i < 10; i++) {
            // Measure start time and memory usage
            long startTime = System.nanoTime();
            long startMemory = runtime.totalMemory() - runtime.freeMemory();

            // Generate 1000 random numbers
            for (int z = 0; z < 1000; z++) {
                bbs.next();
            }
            
            // Measure end time and memory usage
            long endTime = System.nanoTime();
            long endMemory = runtime.totalMemory() - runtime.freeMemory();
            
            // Calculate total time taken and memory used
            double totalTimeTakenMs = (endTime - startTime) / 1_000_000.0;
            long totalUsedMemoryBytes = endMemory - startMemory;

            // Calculate average time and memory used per number
            double averageTimeTakenMs = totalTimeTakenMs / 1000;
            double averageUsedMemoryBytes = totalUsedMemoryBytes / 1000.0;

            // Generate one final random number to print
            BigInteger randomNumber = bbs.next();

            System.out.println("Random Number: " + randomNumber);
            System.out.println("Bit Length: " + randomNumber.bitLength());
            System.out.println("Average Time taken: " + averageTimeTakenMs + " ms");
            System.out.println("Average Used Memory: " + averageUsedMemoryBytes + " Bytes");
            System.out.println();
        }
    }
}
