/*
Program gjort i April 2019, med facit i hand tog jag nog vatten över huvudet, men det blev ändå ett helt ok resultat.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

public class Tetris extends JPanel{

    private Point[][][] Piece = {
            /*
            Denna del är alla bitar som är med.
            Fyra block är i en bit, och bitarna är uppbyggda som ett koordinatsystem.
            Jag valde att byta uppbyggnaden av bitarna för att jag inte fick till det med Enum:en.
             */
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
            },

            // J-Piece
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
            },

            // L-Piece
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
            },

            // O-Piece
            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },

            // S-Piece
            {
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            },
    };
    private Color [] Colors = {
            Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green,
    };

    private Point pieceOrigin;
    private int currentPiece;
    private int rotate;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    private long score;
    private Color[][] well;

    // Gör en border runt spelytan + genererar en ny bit
    private void init() {
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

    // Generar en ny bit
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
    private boolean collidesAt(int x, int y, int rotate) {
        for (Point p : Piece[currentPiece][rotate]) {
            if (well[p.x + x][p.y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }

    // Roterar biten med/motsols
    public void rotate(int i) {
        int newRotation = (rotate + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotate = newRotation;
        }
        repaint();
    }

    // Flyttar biten vänster/höger
    public void move(int i) {
        if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotate)) {
            pieceOrigin.x += i;
        }
        repaint();
    }

    // Biten åker ner en rad, alt. fastnar på bitarna
    public void dropDown() {
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotate)) {
            pieceOrigin.y += 1;
        } else {
            fixToWell();
        }
        repaint();
    }

    // Biten blir en dell av spelplanen, vilket gör att den kan kännas av
    // collision detection.
    public void fixToWell() {
        for (Point p : Piece[currentPiece][rotate]) {
            well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = Colors[currentPiece];
        }
        clearRows();
        newPiece();
    }

    public void deleteRow(int row) {
        for (int j = row-1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j+1] = well[i][j];
            }
        }
    }

    // Tar bort det antal rader som går och ger poäng för dem
    public void clearRows() {
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
        g.setColor(Colors[currentPiece]);
        for (Point p : Piece[currentPiece][rotate]) {
            g.fillRect((p.x + pieceOrigin.x) * 26,
                    (p.y + pieceOrigin.y) * 26,
                    25, 25);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // Ritar ut spelplanen
        g.fillRect(0, 0, 700, 770);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                g.setColor(well[i][j]);
                g.fillRect(26*i, 26*j, 25, 25);
            }
        }
        g.setColor(Color.WHITE);
        g.drawString("SCORE: " + score, 370, 25);
        g.drawString("Detta spel utvecklades av", 400, 500);
        g.drawString("Alexander O TE17 under 2019", 400, 520);
        drawPiece(g);
    }
    public static void main (String[]args){
        JOptionPane.showMessageDialog(null, "Welcome to ALETRIS!");
        int reply = JOptionPane.showConfirmDialog(null, "Do you know how to play?");
        if (reply == 0) {
            JOptionPane.showMessageDialog(null, "Alright, let's go!");
        } else if (reply == 1) {
            JOptionPane.showMessageDialog(null, "Alright, this is how you play.");
            JOptionPane.showMessageDialog(null, "Your goal is to remove as many lines as possible.");
            JOptionPane.showMessageDialog(null, "Controls: \n Use the arrow keys to rotate \n Press Space to lower the piece quicker");
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
        game.init();
        f.add(game);
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

        // Biten åker ned varje sekund
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