import javax.swing.*;
import java.awt.*;

public class Tetris{

    enum Tetrominoes {
        NoShape(new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, new Color(0, 0, 0)),
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
        public static void main (String[]args){
            JOptionPane.showMessageDialog(null, "Welcome to AlexTetris!");
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
            JFrame f = new JFrame("ALEXTETRIS");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(620, 900);
            f.setVisible(true);
            f.getContentPane().setBackground(Color.BLACK);
        }
    }