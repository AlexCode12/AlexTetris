import javax.swing.*;

public class Tetris {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to AlexTetris!");
        int reply = JOptionPane.showConfirmDialog(null, "Do you know how to play?");
        if (reply == 0) {
            JOptionPane.showMessageDialog(null, "Alright, let's go!");
        } else if (reply == 1){
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
    }
}