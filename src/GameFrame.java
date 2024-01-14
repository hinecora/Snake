import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GameFrame(){
        Container pane = this.getContentPane();
        pane.add(new GamePanel());
        pane.add(new ScorePanel(), BorderLayout.NORTH);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
