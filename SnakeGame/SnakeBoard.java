/**
 *	Manages the visual grid of the game using a 2D char[][] array,
 *  where the snake and the target are rendered as ASCII characters.
 *
 *	@author	Jonathan Chen
 *	@since	May 5, 2025
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeBoard extends JPanel implements ActionListener{
    private final int TILE_SIZE = 20;
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private final int DELAY = 100;
    private final Snake snake;
    private Point food;
    private boolean running = true;
    private Timer timer;
    private Random rand = new Random();
    public SnakeBoard(){
        setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						snake.setDirection("UP");
						break;
					case KeyEvent.VK_DOWN:
						snake.setDirection("DOWN");
						break;
					case KeyEvent.VK_LEFT:
						snake.setDirection("LEFT");
						break;
					case KeyEvent.VK_RIGHT:
						snake.setDirection("RIGHT");
						break;
				}
            }
        });
        snake = new Snake();
        placeFood();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    private void placeFood(){
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        food = new Point(x, y);
    }
    public void actionPerformed(ActionEvent e){
        if(running){
            boolean grow = snake.getHead().equals(food);
            snake.move(grow);
            if(grow) placeFood();

            Point head = snake.getHead();
            if(head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT || snake.checkSelfCollision()){
                running = false;
                timer.stop();
            }
        }
        repaint();
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(running){
            // Draw food
            g.setColor(Color.RED);
            g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // Draw snake
            g.setColor(Color.GREEN);
            for(Point p : snake.getBody()){
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        else{
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
        }
    }
}
