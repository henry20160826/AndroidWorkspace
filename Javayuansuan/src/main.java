import java.util.ArrayList;
import java.util.Scanner;

public class main {

	private static ArrayList<Integer> num, symbol;
	private static int symbolStart = 0;

	public static void main(String[] args) {

		// TODO 得到数据
		String string = getString();

		// 处理处理数据
		num = new ArrayList<Integer>();
		symbol = new ArrayList<Integer>();
		getNum(string);
		getSymbol(string);

		// TODO 计算
		while (symbol.size() != symbolStart) {
			calM_D();
		}
		int sum = calA_S();
		System.out.println("Sum:" + sum);
	}

	private static String getString() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	private static void getNum(String string) {
		String string2[] = string.split("[-,*,/,+]");
		// System.out.println(Arrays.toString(string.split("[-,*,/,+]")));
		int len = string2.length, now;
		for (int i = 0; i < len; i++) {
			now = Integer.parseInt(string2[i]);
			num.add(now);
			// System.out.println(num.get(i));
		}
	}

	private static void getSymbol(String string) {
		char[] c = string.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if ('+' == c[i]) {
				symbol.add(1);
			} else if ('-' == c[i]) {
				symbol.add(2);
			} else if ('*' == c[i]) {
				symbol.add(3);
			} else if ('/' == c[i]) {
				symbol.add(4);
			}
		}

	}

	private static void printNum() {
		int len = num.size();
		for (int i = 0; i < len; i++) {
			System.out.println("Num:" + num.get(i));
		}
	}

	private static void printSymbol() {
		int len = symbol.size();
		for (int i = 0; i < len; i++) {
			System.out.println("Symbol:" + symbol.get(i));
		}
	}

	// 计算乘除
	private static void calM_D() {
		int start, end, sum = 0;
		int len = symbol.size();
		start = getStart();
		symbolStart = start;
		// 判断是否有乘除运算
		if (start == len) {
			return;
		}
		end = getEnd(start);
		// System.out.println("end:" + end);
		sum = num.get(start);
		for (int i = start; i < end; i++) {
			if (3 == symbol.get(i)) {
				sum *= num.get(i + 1);
			} else {
				sum /= num.get(i + 1);
			}
		}// 运算
		removeData(start, end);// 清除计算过的数据
		num.add(start, sum);// 更新数据
	}

	// 得到乘除开始位置
	private static int getStart() {
		int len = symbol.size();
		int start = len;
		for (int i = symbolStart; i < len; i++) {
			if (symbol.get(i) > 2) {
				start = i;
				// System.out.println("start:" + start);
				break;
			}
		}// 找到开始
		return start;
	}

	// 得到乘除结束位置
	private static int getEnd(int start) {
		int end = start + 1;
		int len = symbol.size();
		for (int i = start; i < len; i++) {
			if (symbol.get(i) < 3) {
				end = i;
				// System.out.println("end:" + end);
				break;
			}
		}// 找到结尾
		return end;
	}

	// 清除计算过的数据
	private static void removeData(int start, int end) {
		for (int i = 0; i < end - start + 1; i++) {
			num.remove(start);
		}
		for (int i = 0; i < end - start; i++) {
			symbol.remove(start);
		}
	}

	// TODO计算加减
	private static int calA_S() {
		int sum = num.get(0);
		int len = symbol.size();
		for (int i = 0; i < len; i++) {
			if (1 == symbol.get(i)) {
				sum += num.get(i + 1);
			} else {
				sum -= num.get(i + 1);
			}
		}
		return sum;
	}
}
