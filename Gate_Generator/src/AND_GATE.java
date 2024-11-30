// Class representing an AND gate, extending the base Gate class
public class AND_GATE extends Gate {
    // Additional attributes for AND_GATE
    double xPosition;
    double yPosition;
    public String in1;
    public String in2;
    public String booleanOut;

    // Constructor for AND_GATE
    public AND_GATE(String A, String B) {
        // Call the base class constructor with type, operands, and default spacing
        super("AND_GATE", A, B, 0, 0);
        // Additional initialization for AND_GATE if needed
    }

    // Method to visually print an AND gate
    public void printAndGate(double x, double y, String A, String B) {
        // Set input and output values
        this.in1 = A;
        this.in2 = B;
        this.xPosition = x;
        this.yPosition = y;
        this.booleanOut = in1 + "*" + in2;

        // Draw the visual representation of the AND gate using StdDraw
        // Arc representing the AND gate symbol
        StdDraw.arc(x - 0.05, y, 0.1, 270, 90);

        // Lines inside the AND gate symbol
        StdDraw.line(x - 0.05, y + 0.1, x - 0.05, y - 0.1); // Vertical line
        StdDraw.line(x - 0.05, y + 0.05, x - 0.15, y + 0.05); // Top start line
        StdDraw.line(x - 0.05, y - 0.05, x - 0.15, y - 0.05); // Bottom start line
        StdDraw.line(x + 0.05, y, x + 0.15, y); // End line

        // Display input labels
        StdDraw.text(x - 0.13, y + 0.07, A); // Upper input label
        StdDraw.text(x - 0.13, y - 0.07, B); // Lower input label
    }
}
