import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    static int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    static boolean running = false;
    static Timer timer;
    Random random;
    private Image apple;
    private Image headUP;
    private Image headDOWN;
    private Image headRIGHT;
    private Image headLEFT;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        loadImages();
        startGame();
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        newApple();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                graphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                graphics.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            graphics.drawImage(apple, appleX, appleY, null);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    switch (direction) {
                        case 'U':
                            graphics.drawImage(headUP, x[i], y[i], null);
                            break;
                        case 'D':
                            graphics.drawImage(headDOWN, x[i], y[i], null);
                            break;
                        case 'L':
                            graphics.drawImage(headLEFT, x[i], y[i], null);
                            break;
                        case 'R':
                            graphics.drawImage(headRIGHT, x[i], y[i], null);
                            break;
                    }
                }
                else {
                    graphics.setColor(new Color(45,180,0));
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
        else {
            gameOver(graphics);
        }
    }

    public void loadImages() {
        apple = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/apple.png"))).getImage();
        headUP = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/headPositions/headUP.png"))).getImage();
        headRIGHT = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/headPositions/headRIGHT.png"))).getImage();
        headDOWN = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/headPositions/headDOWN.png"))).getImage();
        headLEFT = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/headPositions/headLEFT.png"))).getImage();
    }

    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        //проверка на столкнулкновение головы с телом
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //проверяет на столкновение с левой границей окна
        if (x[0] < 0) {
            running = false;
        }
        //проверяет на столкновение с левой границей окна
        if (x[0] >= SCREEN_WIDTH) {
            running = false;
        }
        //проверяет на столкновение с верхней границей окна
        if (y[0] < 0) {
            running = false;
        }
        //проверяет на столкновение с нижней границей окна
        if (y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics) {
        //game over text
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("ink free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }

    }

}
