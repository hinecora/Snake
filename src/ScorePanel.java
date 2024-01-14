import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 50;

    ScorePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        follow();
    }

    public void follow() {
        if (GamePanel.timer.isRunning()) {
            GamePanel.timer.addActionListener(this);
        }
        else {
            this.setVisible(false);
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("ink free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: "+GamePanel.applesEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE: "+ GamePanel.applesEaten))/2, graphics.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
