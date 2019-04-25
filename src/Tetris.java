import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Tetris extends JPanel {

    private Point[][][] Piece = {
            {
                    /*
                    Genererar en bit, valde att byta till denna metod för att jag hade stora problem med att få Enum:en att fungera som jag vile.
                     */
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
            },
            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },
    };
    private Color[] Colors = {
            Color.green, Color.red
    };
    private long score;
    private Color[][] well;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    private void init() {
        well = new Color[18][28];
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 28; j++) {
                if (i == 0 || i == 11 || j == 22) {
                    well[i][j] = Color.GRAY;
                } else {
                    well[i][j] = Color.BLACK;
                }
            }
        }
    }
    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, 620, 770);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                g.setColor(well[i][j]);
                g.fillRect(32 * i, 32 * j, 31, 31);
            }
            g.setColor(Color.WHITE);
            g.drawString("SCORE: " + score, 425, 25);
            g.drawString("LEEDS LEEDS LEEDS", 425, 200);
            g.drawString("Detta spel utvecklades av", 425, 500);
            g.drawString("Alexander O TE17 under 2019", 425, 520);
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to ALETRIS!");
        int reply = JOptionPane.showConfirmDialog(null, "Do you know how to play?");
        if (reply == 0) {
            JOptionPane.showMessageDialog(null, "Alright, let's go!");
        } else if (reply == 1) {
            JOptionPane.showMessageDialog(null, "Alright, this is how you play.");
            JOptionPane.showMessageDialog(null, "Your goal is to remove as many lines as possible.");
            JOptionPane.showMessageDialog(null, "Controls: \n To rotate: Press x \n To move: Press A");
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
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}