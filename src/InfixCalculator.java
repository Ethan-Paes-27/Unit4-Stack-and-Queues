import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
/**
 * InfixCalculator.java
 * DATA STRUCTURES 5(A) — Jesuit High School
 *
 * Full pipeline: Infix expression → Postfix → Evaluated integer result.
 *
 * HOW TO USE THIS FILE:
 * 1. Your convert() method below is identical to InfixToPostfix.java.
 * Complete it the same way (or copy your finished version over).
 * 2. Find the evaluatePostfix() method stub below.
 * Copy your completed evaluatePostfix() method from last week's
 * assignment into that stub — replace the TODO body only,
 * keeping the method signature exactly as written.
 * 3. The evaluate() method chains them together automatically.
 * 4. Run main() to check all test cases.
 *
 * Assumptions:
 * - Tokens are separated by single spaces.
 * - Operands are non-negative integers.
 * - Operators: + - * / (integer division, truncates toward zero)
 * - Parentheses: ( )
 */
public class InfixCalculator {
// ================================================================
// PART 1 — Infix to Postfix Converter
// (Same algorithm as InfixToPostfix.java — complete or copy over)
// ================================================================

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

    private static boolean isOperator(String token) {
// TODO: return true if token equals "+", "-", "*", or "/"
        return precedence(token) > 0;
    }

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
// ================================================================
// PART 2 — Postfix Evaluator
// Copy your completed method from last week's assignment here.
// Keep the method signature exactly as written below.
// ================================================================
    /**
     * Evaluates a space-separated postfix expression and returns the
     * integer result.
     *
     * Algorithm reminder:
     * - Scan tokens left to right.
     * - Operand → push onto stack.
     * - Operator → pop two operands (b then a), apply operator,
     * push result back onto stack.
     * - End of input → the single value left on the stack is the answer.
     *
     * @param postfix e.g. "3 4 +"
     * @return integer result, e.g. 7
     */
    public static int evaluatePostfix(String postfix) {
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        Scanner sc = new Scanner(postfix);

        ArrayList<String> tokens = new ArrayList<>();

        while (sc.hasNext()) {
            tokens.add(sc.next());
        }

        for (int i = 0; i < tokens.size(); i++) {
            String c = tokens.get(i);

            if (!(c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/"))) {
                stack.push(Integer.parseInt(c));
            }
            else {
                int a = stack.pop();
                int b = stack.pop();

                if (c.equals("+")) {
                    stack.push(a + b);
                }
                if (c.equals("-")) {
                    stack.push(b - a);
                }
                if (c.equals("*")) {
                    stack.push(a * b);
                }
                if (c.equals("/")) {
                    stack.push(b / a);
                }
            }
        }

        return stack.pop();
    }
// ================================================================
// PART 3 — Full Pipeline
// No changes needed here — this wires Parts 1 and 2 together.
// ================================================================
    /**
     * Converts an infix expression to postfix, then evaluates it.
     *
     * @param infix a space-separated infix expression
     * @return the integer result of the expression
     */
    public static int evaluate(String infix) {
        String postfix = convert(infix);
        System.out.println(" Postfix : " + postfix);
        return evaluatePostfix(postfix);
    }
// ================================================================
// Test Cases
// ================================================================
    /**
     * Runs a single end-to-end test.
     *
     * @param infix the infix expression to evaluate
     * @param expected the expected integer result
     */
    private static void runTest(String infix, int expected) {
        System.out.println("Infix : " + infix);
        int result = evaluate(infix);
        boolean passed = result == expected;
        System.out.println("Expected: " + expected);
        System.out.println("Got : " + result);
        System.out.println(passed ? "✓ PASS" : "✗ FAIL");
        System.out.println("--------------------------------------------------");
    }
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" InfixCalculator — End-to-End Test Suite");
        System.out.println("==================================================\n");
// Test 1: Simple addition
// ( 3 + 4 ) → postfix: 3 4 + → result: 7
        runTest("( 3 + 4 )", 7);
// Test 2: Division over parenthesized sum
// 10 / ( 2 + 3 ) → postfix: 10 2 3 + / → result: 2
        runTest("10 / ( 2 + 3 )", 2);
// Test 3: Nested parentheses
// 100 / ( ( 4 - 2 ) * 25 ) → postfix: 100 4 2 - 25 * / → result: 2
        runTest("100 / ( ( 4 - 2 ) * 25 )", 2);
// Test 4: Deeply nested, mixed operators, negative intermediate
// ( ( ( 3 - 5 ) * ( 2 + 7 ) ) / ( 8 - 5 ) )
// → postfix: 3 5 - 2 7 + * 8 5 - /
// → (3-5)=-2, (2+7)=9, -2*9=-18, (8-5)=3, -18/3=-6
        runTest("( ( ( 3 - 5 ) * ( 2 + 7 ) ) / ( 8 - 5 ) )", -6);
        System.out.println("==================================================");
        System.out.println(" All tests complete.");
        System.out.println("==================================================");
    }
}
