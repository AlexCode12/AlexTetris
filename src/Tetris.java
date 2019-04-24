import javax.swing.*;
import java.awt.*;

public class Tetris extends JPanel{

    enum Tetrominoes {
        ZShape(new int[][] { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, new Color(204, 102, 102)),
        SShape(new int[][] { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } }, new Color(102, 204, 102)),
        LineShape(new int[][] { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, new Color(102, 102, 204)),
        TShape(new int[][] { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, new Color(204, 204, 102)),
        SquareShape(new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, new Color(204, 102, 204)),
        LShape(new int[][] { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }, new Color(102, 204, 204)),
        MirroredLShape(new int[][] { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }, new Color(218, 170, 0));

        public int[][] coords;
        public Color color;
        private Tetrominoes(int[][] coords, Color c) {
            this.coords = coords;
            color = c;
        }
    }

    private Color[][] well;
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
                g.fillRect(32*i, 32*j, 31, 31);
            }
        }
    }
        public static void main (String[]args){
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
        }
    }