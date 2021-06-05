package sketch_it.main;

import sketch_it.io.Input;
import sketch_it.io.display.Display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class App
{
    // display
    private int width, height;
    private String title;
    private Display display;
    private Display secondary;

    // input
    private Input input;

    // loop
    private boolean running = false;

    // rendering
    private BufferStrategy bs;
    private Graphics g;
    private BufferStrategy sbs;
    private Graphics sg;

    // drawing
    private int divider = 80;
    private long start = System.nanoTime();
    private long nextSecond = 1000000000 / divider;
    private int tmx = 0, tmy = 0;
    private float brushSize = 1f;

    public App(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init()
    {
        display = new Display(width, height, title);
        secondary = new Display(300, 300, "Sketch Info");
        input = new Input();
        display.getFrame().addKeyListener(input);
        display.getFrame().addMouseListener(input);
        display.getFrame().addMouseMotionListener(input);
        display.getCanvas().addMouseListener(input);
        display.getCanvas().addMouseMotionListener(input);
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
            brushSize = 1f;
        if (input.getKeyDown(KeyEvent.VK_2))
            brushSize = 2f;
        if (input.getKeyDown(KeyEvent.VK_3))
            brushSize = 3f;
        if (input.getKeyDown(KeyEvent.VK_4))
            brushSize = 4f;
        if (input.getKeyDown(KeyEvent.VK_5))
            brushSize = 5f;
        if (input.getKeyDown(KeyEvent.VK_6))
            brushSize = 6f;
        if (input.getKeyDown(KeyEvent.VK_7))
            brushSize = 7f;
        if (input.getKeyDown(KeyEvent.VK_8))
            brushSize = 8f;
        if (input.getKeyDown(KeyEvent.VK_9))
            brushSize = 9f;

        if (brushSize <= 4)
            divider = 80;
        else
            divider = 150;
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
        g.setColor(Color.BLACK);

        int mx = input.getMouseX(), my = input.getMouseY();

        long end = System.nanoTime();
        long elapsedTime = end - start;

        if (elapsedTime >= nextSecond)
        {
            tmx = input.getMouseX();
            tmy = input.getMouseY();
            nextSecond += 1000000000 / divider;
        }

        if (input.getMouseDown(0))
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(brushSize));
            g2d.drawLine(tmx, tmy, mx, my);
        }

        // stop
        bs.show();
        g.dispose();

        // secondary
        sbs = secondary.getCanvas().getBufferStrategy();

        if (sbs == null)
        {
            secondary.getCanvas().createBufferStrategy(3);
            return;
        }

        sg = sbs.getDrawGraphics();
        // clear
        sg.clearRect(0, 0, 300, 300);
        // draw

        sg.drawString("Brush size: " + brushSize, 10, 10);

        // stop
        sbs.show();
        sg.dispose();
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
    }
}
