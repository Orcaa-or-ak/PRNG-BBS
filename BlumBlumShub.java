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
        int bitLength = 1024;

        // Seed value
        BigInteger seed = new BigInteger("3141592654");

        BlumBlumShub bbs = new BlumBlumShub(bitLength, seed);

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();

        // Generate and print 10 random numbers along with CPU, RAM, and time cost
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            BigInteger randomNumber = bbs.next();
            long endTime = System.nanoTime();
            double timeTakenMs = (endTime - startTime) / 1_000_000.0;
            double usedMemoryMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024.0 * 1024.0);

            System.out.println("Random Number: " + randomNumber);
            System.out.println("Bit Length: " + randomNumber.bitLength());
            System.out.println("Time taken: " + timeTakenMs + " ms");
            System.out.println("Used Memory: " + usedMemoryMb + " MB");
            System.out.println();
        }
    }
}
