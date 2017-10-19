
public class Main {
	public static void main(String[] args) {
		int[] numbers = { 1, 2, 3, 4, -6, 4, 12467, -12466 }; // == 9
		printArray(numbers);
		System.out.println("Result is " + Library.addUp(Library.toStringArray(numbers)));
	}

	private static void printArray(int[] numbers) {
		System.out.println("Number list: ");
		for (int i : numbers) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
