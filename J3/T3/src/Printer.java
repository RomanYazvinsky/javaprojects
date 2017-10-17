
public class Printer {
	public static void printBouquetName(String bouquetName)
	{
		System.out.println("Bouquet name: " + bouquetName);
	}
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
