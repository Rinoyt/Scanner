import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {
    @Test
    public void intTest() {
        String[] inputs = new String[]{
                "100",
                "   -100      \r\n\r       34567   "
        };
        
        int[][] expected = new int[][]{
                {100},
                {-100, 34567}
        };

        for (int i = 0; i < inputs.length; i++) {
            try (Scanner in = new Scanner(inputs[i])) {
                for (int expect : expected[i]) {
                    assertTrue(in.hasNextInt());
                    assertEquals(expect, in.nextInt());
                }
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }
}