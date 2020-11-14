import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpirographDrawing
{
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    private static WindowFrame windowFrame;

    public static void main(String[] args) throws InterruptedException
    {
        new SpirographDrawing();

        DrawSpirograph(100, 10, 20);
    }

    public SpirographDrawing()
    {
        windowFrame = new WindowFrame(WINDOW_WIDTH, WINDOW_HEIGHT);
        windowFrame.setTitle("Spirograph");

        SetupParacterPanel();
    }

    /**
     * Setups up a jframe containing spinners that allow
     * the user to change the parameters of the spirograph.
     * 
     * When one of these parameters are changed, a new spirograph
     * is calculated and drawn by calling the DrawSpirograph method.
     */
    private void SetupParacterPanel()
    {
      JFrame frame = new JFrame();

        SpinnerModel model1 = new SpinnerNumberModel(100, 1, 100, 0.1);
        SpinnerModel model2 = new SpinnerNumberModel(10, 1, 100, 0.1);
        SpinnerModel model3 = new SpinnerNumberModel(20, 1, 100, 0.1);

        JSpinner rSpinner = new JSpinner(model1);
        JSpinner RSpinner = new JSpinner(model2);
        JSpinner OSpinner = new JSpinner(model3);

        rSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e1) {
                    float r = Float.parseFloat(rSpinner.getValue().toString());
                    float R = Float.parseFloat(RSpinner.getValue().toString());
                    float O = Float.parseFloat(OSpinner.getValue().toString());

                    DrawSpirograph(r, R, O);
                }
            }
        );
        RSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e2) {
                    float r = Float.parseFloat(rSpinner.getValue().toString());
                    float R = Float.parseFloat(RSpinner.getValue().toString());
                    float O = Float.parseFloat(OSpinner.getValue().toString());

                    DrawSpirograph(r, R, O);
                }
            }
        );
        OSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e3) {
                    float r = Float.parseFloat(rSpinner.getValue().toString());
                    float R = Float.parseFloat(RSpinner.getValue().toString());
                    float O = Float.parseFloat(OSpinner.getValue().toString());

                    DrawSpirograph(r, R, O);
                }
            }
        );

        JLabel label1 = new JLabel("Fixed Circle Radius:");
        JLabel label2 = new JLabel("Moving Circle Radius:");
        JLabel label3 = new JLabel("Offset:");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label1);
        panel.add(rSpinner);
        panel.add(label2);
        panel.add(RSpinner);
        panel.add(label3);
        panel.add(OSpinner);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100);
        frame.setTitle("Parameters");
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Instantiates a new spirograph with passed parameters.
     * 
     * An array of points on the spirograph are calculated
     * and passing onto the window frame be drawn.
     */
    private static void DrawSpirograph(float _r, float _R, float _O)
    {
        Spirograph spirograph = new Spirograph(_r, _R, _O);
        Point[] spirographPoints = spirograph.CalculatePoints(100000, 0.1f);
        windowFrame.PassPoints(spirographPoints);
        windowFrame.repaint();
    }
}