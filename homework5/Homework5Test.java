import java.math.BigInteger;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;

public class Homework5Test {

    @Test
    public void testBinaryString() {
        record Trial(int n, String expected) {
        }
        var trials = new Trial[] {
                new Trial(0, "0"),
                new Trial(1, "1"),
                new Trial(2, "10"),
                new Trial(3, "11"),
                new Trial(10, "1010"),
                new Trial(256, "100000000"),
                new Trial(257, "100000001"),
                new Trial(15378813, "111010101010100101111101"),
                new Trial(Integer.MAX_VALUE, "1111111111111111111111111111111"),
                new Trial(-1, "-1"),
                new Trial(-2, "-10"),
                new Trial(-35, "-100011"),
                new Trial(Integer.MIN_VALUE, "-10000000000000000000000000000000")
        };
        for (var trial : trials) {
            assertEquals(trial.expected, NeedlesslyRecursive.binaryString(trial.n));
        }
    }

    @Test
    public void testAckermann() {
        String[] bad = { "-1 3", "3 -10", "0 -1", "-1 -1" };
        String[] good = {
                "0 0 1", "0 9 10", "0 69 70", "1 0 2", "1 1 3", "1 9 11",
                "2 0 3", "2 3 9", "2 9 21", "3 0 5", "3 2 29", "3 9 4093",
                "4 0 13"
        };
        for (String s : bad) {
            var trial = s.split(" ");
            BigInteger x = new BigInteger(trial[0]);
            BigInteger y = new BigInteger(trial[1]);
            assertThrows(IllegalArgumentException.class,
                    () -> NeedlesslyRecursive.a(x, y));
        }
        for (String s : good) {
            var trial = s.split(" ");
            BigInteger x = new BigInteger(trial[0]);
            BigInteger y = new BigInteger(trial[1]);
            BigInteger expected = new BigInteger(trial[2]);
            assertEquals(expected, NeedlesslyRecursive.a(x, y));
        }
    }

    @Test
    public void testLog3() {
        assertEquals(0, NeedlesslyRecursive.log3(1));
        assertEquals(1, NeedlesslyRecursive.log3(3));
        assertEquals(1, NeedlesslyRecursive.log3(5));
        assertEquals(2, NeedlesslyRecursive.log3(9));
        assertEquals(1, NeedlesslyRecursive.log3(8));
        assertEquals(3, NeedlesslyRecursive.log3(80));
        assertEquals(4, NeedlesslyRecursive.log3(81));
        assertEquals(4, NeedlesslyRecursive.log3(242));
        assertEquals(5, NeedlesslyRecursive.log3(243));
        assertEquals(8, NeedlesslyRecursive.log3(19682));
        assertEquals(9, NeedlesslyRecursive.log3(19683));
    }

    @Test
    public void testPower() {
        var trials = new String[] {
                "0 5 0", "1 8 1", "9 3 729", "2 12 4096", "2 13 8192",
                "231 15 284563830596984699348644406569092951",
                "3 99 171792506910670443678820376588540424234035840667",
                "100 20 10000000000000000000000000000000000000000" };
        for (var trial : trials) {
            var values = trial.split(" ");
            BigInteger x = new BigInteger(values[0]);
            int n = Integer.parseInt(values[1]);
            BigInteger expected = new BigInteger(values[2]);
            assertEquals(expected, NeedlesslyRecursive.power(x, n));
        }
    }

    @Test
    public void testPowerSet() {
        assertEquals(Sets.powerSet(Set.of()),
                Set.of(Set.of()));
        assertEquals(Sets.powerSet(Set.of(1)),
                Set.of(Set.of(), Set.of(1)));
        assertEquals(Sets.powerSet(Set.of(1, 2)),
                Set.of(Set.of(), Set.of(1), Set.of(2), Set.of(1, 2)));
        assertEquals(Sets.powerSet(Set.of("A", "B", "C")),
                Set.of(Set.of(), Set.of("A"), Set.of("B"),
                        Set.of("A", "B"), Set.of("C"),
                        Set.of("A", "C"), Set.of("B", "C"),
                        Set.of("A", "B", "C")));
        assertThrows(IllegalArgumentException.class, () -> Sets.powerSet(
                IntStream.rangeClosed(1, 17).boxed().collect(toSet())));
    }

    @Test
    public void testMultiples() {
        assertEquals(Sets.multiples(Set.of(), 5), List.of());
        assertEquals(Sets.multiples(Set.of(3, 1, 8, 19), 5), List.of());
        assertEquals(Sets.multiples(Set.of(3, 800, 8, 19), 8), List.of(8, 800));
        assertEquals(Sets.multiples(Set.of(3, 800, 8, 19), 1), List.of(3, 8, 19, 800));
    }
}