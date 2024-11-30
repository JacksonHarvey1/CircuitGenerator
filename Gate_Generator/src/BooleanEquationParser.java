import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class BooleanEquationParser {

    // Constants for gate spacing
    private static final double VERTICAL_GATE_SPACING = 0.3;
    private static final double HORIZONTAL_GATE_SPACING = 0.4;

    // Counters and gate instances for tracking state
    static int singleLetterOperandsCounter = 0;
    static OR_GATE orGate;
    static AND_GATE andGate;
    // List to store gate instances
    private static List<Gate> gates = new ArrayList<>();

    // Method to parse and print gates
    public static void parseAndPrintGates(String equation) {
        StdDraw.clear();
        // Setting up the drawing canvas
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.setXscale(0, 2);
        StdDraw.setYscale(-1.5, 1);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        double[] a = {-10, 10, 10, -10};
        double[] b = {-10, -10, 10, 10};
        StdDraw.filledPolygon(a, b);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);

        // Stacks for operators and operands
        Stack<Character> operators = new Stack<>();
        Stack<String> operands = new Stack<>();

        // Iterate through each character in the equation
        for (char c : equation.toCharArray()) {
            if (c == '(') {
                // Push opening parenthesis onto the operator stack
                operators.push(c);
            } else if (c == ')') {
                // Pop operators and build gates until matching '(' is found
                while (!operators.isEmpty() && operators.peek() != '(') {
                    buildGate(operators.pop(), operands);
                }
                operators.pop(); // Pop the corresponding '('
            } else if (c == ' ') {
                // Ignore spaces
                continue;
            } else if ((c >= 'A' && c <= 'Z')) {
                // Push operands onto the stack
                operands.push(String.valueOf(c));
            } else if (c == '+' || c == '*') {
                // Pop and build gates based on operator precedence
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    buildGate(operators.pop(), operands);
                }
                operators.push(c);
            }
        }

        // Pop remaining operators and build gates
        while (!operators.isEmpty()) {
            buildGate(operators.pop(), operands);
        }

        // Connect lines between gates
        connectLines(gates);
        StdDraw.show();
    }

    // Method to build a gate based on the operator and operands
    private static void buildGate(char operator, Stack<String> operands) {
        String operand2 = operands.pop();
        String operand1 = operands.pop();
        String gateType = (operator == '+') ? "OR_GATE" : "AND_GATE";

        // Determine the column and spacing based on the number of gates with 1-letter operands
        double verticalSpacing = 0;
        double horizontalSpacing = 0;

        if (operand1.length() == 1 && operand2.length() == 1) {
            // For single-letter operands, use zero horizontal spacing
            verticalSpacing = (singleLetterOperandsCounter++) * VERTICAL_GATE_SPACING;
            horizontalSpacing = 0;
        }

        // Iterate through previous gates to check for matches
        for (Gate previousGate : gates) {
            if (previousGate.booleanOut.equals(operand1) || previousGate.booleanOut.equals(operand2)) {
                // If a match is found, update spacing based on the previous gate
                verticalSpacing = previousGate.verticalSpacing;
                horizontalSpacing = previousGate.horizontalSpacing + HORIZONTAL_GATE_SPACING;
                // Additional logic can be added here based on the match
            }
        }

        // Create a new gate and add it to the list
        gates.add(new Gate(gateType, operand1, operand2, horizontalSpacing, verticalSpacing));

        // Print the gate visually
        printGate(gateType, operand1, operand2, horizontalSpacing, verticalSpacing);

        // Push the result back onto the operand stack
        operands.push(getNextGateLabel(gateType, operand1, operand2));
    }

    // Method to print an individual gate visually
    private static void printGate(String gateType, String operand1, String operand2, double horizontalSpacing, double verticalSpacing) {
        double x;
        double y = 0.8 - verticalSpacing;

        if (gateType.equals("OR_GATE")) {
            // For OR gates, set x position and print the gate
            x = 0.2 + horizontalSpacing;
            orGate = new OR_GATE(operand1, operand2);
            orGate.printOrGate(x, y, operand1, operand2);
        } else {
            // For AND gates, set x position and print the gate
            x = 0.2 + horizontalSpacing;
            andGate = new AND_GATE(operand1, operand2);
            andGate.printAndGate(x, y, operand1, operand2);
        }
    }

    // Method to get the next gate label based on gate type and operands
    private static String getNextGateLabel(String gateType, String Operand1, String Operand2) {
        if (gateType.equals("OR_GATE")) {
            return Operand1 + "+" + Operand2;
        } else {
            return Operand1 + "*" + Operand2;
        }
    }

    // Method to determine operator precedence
    private static int precedence(char operator) {
        return (operator == '+') ? 1 : (operator == '*') ? 2 : 0;
    }

    // Method to connect lines between gates
    public static void connectLines(List<Gate> gates) {
        // Iterate through the gates and connect lines based on their relationships
        for (int i = 1; i < gates.size(); i++) {
            Gate currentGate = gates.get(i);

            for (int j = 0; j < i; j++) {
                Gate previousGate = gates.get(j);

                // Check if the gates have matching inputs and outputs
                if (previousGate.booleanOut.contains(currentGate.operand1) || previousGate.booleanOut.contains(currentGate.operand2)) {

                    // Draw connecting lines using the gate positions
                    if (previousGate.booleanOut.contains(currentGate.operand1)) {
                        if (previousGate.getType().equals("AND_GATE") && currentGate.getType().equals("AND_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYTop());
                            connectLines.drawLines();}
                        } else if (previousGate.getType().equals("OR_GATE") && currentGate.getType().equals("OR_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYTop());
                            connectLines.drawLines(); }
                        } else if (previousGate.getType().equals("OR_GATE") && currentGate.getType().equals("AND_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYTop());
                            connectLines.drawLines(); }
                        } else if (previousGate.getType().equals("AND_GATE") && currentGate.getType().equals("OR_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYTop());
                            connectLines.drawLines(); }
                        }
                    }
                    else {
                        if (previousGate.getType().equals("AND_GATE") && currentGate.getType().equals("AND_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYBottom());
                            connectLines.drawLines(); }
                        } else if (previousGate.getType().equals("OR_GATE") && currentGate.getType().equals("OR_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYBottom());
                            connectLines.drawLines(); }
                        } else if (previousGate.getType().equals("OR_GATE") && currentGate.getType().equals("AND_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines(); }

                            else {                             ConnectLines connectLines = new ConnectLines(
                                previousGate.getEndX(), previousGate.getEndY(),
                                currentGate.getStartX(), currentGate.getStartYBottom());
                            connectLines.drawLines(); }

                        } else if (previousGate.getType().equals("AND_GATE") && currentGate.getType().equals("OR_GATE")) {
                            if (previousGate.horizontalSpacing >= currentGate.horizontalSpacing) {
                                StdDraw.line(previousGate.getEndX(), previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY());
                                StdDraw.line(previousGate.getEndX() +0.2, previousGate.getEndY(), previousGate.getEndX()+0.2, previousGate.getEndY()-0.2);
                                ConnectLines connectLines = new ConnectLines(
                                        previousGate.getEndX() + 0.2, previousGate.getEndY()-0.2,
                                        currentGate.getStartX(), currentGate.getStartYTop());
                                connectLines.drawLines();
                            }
                            else {
                            ConnectLines connectLines = new ConnectLines(
                                    previousGate.getEndX(), previousGate.getEndY(),
                                    currentGate.getStartX(), currentGate.getStartYBottom());
                            connectLines.drawLines(); }
                        }
                    }
                }
            }
        }
    }
}
