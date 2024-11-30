public class Gate {
    // Attributes of the gate
    public String type;
    public String operand1;
    public String operand2;
    public double horizontalSpacing; // Added horizontalSpacing
    public double verticalSpacing; // Added verticalSpacing
    public String booleanOut;

    // Constructor for the base class
    Gate(String type, String operand1, String operand2, double horizontalSpacing, double verticalSpacing) {
        // Initialize attributes with provided values
        this.type = type;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;

        // Determine boolean output based on gate type
        if (type.equals("OR_GATE")) {
            this.booleanOut = operand1 + "+" + operand2;
        } else {
            this.booleanOut = operand1 + "*" + operand2;
        }
    }

    // Getters for common attributes
    public String getType() {
        return type;
    }

    // Methods to get coordinates for drawing
    public double getStartX() {
        if (type.equals("AND_GATE")) {
            return horizontalSpacing + 0.05;
        } else {
            return horizontalSpacing + 0.05;
        }
    }

    public double getEndX() {
        if (type.equals("AND_GATE")) {
            return horizontalSpacing + 0.35; // Adjust as needed
        } else {
            return horizontalSpacing + 0.35; // Adjust as needed
        }
    }

    public double getStartYTop() {
        if (type.equals("AND_GATE")) {
            return 0.85 - verticalSpacing; // Adjust as needed
        } else {
            return 0.83 - verticalSpacing; // Adjust as needed
        }
    }

    public double getStartYBottom() {
        if (type.equals("AND_GATE")) {
            return 0.75 - verticalSpacing; // Adjust as needed
        } else {
            return 0.77 - verticalSpacing; // Adjust as needed
        }
    }

    public double getEndY() {
        if (type.equals("AND_GATE")) {
            return 0.8-verticalSpacing; // Adjust as needed
        } else {
            return -verticalSpacing + 0.8; // Adjust as needed
        }
    }
}
