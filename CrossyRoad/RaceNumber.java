/**
 * Picks race numbers and draws them with seven-segment digits.
 *
 * @author Merlin Ortner
 * @version 1.0.0
 */
public class RaceNumber {
    private static final int[] RACE_NUMBERS = {1, 3, 5, 7, 11, 16, 23, 27, 33, 44, 55, 63, 77, 81, 99};
    // Segments of a seven-segment digit: top, upper right, lower right,
    // bottom, lower left, upper left, middle
    private static final String[] DIGIT_SEGMENTS = {
        "abcdef", "bc", "abged", "abgcd", "fgbc", "afgcd", "afgedc", "abc", "abcdefg", "abcdfg"
    };

    /**
     * @return a random number from the list of race numbers
     */
    public static int pick() {
        return RACE_NUMBERS[(int) (Math.random() * RACE_NUMBERS.length)];
    }

    /**
     * Draws the number with seven-segment digits (3 x 6 points each),
     * centered around (centerX, centerY), scaled by scale.
     */
    public static void draw(Behaelter container, int number, int centerX, int centerY, int scale) {
        String text = String.valueOf(number);
        int width = text.length() * 3 + (text.length() - 1) * 2;
        int left = centerX - width / 2;

        for (int i = 0; i < text.length(); i++) {
            int x0 = left + i * 5;
            int x1 = x0 + 3;
            int top = centerY - 3, mid = centerY, bottom = centerY + 3;

            for (char segment : DIGIT_SEGMENTS[text.charAt(i) - '0'].toCharArray()) {
                int[] l;
                switch (segment) {
                    case 'a': l = new int[]{x0, top, x1, top}; break;
                    case 'b': l = new int[]{x1, top, x1, mid}; break;
                    case 'c': l = new int[]{x1, mid, x1, bottom}; break;
                    case 'd': l = new int[]{x0, bottom, x1, bottom}; break;
                    case 'e': l = new int[]{x0, mid, x0, bottom}; break;
                    case 'f': l = new int[]{x0, top, x0, mid}; break;
                    default:  l = new int[]{x0, mid, x1, mid}; break;
                }
                Linie line = new Linie(container, l[0] * scale, l[1] * scale, l[2] * scale, l[3] * scale);
                line.setzeLinienDicke(scale);
                line.setzeFarbe("schwarz");
            }
        }
    }
}
