import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("PLease enter your Boolean Equation with the necessary parentheses.\nNote that this system does not work with NOT gates.");
        String input = scanner.nextLine();
        BooleanEquationParser.parseAndPrintGates(input);

}}