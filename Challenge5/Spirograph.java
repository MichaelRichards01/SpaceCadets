public class Spirograph 
{
    private float R, r, O;

    /**
     * Instantiates a new spirograph with the passing parameters:
     *  - Fixed circle radius.
     *  - Moving circle radius.
     *  - Pen offset from the moving circle.
     */
    public Spirograph(float _R, float _r, float _O)
    {
        R = _R;
        r = _r;
        O = _O;
    }

    /**
     * Calculates all the points on the spirograph.
     */
    public Point[] CalculatePoints(int _iterations, float _step)
    {
        Point[] points = new Point[_iterations];

        for (int i = 0; i < _iterations; i++)
        {
            float t = i * _step;
            Point newPoint = CalculatePoint(t);
            points[i] = newPoint;
        }

        return points;
    }

    /**
     * Calculate a point at the giving t value using parametric equations.
     */
    private Point CalculatePoint(float _t)
    {
        float x = (float)((R - r) * Math.cos(_t) + O * Math.cos(((R - r) / r) * _t));
        float y = (float)((R - r) * Math.sin(_t) + O * Math.sin(((R - r) / r) * _t));

        Point newPoint = new Point(x, y);

        return newPoint;
    }
}
