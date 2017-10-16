
public class Main {
	public static void main(String[] args) {
		int[] numbers = {1,2,3,4,-6,4, 12467, -12466}; // == 9
		System.out.println("Number list: ");
		for (int i : numbers) {
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println("Result is " + Library.toAdd(Library.toStringArray(numbers)));
	}
}
