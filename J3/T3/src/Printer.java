
public class Printer {
	public static void printComposition(String[] flowers) {
		System.out.println("Bouquet consists of: ");
		for (String flower : flowers) {
			System.out.println(flower + " ");
		}
	}

	public static void printPrice(int price) {
		System.out.println("Bouquet price: " + price);
	}
}
