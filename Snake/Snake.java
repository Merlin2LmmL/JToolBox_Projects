public class Snake {
    Spielfeld spielfeld = new Spielfeld();
    int[] FELD = spielfeld.FELD;
    Kreis[][] felder = new Kreis[FELD[0]][FELD[1]];
    int[][] snake = new int[FELD[0]][FELD[1]];
    Apple[][] apples = new Apple[FELD[0]][FELD[1]];

    public Snake() {
        for (int y = 0; y < FELD[1]; y++) {
            for (int x = 0; x < FELD[0]; x++) {
                felder[x][y] = new Kreis(x * FELD[2], y * FELD[2], FELD[2] / 2);
                felder[x][y].setzeFarbe("gruen");
                felder[x][y].fuellen();
                felder[x][y].unsichtbarMachen();
            }
        }
        snake[(int) (Math.random() * FELD[0])][(int) (Math.random() * FELD[1])] = 1;
        newFruit();
        int[] coords = getHead();
        felder[coords[0]][coords[1]].sichtbarMachen();
    }

    public int getLength() {
        int length = 0;
        for (int y = 0; y < FELD[1]; y++) {
            for (int x = 0; x < FELD[0]; x++) {
                if (snake[x][y] > length) {
                    length = snake[x][y];
                }
            }
        }
        return length;
    }

    public int[] getHead() {
        int xpos = 0;
        int ypos = 0;
        int length = 0;
        for (int y = 0; y < FELD[1]; y++) {
            for (int x = 0; x < FELD[0]; x++) {
                if (snake[x][y] > length) {
                    length = snake[x][y];
                    xpos = x;
                    ypos = y;
                }
            }
        }
        return new int[]{xpos, ypos};
    }

    public void update() {
        for (int y = 0; y < FELD[1]; y++) {
            for (int x = 0; x < FELD[0]; x++) {
                if (snake[x][y] != 0) {
                    felder[x][y].sichtbarMachen();
                    felder[x][y].getBasisComponente().repaint();
                } else {
                    felder[x][y].unsichtbarMachen();
                }
            }
        }
    }

    public void move(int direction /* 0=↓, 1=↑, 2=→, 3=← */) {
        int[] head = getHead();
        int newX = head[0];
        int newY = head[1];

        switch (direction) {
            case 0: // ↓
                newY++;
                break;
            case 1: // ↑
                newY--;
                break;
            case 2: // →
                newX++;
                break;
            case 3: // ←
                newX--;
                break;
            default:
                System.out.println("Keine gültige Bewegungsrichtung");
                return;
        }

        if (newX < 0 || newX >= FELD[0] || newY < 0 || newY >= FELD[1] || snake[newX][newY] != 0) {
            System.out.println("Game Over!");
            System.exit(0);
        }

        checkforwin();

        snake[newX][newY] = getLength() + 1;

        // Fruit-Check
        if (apples[newX][newY] != null) {
            apples[newX][newY].remove();
            apples[newX][newY] = null;
            newFruit();
        } else {
            for (int y = 0; y < FELD[1]; y++) {
                for (int x = 0; x < FELD[0]; x++) {
                    if (snake[x][y] != 0) {
                        snake[x][y]--;
                    }
                }
            }
        }
        update();
    }

    public void newFruit() {
        while (true) {
            int x = (int) (Math.random() * FELD[0]);
            int y = (int) (Math.random() * FELD[1]);
            if (snake[x][y] == 0 && apples[x][y] == null) {
                int appleX = x * FELD[2] + FELD[2] / 4;
                int appleY = y * FELD[2] + FELD[2] / 4;
                Apple newApple = new Apple(appleX, appleY, FELD[2] / 4);
                apples[x][y] = newApple;
                break;
            }
        }
    }

    public void checkforwin() {
        int feldersumme=0;
        for (int y = 0; y < FELD[1]; y++) {
            for (int x = 0; x < FELD[0]; x++) {
                if (snake[x][y] != 0) {
                    feldersumme++;
                }
            }
        }
        if (feldersumme >= FELD[0]*FELD[1] - 1) {
            System.out.println("You Win!");
            System.exit(0);
        }
    }
}