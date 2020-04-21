package Snake;

import java.awt.*;

public class Fruit {

    private int x, y, width, height;

    Fruit(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        width = tileSize;
        height = tileSize;
    }

    void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x * width, y * height, width, height);

    }
    int getx(){
        return x;
    }

    public void setx(int x) {
        this.x = x;
    }

    int gety() {
        return y;
    }

    public void sety(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}


