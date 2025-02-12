import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {
    @Test
    public void intTest() {
        hasInt("100");
        validInt(100, "100");
        hasInt("   -100      \n       34567   ");
        validInt(-100, "   -100      \n       34567   ");
        validInt(34567, "      \n       34567   ");
    }

    private static void hasInt(String input) {
        try (Scanner in = new Scanner(input)) {
            assertTrue(in.hasNextInt());
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static void validInt(int expect, String input) {
        try (Scanner in = new Scanner(input)) {
            assertEquals(expect, in.nextInt());
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}