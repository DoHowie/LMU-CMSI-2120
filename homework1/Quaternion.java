import java.util.List;

public record Quaternion(double a, double b, double c, double d) {

    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);

    public Quaternion {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Coefficients cannot be NaN");
        }
    }

    public Quaternion plus(Quaternion q) {
        return new Quaternion(a + q.a, b + q.b, c + q.c, d + q.d);
    }

    public Quaternion minus(Quaternion q) {
        return new Quaternion(a - q.a, b - q.b, c - q.c, d - q.d);
    }

    public Quaternion times(Quaternion q) {
        return new Quaternion(
                q.a * a - q.b * b - q.c * c - q.d * d,
                q.a * b + q.b * a - q.c * d + q.d * c,
                q.a * c + q.b * d + q.c * a - q.d * b,
                q.a * d - q.b * c + q.c * b + q.d * a);
    }

    private Quaternion times(double k) {
        return new Quaternion(k * a, k * b, k * c, k * d);
    }

    public double norm() {
        return Math.sqrt(a * a + b * b + c * c + d * d);
    }

    public Quaternion normalized() {
        return this.times(1.0 / norm());
    }

    public Quaternion inverse() {
        return conjugate().times(1.0 / Math.pow(norm(), 2.0));
    }

    public Quaternion conjugate() {
        return new Quaternion(a, -b, -c, -d);
    }

    public List<Double> coefficients() {
        return List.of(a, b, c, d);
    }
}