// Class to represent and draw lines connecting gates
class ConnectLines {
    // Attributes to store line coordinates
    public double startX;
    public double startY;
    public double endX;
    public double endY;

    // Constructor to initialize line coordinates and draw the lines
    public ConnectLines(double startX, double startY, double endX, double endY) {
        // Initialize line coordinates
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        // Call the drawLines method to draw the lines immediately upon construction
        drawLines();  // Add this line to explicitly call drawLines from the constructor
    }

    // Method to draw lines using StdDraw library
    public void drawLines() {
        // Draw the first horizontal line
        StdDraw.line(startX, startY, endX, startY);

        // Draw the vertical line connecting the two gates
        StdDraw.line(endX, startY, endX, endY);  // Update endY for the second line
    }
}
