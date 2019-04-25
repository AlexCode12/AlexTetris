import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Tetris extends JPanel {
    private Point[][][] Piece = {
            /*
            Bitarna genereras
             */
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
            },

            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
            },

            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
            },

            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },

            {
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            },
    };

    private Color[] colors = {
            Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green,
    };

    private Point pieceOrigin;
    private int currentPiece;
    private int rotate;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    private long score;
    private Color[][] well;

    // Skapar en border runt spelytan samt startar spelet då newPiece annropas
    private void border() {
        well = new Color[12][24];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                if (i == 0 || i == 11 || j == 22) {
                    well[i][j] = Color.GRAY;
                } else {
                    well[i][j] = Color.BLACK;
                }
            }
        }
        newPiece();
    }

    // Ny del spawnar
    public void newPiece() {
        pieceOrigin = new Point(5, 2);
        rotate = 0;
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4);
            Collections.shuffle(nextPieces);
        }
        currentPiece = nextPieces.get(0);
        nextPieces.remove(0);
    }

    // Collision detection
    private boolean collisionAt(int x, int y, int rotation) {
        for (Point p : Piece[currentPiece][rotation]) {
            if (well[p.x + x][p.y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }

    // Biten kan roteras med/motsols
    public void rotate(int i) {
        int newRotate = (rotate + i) % 4;
        if (newRotate < 0) {
            newRotate = 3;
        }
        if (!collisionAt(pieceOrigin.x, pieceOrigin.y, newRotate)) {
            rotate = newRotate;
        }
        repaint();
    }

    // Biten kan flyttas till vänster eller höger
    public void move(int i) {
        if (!collisionAt(pieceOrigin.x + i, pieceOrigin.y, rotate)) {
            pieceOrigin.x += i;
        }
        repaint();
    }

    // Biten går ner en rad alt. fäster sig på spelytan
    public void dropDown() {
        if (!collisionAt(pieceOrigin.x, pieceOrigin.y + 1, rotate)) {
            pieceOrigin.y += 1;
        } else {
            fixToWell();
        }
        repaint();
    }

    // Sätter fast biten på spelplanen och gör den tillgänglig för collision detection.
    public void fixToWell() {
        for (Point place : Piece[currentPiece][rotate]) {
            well[pieceOrigin.x + place.x][pieceOrigin.y + place.y] = colors[currentPiece];
        }
        clearTheRows();
        newPiece();
    }

    public void deleteRow(int row) {
        for (int j = row-1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j+1] = well[i][j];
            }
        }
    }

    // Tar bort det antal av rader som är fyllda och ger poäng till det
    public void clearTheRows() {
        boolean gap;
        int numClears = 0;

        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (well[i][j] == Color.BLACK) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(j);
                j += 1;
                numClears += 1;
            }
        }

        switch (numClears) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }

    // Ritar ut biten
    private void drawPiece(Graphics g) {
        g.setColor(colors[currentPiece]);
        for (Point p : Piece[currentPiece][rotate]) {
            g.fillRect((p.x + pieceOrigin.x) * 26,
                    (p.y + pieceOrigin.y) * 26,
                    25, 25);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // Paint the well
        g.fillRect(0, 0, 620, 770);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                g.setColor(well[i][j]);
                g.fillRect(26*i, 26*j, 25, 25);
            }
        }
        g.setColor(Color.WHITE);
        g.drawString("" + score, 425, 25);
        drawPiece(g);
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to ALETRIS!");
        int reply = JOptionPane.showConfirmDialog(null, "Do you know how to play?");
        if (reply == 0) {
            JOptionPane.showMessageDialog(null, "Alright, let's go!");
        } else if (reply == 1) {
            JOptionPane.showMessageDialog(null, "Alright, this is how you play.");
            JOptionPane.showMessageDialog(null, "Your goal is to remove as many lines as possible.");
            JOptionPane.showMessageDialog(null, "Controls: \n Use the arrowkeys to move/rotate, and the Space key to expedite the decent");
            int finalReply = JOptionPane.showConfirmDialog(null, "Are you sure you want to continue?");
            if (finalReply == 0) {
                JOptionPane.showMessageDialog(null, "Alright, let's go!");
            } else {
                JOptionPane.showMessageDialog(null, "Okej. :(");
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
        JFrame f = new JFrame("ALETRIS");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(620, 770);
        f.setVisible(true);

        final Tetris game = new Tetris();
        game.border();
        f.add(game);

        // Kontroller
        f.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        game.rotate(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.rotate(+1);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.move(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.move(+1);
                        break;
                    case KeyEvent.VK_SPACE:
                        game.dropDown();
                        game.score += 1;
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        // Biten faller en rad på en sekund
        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        game.dropDown();
                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();
    }
}