package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JPanel implements Runnable, KeyListener, ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 300, HEIGHT = 300;
    private int speed = 100;

    int score=0;
    private Client game;
    private Thread thread;
    private boolean running;
    private boolean right = true, left = false, up = false, down = false;

    private ArrayList<Snake> snake;
    private ArrayList<Fruit> fruits;
    private Random random;
    private int x = 0, y=0, size=5;

    private boolean actionDone = false;
    // private static Image backGround = Toolkit.getDefaultToolkit().createImage("C:\\Users\\Mahmo\\Documents\\KODA\\Projekt\\src\\Snake\\Grass_Snake.jpg");
    // private Icon[] backGround = new ImageIcon("/Users/charlotteandersson/IdeaProjects/Prutten/src/Lab3/rock.png")
    // Image img = ImageIO.read(new File("C:\\Users\\Mahmo\\Documents\\KODA\\Projekt\\src\\Snake\\Grass_snake_low.jpg"));
    static String location_pic = File.separator + "src"+ File.separator + "Snake" + File.separator + "Snake_Background.jpg";

    static String path_gameboard = System.getProperty("user.dir");
    private static Image backGround = Toolkit.getDefaultToolkit().createImage(path_gameboard+location_pic);
    
    GameBoard(Client client){
        game = client;

        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // skapar skärmen
        addKeyListener(this);
  
        snake = new ArrayList<Snake>();
        fruits = new ArrayList<Fruit>();
        random = new Random();
 
        start();
       
    }

    private void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop(){
        running = false;
        repaint();
        try{
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void move(){
        if (snake.size() == 0){
            Snake b = new Snake(x, y, size);
            snake.add(b);
        }
        if (right) x++;
        if (left) x--;
        if (up) y--;
        if (down) y++;
        actionDone = false;
        Snake b = new Snake(x, y, 10);
        snake.add(b);
        
        if (snake.size() > size) {
            snake.remove(0);}
        
    
        if (fruits.size() == 0) {
            int x = random.nextInt(WIDTH/10 -1);
            int y = random.nextInt(HEIGHT/10 -1);

            Fruit apple = new Fruit(x, y, 10);
            fruits.add(apple);
        }
        for (int i = 0; i < fruits.size(); i++) {
            if (x == fruits.get(i).getx() && y == fruits.get(i).gety()) {
                size++;
                score++;
                fruits.remove(i);
                i++;
                if (speed>1){
                    speed = speed - 3;
                }
            }
        }
        //  Kolliderar med sig sjäv
        for (int i = 0; i < snake.size(); i++) {
            if (x == snake.get(i).getx() && y == snake.get(i).gety()){
                if (i != snake.size()-1) {
                    stop();
                }
            }
        }
        //  Kolliderar med gränsen
        if (x < 0 || x > (WIDTH/10-1) || y < 0 || y > (HEIGHT/10-1)) {
            stop();
        }
    }

    public void paint(Graphics g){
        if (running) {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawImage(backGround, 0, 0, null);
            g.drawString(String.valueOf(score), 140, 270);

            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).draw(g);
            }
            for (int i = 0; i < fruits.size(); i++) {
                fruits.get(i).draw(g);
            }
        }
        else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        game.out.println("Gameover");
        game.out.flush();
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (WIDTH - fontMetrics.stringWidth(msg)) / 2, HEIGHT / 2);

    }
        /*
        JFrame scoreTable = new JFrame("Snake Highscores");
        scoreTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoreTable.setSize(400, 400);
        JLabel title = new JLabel("");
        title.setText("Leaderboard");
        scoreTable.add(title);
        scoreTable.setVisible(true);
        */


    @Override
    public void run() {
        long last_time = System.currentTimeMillis();
        // long last_time = System.nanoTime();
        while (running){
            long time = System.currentTimeMillis();
            if (time - last_time > speed){
                move();
                repaint();
                last_time = time;
            }
           
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!actionDone){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }
            actionDone = true;
        }
    
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
