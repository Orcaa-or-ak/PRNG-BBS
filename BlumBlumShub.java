import java.math.BigInteger;
import java.security.SecureRandom;

// Produces a sequence of numbers using the Blum Blum Shub algorithm
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
}
