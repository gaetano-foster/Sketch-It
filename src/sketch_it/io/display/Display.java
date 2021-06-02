package sketch_it.io.display;

import javax.swing.*;
import java.awt.*;

public class Display
{
    private int width, height;
    private String title;
    private JFrame frame;
    private Canvas canvas;

    public Display(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;

        generateWindow();
    }

    private void generateWindow()
    {
        frame = new JFrame(title);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public void setFrame(JFrame frame)
    {
        this.frame = frame;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }
}
