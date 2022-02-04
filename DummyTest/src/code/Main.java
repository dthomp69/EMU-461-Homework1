package code;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Integer> what = new ArrayList<Integer>();
		what.add(1);
		what.add(2);
		what.add(3);
		what.add(4);

		System.out.println("What.size: " + what.size());

		System.out.println(what.get(0));
		System.out.println(what.get(1));
		System.out.println(what.get(2));
		System.out.println(what.get(3));
		System.out.println(what.get(4));

	}

}
