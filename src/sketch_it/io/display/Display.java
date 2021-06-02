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
}
