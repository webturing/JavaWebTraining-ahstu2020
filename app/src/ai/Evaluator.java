package ai;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class Evaluator {
	public static void main(String[] args) {
		String[] exp = "5 - 1 5 /   5 *".split("\\s+");
		System.out.println(eval(exp));
	}

	public static double eval(String[] exp) {
		return eval(Arrays.asList(exp));
	}


	public static double eval(List<String> exp) {
		Stack<Double> stack = new Stack<Double>();
		for (String s : exp) {
			if (isNumber(s)) {
				stack.push(Double.parseDouble(s));
			} else if (isOperator(s)) {
				if (stack.isEmpty())
					return -1;
				double b = stack.pop();
				double a = 0;
				try {
					a = stack.pop();
				} catch (Exception e) {
					return -1;
				}
				double result = 0;
				switch (s.charAt(0)) {
				case '+':
					result = a + b;
					break;
				case '-':
					result = a - b;
					break;
				case '*':
					result = a * b;
					break;
				case '/':
					if (b == 0) {
						result = Double.MAX_VALUE;
					} else {
						result = a / b;
					}
					break;
				}
				stack.push(result);
			}
		}
		if (stack.size() != 1)
			return -1;
		return stack.peek();
	}

	public static boolean isOperator(String s) {
		return "+-*/".contains(s);
	}

	public static boolean isNumber(String s) {// isXXXX hasXXX
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean lessOrEqual(String a, String b) {
		return Arrays.asList("-- -+ *+ *- */ /+ /- /* //".split(" ")).contains(
				a + b);
	}

	public static boolean less(String a, String b) {
		return Arrays.asList("+* +/ -* -/".split(" ")).contains(a + b);
	}
}