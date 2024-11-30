// Class representing an OR gate, extending the base Gate class
public class OR_GATE extends Gate {
    // Additional attributes for OR_GATE
    public double xPosition;
    public double yPosition;
    public double adjustedX;
    public double adjustedY;
    public String in1;
    public String in2;
    public String booleanOut;

    // Constructor for OR_GATE
    public OR_GATE(String A, String B) {
        // Call the base class constructor with type, operands, and default spacing
        super("OR_GATE", A, B, 0, 0);
        // Additional initialization for OR_GATE if needed
    }

    // Method to visually print an OR gate
    public void printOrGate(double x, double y, String A, String B) {
        // Set input and output values
        this.in1 = A;
        this.in2 = B;
        this.booleanOut = in1 + "+" + in2;

        // Set the default position values
        this.xPosition = x;
        this.yPosition = y;
        double defaultX = 0.5;
        double defaultY = 0.5;

        // Calculate adjusted x and y values for positioning
        double adjustedX = x - defaultX - 0.1; // Subtract 0.1 from x
        double adjustedY = y - defaultY;
        this.adjustedX = adjustedX;
        this.adjustedY = adjustedY;

        // Draw the visual representation of the OR gate using StdDraw
        // Ellipse representing the OR gate symbol
        StdDraw.ellipseArc(defaultX + adjustedX, defaultY + adjustedY, 0.05, 0.08, 90, -90);

        // Small lines inside the OR gate symbol
        StdDraw.line(defaultX + adjustedX, 0.58 + adjustedY, 0.55 + adjustedX, 0.58 + adjustedY);
        StdDraw.line(defaultX + adjustedX, 0.42 + adjustedY, 0.55 + adjustedX, 0.42 + adjustedY);

        // Ellipse arcs inside the OR gate symbol
        StdDraw.ellipseArc(0.55 + adjustedX, defaultY + adjustedY, 0.13, 0.08, 90, -1);
        StdDraw.ellipseArc(0.55 + adjustedX, defaultY + adjustedY, 0.13, 0.08, -90, -1);

        // Lines representing the inputs and outputs of the OR gate
        StdDraw.line(0.54 + adjustedX, 0.53 + adjustedY, 0.45 + adjustedX, 0.53 + adjustedY); // Upper input
        StdDraw.line(0.54 + adjustedX, 0.47 + adjustedY, 0.45 + adjustedX, 0.47 + adjustedY); // Lower input
        StdDraw.line(0.67 + adjustedX, defaultY + adjustedY, 0.75 + adjustedX, defaultY + adjustedY); // Output

        // Display input labels
        StdDraw.text(x - 0.13, y + 0.07, A); // Upper input label
        StdDraw.text(x - 0.13, y - 0.07, B); // Lower input label
    }
}
