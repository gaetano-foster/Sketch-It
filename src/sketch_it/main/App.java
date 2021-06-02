package sketch_it.main;

import sketch_it.io.display.Display;

public class App
{
    // display
    private int width, height;
    private String title;
    private Display display;

    // loop
    private boolean running = false;

    public App(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init()
    {
        display = new Display(width, height, title);
    }

    private void run()
    {
        init();

        double delta = 0;
        int desiredFPS = 255;
        double timePerTick = 1000000000 / desiredFPS;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1)
            {
                update();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000)
            {
                display.setTitle(display.getTitle() + " FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

    }

    private void update()
    {

    }

    private void render()
    {

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
