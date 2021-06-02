package sketch_it.main;

import sketch_it.io.Input;
import sketch_it.io.display.Display;

import java.awt.*;
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

    }

    private void render()
    {
        //todo: implement multithreading for gui
        bs = display.getCanvas().getBufferStrategy();

        if (bs == null)
        {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        //clear
        //g.clearRect(0, 0, display.getWidth(), display.getHeight());
        // draw

        g.setPaintMode();
        g.setColor(Color.RED);

        if (input.getMouseDown(0))
        {
            g.fillOval(input.getMouseX(), input.getMouseY(), 10, 10);
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
