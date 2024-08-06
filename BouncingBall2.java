import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.ArrayList;

public class BouncingBall2 extends JPanel implements ActionListener {
    private int diameter;
    private int ballDiameter = 100; // Reduced ball diameter for better visibility
    private Timer timer;
    private ArrayList<Ball> balls; // List to store balls
    private BufferedImage buffer; // Buffered image for trails

    public BouncingBall2() {
        timer = new Timer(20, this); // Update every 20 milliseconds
        timer.start();

        balls = new ArrayList<>();
        int numBalls = 500; // Number of balls for the flower pattern

        // Increase the speed for more dynamic movement
        for (int i = 0; i < numBalls; i++) {
            double phase = 2 * Math.PI / numBalls * i; // Different starting phases
            double speed = 0.5 + (i % 5) * 0.2; // Further increased speed
            balls.add(new Ball(phase, speed)); // Create ball with phase and speed
        }

        // Remove balls after 60 seconds
        new Timer(60000, e -> balls.clear()).start(); // 60,000 milliseconds = 60 seconds
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Initialize buffered image if not already done
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = buffer.createGraphics();

        Dimension size = getSize();
        int width = size.width;
        int height = size.height;

        diameter = Math.min(width, height) - 50;
        int centerX = width / 2;
        int centerY = height / 2;

        // Maintain previous buffer content and add new trails
        g2d.setComposite(java.awt.AlphaComposite.SrcOver.derive(0.1f)); // Set lower opacity for trails
        g2d.drawImage(buffer, 0, 0, null); // Draw existing buffer content onto the canvas

        // Draw the circle boundary
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);

        // Draw trails of each ball
        for (Ball ball : balls) {
            ball.updatePosition();
            // Draw the ball
            g2d.setColor(ball.getColor()); // Fully opaque color for ball trails
            g2d.fillOval((int) (ball.getX() - ballDiameter / 2), (int) (ball.getY() - ballDiameter / 2), ballDiameter, ballDiameter);
        }

        // Draw the buffered image onto the panel
        g.drawImage(buffer, 0, 0, null);
    }

    private Color getRainbowColor(double phase) {
        // Generates a color in the rainbow spectrum based on the phase
        float hue = (float) (phase / (2 * Math.PI)); // Maps phase to hue
        return Color.getHSBColor(hue, 1.0f, 1.0f); // Full saturation and brightness
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // Trigger the paintComponent to update ball positions and trails
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(600, 600);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to fullscreen mode
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when window is closed

        BouncingBall2 circlePanel = new BouncingBall2();
        mainFrame.add(circlePanel);
        mainFrame.setVisible(true);
    }

    // Inner class for each ball
    private class Ball {
        private double phase;
        private double centerX;
        private double centerY;
        private double speed; // Speed of the ball
        private double xSpeed;
        private double ySpeed;
        private Color color;

        public Ball(double phase, double speed) {
            this.phase = phase;
            this.speed = speed;

            // Initialize the position at the center
            this.centerX = getWidth() / 2;
            this.centerY = getHeight() / 2;

            // Set an initial random direction
            double angle = Math.random() * 2 * Math.PI; // Random direction
            this.xSpeed = speed * Math.cos(angle);
            this.ySpeed = speed * Math.sin(angle);

            // Set the ball color based on its phase
            this.color = getRainbowColor(phase);
        }

        public void updatePosition() {
            // Update position based on speed
            centerX += xSpeed;
            centerY += ySpeed;

            // Compute distance from the center of the circle
            double dx = centerX - getWidth() / 2;
            double dy = centerY - getHeight() / 2;
            double distanceFromCenter = Math.sqrt(dx * dx + dy * dy);

            // Check if the ball is outside the circle
            if (distanceFromCenter > diameter / 2 - ballDiameter) {
                // Normalize the normal vector
                double normX = dx / distanceFromCenter;
                double normY = dy / distanceFromCenter;

                // Reflect the direction vector
                double dotProduct = xSpeed * normX + ySpeed * normY;
                xSpeed -= 2 * dotProduct * normX;
                ySpeed -= 2 * dotProduct * normY;

                // Adjust position to be on the circle boundary
                centerX = getWidth() / 2 + (diameter / 2 - ballDiameter) * normX;
                centerY = getHeight() / 2 + (diameter / 2 - ballDiameter) * normY;
            }
        }

        public double getX() {
            return centerX;
        }

        public double getY() {
            return centerY;
        }

        public Color getColor() {
            return color;
        }

        public double getPhase() {
            return phase;
        }
    }
}
