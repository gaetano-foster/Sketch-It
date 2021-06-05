package sketch_it.main;

import sketch_it.io.Input;
import sketch_it.io.display.Display;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class App
{
    // display
    private int width, height;
    private String title;
    private Display display;

    // input
    private Input input;

    // loop
    private boolean running = false;

    // rendering
    private BufferStrategy bs;
    private Graphics g;

    // drawing
    private float brushSize = 1f;
    private String mode = "pencil";
    private int px, py;
    private Color color = Color.BLACK;


    public App(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init()
    {
        display = new Display(width, height, title);
        input = new Input();
        display.getFrame().addKeyListener(input);
        display.getFrame().addMouseListener(input);
        display.getFrame().addMouseMotionListener(input);
        display.getCanvas().addMouseListener(input);
        display.getCanvas().addMouseMotionListener(input);
        px = input.getMouseX();
        py = input.getMouseY();
    }

    private void run()
    {
        init();

        while (running)
        {
            update();
            render();
        }

        stop();
    }

    private void update()
    {
        if (input.getKeyDown(KeyEvent.VK_1))
            mode = "pencil";
        if (input.getKeyDown(KeyEvent.VK_2))
            mode = "radial";
        if (input.getKeyDown(KeyEvent.VK_3))
            mode = "marker";
        if (input.getKeyDown(KeyEvent.VK_4))
            mode = "erase";
        if (input.getKeyDown(KeyEvent.VK_5))
            color = Color.BLACK;
        if (input.getKeyDown(KeyEvent.VK_6))
            color = Color.RED;
        if (input.getKeyDown(KeyEvent.VK_7))
            color = Color.GREEN;
        if (input.getKeyDown(KeyEvent.VK_8))
            color = Color.BLUE;
        if (input.getKeyDown(KeyEvent.VK_9))
            color = Color.YELLOW;
        if (input.getKeyDown(KeyEvent.VK_0))
            color = Color.MAGENTA;
        if (input.getKeyDown(KeyEvent.VK_MINUS))
            color = Color.CYAN;
        if (input.getKeyDown(KeyEvent.VK_EQUALS))
            color = Color.GRAY;
    }

    private void render()
    {
        bs = display.getCanvas().getBufferStrategy();

        if (bs == null)
        {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        // draw
        g.clearRect(1000, 0, width - 1000, height);
        g.setColor(Color.BLACK);
        g.drawLine(1000, 0, 1000, height);
        g.drawString("Drawing Mode: " + mode, 1040, 50);
        g.drawString("Drawing Color: " + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue(), 1040, 100);

        int mx = input.getMouseX(), my = input.getMouseY();

        Graphics2D g2d = (Graphics2D) g;

        if (input.getMouseDown(0))
        {
            if (mode == "pencil")
            {
                brushSize = 1f;
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(px, py, mx, my);
            }

            if (mode == "marker")
            {
                brushSize = 8f;
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(px, py, mx, my);
            }

            if (mode == "erase")
            {
                brushSize = 12f;
                g2d.setColor(new Color(238, 238, 238));
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(px, py, mx, my);
            }

            if (mode == "radial")
            {
                brushSize = 4f;
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(px, py, mx, my);
            }
        }

        if (mode == "radial" && input.getMouseDown(1))
        {
            px = input.getMouseX();
            py = input.getMouseY();
        }

        if (mode == "pencil")// && input.getMouseDown(0))
        {
            px = input.getMouseX();
            py = input.getMouseY();
        }

        if (mode == "marker")// && input.getMouseDown(0))
        {
            px = input.getMouseX();
            py = input.getMouseY();
        }

        if (mode == "erase")// && input.getMouseDown(0))
        {
            px = input.getMouseX();
            py = input.getMouseY();
        }

        // stop
        bs.show();
        g.dispose();
    }

    public synchronized void start()
    {
        if (running)
            return;
        running = true;
        run();
    }

    public synchronized void stop()
    {
        if (!running)
            return;
        running = false;
        System.exit(0);
    }
}
