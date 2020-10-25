package DS.CalculatorPostfix;

public class Calculator {



    public static void main(String[] args) {
        InfixToPostfix a = new InfixToPostfix();

        System.out.println(a.infixToPostfix("1*2^3/4-5*6^(8-9)"));

    }
}
