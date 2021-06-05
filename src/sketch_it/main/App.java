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

    // input
    private Input input;

    // loop
    private boolean running = false;

    // rendering
    private BufferStrategy bs;
    private Graphics g;

    // drawing
    private final int DIVIDER = 80;
    private long start = System.nanoTime();
    private long nextSecond = 1000000000 / DIVIDER;
    private int tmx = 0, tmy = 0;
    private float brushSize = 1f;
    private String mode = "pencil";
    private int px = 0, py = 0;


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
        /*if (input.getKeyDown(KeyEvent.VK_4))
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
            brushSize = 9f;*/
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
            nextSecond += 1000000000 / DIVIDER;
        }

        Graphics2D g2d = (Graphics2D) g;

        if (input.getMouseDown(0))
        {
            if (mode == "pencil")
            {
                brushSize = 1f;
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(tmx, tmy, mx, my);
            }

            if (mode == "marker")
            {
                brushSize = 5f;
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(tmx, tmy, mx, my);
            }

            if (mode == "radial")
            {
                g.drawLine(px, py, mx, my);
            }
        }

        if (mode == "radial" && input.getMouseDown(1))
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
    }
}
