import javax.swing.JFrame;

public class WindowFrame extends JFrame
{
    WindowGraphics windowGraphics;

    public WindowFrame(int xSize, int ySize)
    {
        windowGraphics = new WindowGraphics(new Point(xSize / 2, ySize / 2));

        this.setSize(xSize, ySize);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(windowGraphics);

        this.setVisible(true);
    }

    public void PassPoints(Point[] _points) { windowGraphics.SetPoints(_points); }
}