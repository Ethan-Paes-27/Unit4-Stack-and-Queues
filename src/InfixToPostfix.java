import java.io.Writer;
import java.util.Scanner;
import java.util.Stack;
/**
 * InfixToPostfix.java
 * DATA STRUCTURES 5(A) — Jesuit High School
 *
 * Converts an infix expression (tokens separated by spaces) to
 * postfix (Reverse Polish Notation) using the Shunting Yard algorithm.
 *
 * Assumptions:
 * - Tokens are separated by single spaces.
 * - Operands are non-negative integers.
 * - Operators are: + - * /
 * - Parentheses are: ( )
 */

public class InfixToPostfix {
// ---------------------------------------------------------------
// Helper: Operator Precedence
// ---------------------------------------------------------------
    /**
     * Returns the precedence level of the given operator.
     * * and / → 2 (higher precedence)
     * + and - → 1 (lower precedence)
     * anything else → 0
     *
     * @param op a single-character operator string
     * @return integer precedence level
     */
    private static int precedence(String op) {
        if (op.equals("*") || op.equals("/")) {
            return 2;
        }

        if (op.equals("+") || op.equals("-")) {
            return 1;
        }

        return 0;
    }

// ---------------------------------------------------------------
// Helper: Is Operator?
// ---------------------------------------------------------------
    /**
     * Returns true if the token is one of the four operators: + - * /
     *
     * @param token the token to check
     * @return true if token is an operator
     */
    private static boolean isOperator(String token) {
// TODO: return true if token equals "+", "-", "*", or "/"
        return precedence(token) > 0;
    }

// ---------------------------------------------------------------
// Core Algorithm: Infix → Postfix
// ---------------------------------------------------------------
    /**
     * Converts a space-separated infix expression to a space-separated
     * postfix expression using the Shunting Yard algorithm.
     *
     * Algorithm steps for each token:
     * 1. Operand (number) → append to output
     * 2. "(" → push onto stack
     * 3. ")" → pop to output until "(" is found; discard
     "("
     * 4. Operator → while stack top is not "(" and has >=
     precedence,
     * pop to output; then push current operator
     * 5. End of input → pop all remaining operators to output
     *
     * @param infix a space-separated infix expression, e.g. "( 3 + 4 )"
     * @return the equivalent postfix expression, e.g. "3 4 +"
     */
    public static String convert(String infix) {
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        Scanner sc = new Scanner(infix);

        while (sc.hasNext()) {
            String s = sc.next();

            if (precedence(s) == 0 && !s.equals("(") && !s.equals(")")) {
                output.append(s);
                output.append(" ");
            }
            else if (s.equals("(")) {
                stack.push("(");
            }
            else if (s.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.append((stack.pop()));
                    output.append(" ");
                }

                stack.pop();
            }
            else {
                while (!stack.isEmpty() && !stack.peek().equals("(")
                        && precedence(stack.peek()) >= precedence(s)) {
                    output.append(stack.pop());
                    output.append(" ");
                }

                stack.push(s);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop());
            output.append(" ");
        }

        System.out.println(output.toString().trim());

        return output.toString().trim();
    }

// ---------------------------------------------------------------
// Test Cases
// ---------------------------------------------------------------
    /**
     * Runs a single test: converts the infix expression and checks
     * the result against the expected postfix string.
     *
     * @param infix the infix expression to convert
     * @param expected the expected postfix result
     */
    private static void runTest(String infix, String expected) {
        String result = convert(infix);
        boolean passed = result.equals(expected);
        System.out.println("Infix : " + infix);
        System.out.println("Expected: " + expected);
        System.out.println("Got : " + result);
        System.out.println(passed ? "✓ PASS" : "✗ FAIL");
        System.out.println("--------------------------------------------------");
    }
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" InfixToPostfix — Test Suite");
        System.out.println("==================================================\n");
// Test 1: Simple addition — no precedence conflict
// Expected: 3 4 +
        runTest("( 3 + 4 )", "3 4 +");
// Test 2: Division over a parenthesized sum — precedence + parens
// Expected: 10 2 3 + /
        runTest("10 / ( 2 + 3 )", "10 2 3 + /");
// Test 3: Nested parentheses — two levels deep
// Expected: 100 4 2 - 25 * /
        runTest("100 / ( ( 4 - 2 ) * 25 )", "100 4 2 - 25 * /");
// Test 4: Deeply nested with mixed operators — full complexity
// Expected: 3 5 - 2 7 + * 8 5 - /
        runTest("( ( ( 3 - 5 ) * ( 2 + 7 ) ) / ( 8 - 5 ) )",
                "3 5 - 2 7 + * 8 5 - /");
        System.out.println("==================================================");
        System.out.println(" All tests complete.");
        System.out.println("==================================================");
    }
}
