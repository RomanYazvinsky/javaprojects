import by.flowershop.*;
import by.flowershop.flowers.*;
public class Main {
	public static void main(String[] args) {
		CustomFlower Romashka = new CustomFlower("Romashka", 99999, Colour.WHITE, Colour.YELLOW);
		CratevaReligiosa cratevaReligiosa = new CratevaReligiosa();
		Rose rose = new Rose();
		Bouquet bouquet = new Bouquet("For Aliaksandra", Romashka, cratevaReligiosa, rose, new Cactus());
		String[] bouquetFlowers = bouquet.getFlowerNames();
		System.out.println("Bouquet name: " + bouquet.getName());
		System.out.println("Bouquet consists of: ");
		for (String flower : bouquetFlowers) {
			System.out.println(flower + " ");
		}
		System.out.println();
		System.out.println("Bouquet price: " + bouquet.getPrice());
	}
}
