import java.math.BigInteger;

interface NeedlesslyRecursive {

    static BigInteger a(BigInteger x, BigInteger y) {
        if (x == null || y == null || x.compareTo(BigInteger.ZERO) < 0 || y.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        if (x.equals(BigInteger.ZERO) && y.compareTo(BigInteger.ZERO) >= 0) {
            return y.add(BigInteger.ONE);
        }

        else if (x.compareTo(BigInteger.ZERO) > 0 && y.equals(BigInteger.ZERO)) {
            return a(x.subtract(BigInteger.ONE), BigInteger.ONE);
        }

        else {
            return a(x.subtract(BigInteger.ONE), a(x, y.subtract(BigInteger.ONE)));
        }
    }

    static String binaryString(long n) {
        if (n < 0) {
            return "-" + binaryString(Math.abs(n));
        }

        else if (n == 0 || n == 1) {
            String binaryNum = "";
            binaryNum = binaryNum + n;
            return binaryNum;
        }

        else {
            return binaryString(n / 2) + n % 2;
        }
    }

    static int log3(int n) {
        if (n <= 2) {
            return 0;
        }

        else {
            return 1 + log3(n / 3);
        }
    }

    static BigInteger power(BigInteger x, int n) {
        BigInteger uncompleteFinalNum;

        if (n == 0)
            return BigInteger.ONE;

        uncompleteFinalNum = power(x, n / 2);

        if (n % 2 == 0)
            return uncompleteFinalNum.multiply(uncompleteFinalNum);

        else {
            return x.multiply(uncompleteFinalNum.multiply(uncompleteFinalNum));
        }
    }
}