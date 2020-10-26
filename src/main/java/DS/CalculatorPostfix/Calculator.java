package DS.CalculatorPostfix;

import java.util.Arrays;
import java.util.Stack;

public class Calculator {
//    private String[] toArray(String str) {
//        String[] array = new String[str.length()];
//        int k = 0;
//        StringBuilder temp = new StringBuilder();
//
//        for(int i = 0; i < str.length(); i++) {
//
//
//            if (str.charAt(i) == ' ') {
//                array[k++] = temp.toString();
//                temp = new StringBuilder();
//            } else {
//                temp.append(str.charAt(i));
//            }
//
//        }
//
//        String[] result = new String[k];
//        System.arraycopy(array, 0, result, 0, k);
//        return result;
//
//    }

    private int operate(int a, int b, char operation) {
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return (int) Math.pow(a, b);
        }
        return -1;
    }
    public int evaluate(String expression) {
        String[] expressionArray = expression.split(" ");
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < expressionArray.length; i++) {
            if (InfixToPostfix.isOperand(expressionArray[i])) {
                stack.push(Integer.valueOf(expressionArray[i]));
            } else if (InfixToPostfix.isOperator(expressionArray[i].charAt(0))) {
                int a = stack.peek();
                stack.pop();
                int b = stack.peek();
                stack.pop();
                stack.push(operate(b, a, expressionArray[i].charAt(0)));
            }
        }
        return stack.peek();
    }


    public static void main(String[] args) {
        InfixToPostfix a = new InfixToPostfix();
        Calculator b = new Calculator();
        System.out.println(b.evaluate(a.infixToPostfix("4*((2+16)/3)-2^3")));

        System.out.println(a.infixToPostfix("4*((2+16)/3)-2^5"));

    }
}
