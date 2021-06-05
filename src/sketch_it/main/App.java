package sketch_it.main;

import sketch_it.io.Input;
import sketch_it.io.display.Display;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.text.ParseException;

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
    private int cx, cy, cz;


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
        {
            JFormattedTextField field = null;
            try
            {
                field = new JFormattedTextField(new MaskFormatter(
                            "###, ###, ###"));
            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            field.setColumns(10);
            field.setEnabled(true);
            field.setEditable(true);
            JOptionPane.showOptionDialog(null, "Colors: ", "Color Changer", -1, 0, null,
                    new Object[]
                            { field }, field);
            JOptionPane.showMessageDialog(null, "Colors changed!");

            String colors[] = field.getText().split(", ");

            cx = Integer.parseInt(colors[0]);
            cy = Integer.parseInt(colors[1]);
            cz = Integer.parseInt(colors[2]);
        }

        /*if (input.getKeyDown(KeyEvent.VK_6))
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
        g.clearRect(1000, 0, width - 1000, height);
        g.setColor(Color.BLACK);
        g.drawLine(1000, 0, 1000, height);
        g.drawString("Drawing Mode: " + mode, 1040, 50);
        g.drawString("Drawing Color: " + cx + ", " + cy + ", " + cz, 1040, 100);

        int mx = input.getMouseX(), my = input.getMouseY();

        Graphics2D g2d = (Graphics2D) g;

        if (input.getMouseDown(0))
        {
            if (mode == "pencil")
            {
                brushSize = 1f;
                g2d.setColor(new Color(cx, cy, cz));
                g2d.setStroke(new BasicStroke(brushSize));
                g2d.drawLine(px, py, mx, my);
            }

            if (mode == "marker")
            {
                brushSize = 8f;
                g2d.setColor(new Color(cx, cy, cz));
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
                g2d.setColor(new Color(cx, cy, cz));
                g.drawLine(px, py, mx, my);
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
    }
}
