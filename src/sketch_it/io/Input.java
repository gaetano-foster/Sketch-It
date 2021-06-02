package sketch_it.io;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
    private boolean[] keys = new boolean[256];
    private boolean mouse1 = false;
    private boolean mouse2 = false;
    private boolean mouse3 = false;
    private int mouseX, mouseY;

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            mouse1 = true;

        if (e.getButton() == MouseEvent.BUTTON3)
            mouse2 = true;

        if (e.getButton() == MouseEvent.BUTTON2)
            mouse3 = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            mouse1 = false;

        if (e.getButton() == MouseEvent.BUTTON3)
            mouse2 = false;

        if (e.getButton() == MouseEvent.BUTTON2)
            mouse3 = false;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public boolean getKeyDown(int keyCode)
    {
        return keys[keyCode];
    }

    public boolean getMouseDown(int mouseButton)
    {
        if (mouseButton == 0)
            return mouse1;
        else if (mouseButton == 1)
            return mouse2;
        else
            return mouse3;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }
}
