package ai;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RPolandExpression {
    static final double ERROR = -1;

    public static void main(String[] args) {
        System.out.println(eval("1 2 + 3 4 + *") == 21.0);//ok
        System.out.println(eval("+ 1 2") == ERROR);//error
        System.out.println(eval("1 2 +") == 3.0);
        System.out.println(eval("5 1 5 / - 5 *"));
        //(5 - (1/5)) *5 =24
        System.out.println(eval("5 1 5 / - 5 *".split("\\s+")));
        System.out.println(eval(Arrays.asList("5 1 5 / - 5 *".split("\\s+"))));
    }

    public static double eval(List<String> exp) {
        Stack<Double> S = new Stack<>();
        for (String tok : exp) {
            if (Evaluator.isNumber(tok)) {
                S.push(Double.parseDouble(tok));
            } else {
                if (S.isEmpty()) {
                    return ERROR;
                }
                double b = S.peek();
                S.pop();
                if (S.isEmpty()) {
                    return ERROR;
                }
                double a = S.peek();
                S.pop();
                double c = 0;
                switch (tok) {
                    case "+":
                        c = a + b;
                        break;
                    case "-":
                        c = a - b;
                        break;
                    case "*":
                        c = a * b;
                        break;
                    case "/":
                        if (b == 0) return ERROR;
                        c = a / b;
                        break;

                }
                S.push(c);
            }

        }
        return S.size() != 1 ? ERROR : S.peek();
    }

    public static double eval(String[] exp) {
        return eval(Arrays.asList(exp));
    }

    public static double eval(String s) {

        return eval(s.split("\\s+"));

    }

}
