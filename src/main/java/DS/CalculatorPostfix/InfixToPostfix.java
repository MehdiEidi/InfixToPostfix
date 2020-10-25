package DS.CalculatorPostfix;

import java.util.Stack;

public class InfixToPostfix {

    //Returns true if the given character is an operator.
    private boolean isOperator(char character) {
        switch (character) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
                return true;
            default:
                return false;
        }
    }

    //Returns true if the given character is an operand
    private boolean isOperand(char character) {
        switch (character) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    //Returns true if the operator1 has higher precedence than operator2.
    private boolean hasHigherPrecedence(char operator1, char operator2) {
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-' || operator2 == '(')) {
            return true;
        } else if (operator1 == '^' && (operator2 == '*' || operator2 == '/' || operator2 == '+' || operator2 == '-' || operator2 == '(')) {
            return true;
        } else return (operator1 == '+' || operator1 == '-') && operator2 == '(';
    }

    //Returns true if both operators have the same precedence.
    private boolean hasSamePrecedence(char operator1, char operator2) {
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '/' || operator2 == '*')) {
            return true;
        } else return (operator1 == '-' || operator1 == '+') && (operator2 == '-' || operator2 == '+');
    }

    /**
     * Converts an infix expression to a postfix expression.
     * @param infixExpression the infix expression.
     * @return the postfix expression.
     */
    public String infixToPostfix(String infixExpression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        //Traversing the given string.
        for (int i = 0; i < infixExpression.length(); i++) {
            //If the character is an operand we append it to the result string.
            //else if it is an operator and the stack is empty, we push it to the stack.
            //else if it is an operator and stack is not empty and the top operator in stack has lower precedence, we push
            //the character to the stack.
            //else if it is an operator and stack is not empty and the top operator in stack has higher precedence,
            //while the stack is not empty and the top operator in the stack has higher precedence than the character or they have
            //the same precedence, we append the top operator of the stack into the result and pop it from the stack. Then
            //we push the character in the expression to the stack.
            //else if the character is an opening bracket, we push it to the stack.
            //else if the character is a closing bracket, while the stack is not empty and we haven't reached an opening
            //bracket, we append everything to the result string and pop them from the stack. Then we pop the opening bracket
            //from the stack.
            if (isOperand(infixExpression.charAt(i))) {
                result.append(infixExpression.charAt(i));
            } else if (isOperator(infixExpression.charAt(i)) && stack.isEmpty()) {
                stack.push(infixExpression.charAt(i));
            } else if (isOperator(infixExpression.charAt(i)) && hasHigherPrecedence(infixExpression.charAt(i), stack.peek())) {
                stack.push(infixExpression.charAt(i));
            } else if (isOperator(infixExpression.charAt(i)) && !hasHigherPrecedence(infixExpression.charAt(i), stack.peek())) {
                while (!stack.isEmpty() && (hasHigherPrecedence(stack.peek(), infixExpression.charAt(i)) || hasSamePrecedence(infixExpression.charAt(i), stack.peek()))) {
                    result.append(stack.peek());
                    stack.pop();
                }

                stack.push(infixExpression.charAt(i));
            } else if (infixExpression.charAt(i) == '(') {
                stack.push(infixExpression.charAt(i));
            } else if (infixExpression.charAt(i) == ')') {
                while(!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.peek());
                    stack.pop();
                }

                stack.pop();
            }
        }

        //Appending the remaining operators to the result.
        while (!stack.isEmpty()) {
            result.append(stack.peek());
            stack.pop();
        }

        return result.toString();
    }
}