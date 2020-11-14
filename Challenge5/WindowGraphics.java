import java.awt.*;
import javax.swing.*;

public class WindowGraphics extends JPanel
{
    private Point[] points;
    private Point centrePoint;

    public WindowGraphics(Point _centrePoint) { centrePoint = _centrePoint; }

    public void SetPoints(Point[] _points) { points = _points; }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBackground(Color.BLACK);

        Graphics2D graphics2d = (Graphics2D)graphics;
        graphics2d.setColor(Color.BLUE);

        if (points != null) DrawPoints(graphics2d);
    }

    /**
     * Draws each point on the spirograph.
     */
    private void DrawPoints(Graphics2D graphics2d)
    {
        for (Point point : points) {
            int x = Math.round(point.GetX() + centrePoint.GetX());
            int y = Math.round(point.GetY() + centrePoint.GetY());

            graphics2d.drawLine(x, y, x, y);
        }
    }
}