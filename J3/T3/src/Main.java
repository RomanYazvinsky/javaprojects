import by.flowershop.*;
import by.flowershop.flowers.*;
public class Main {
	public static void main(String[] args) {
		CustomFlower Romashka = new CustomFlower("Romashka", 99999, Colour.WHITE, Colour.YELLOW);
		CratevaReligiosa cratevaReligiosa = new CratevaReligiosa();
		Rose rose = new Rose();
		Bouquet bouquet = new Bouquet("For Aliaksandra", Romashka, cratevaReligiosa, rose, new Cactus());
		Printer.printBouquetName(bouquet.getName());
		Printer.printComposition(bouquet.getFlowerNames());
		Printer.printPrice(bouquet.getPrice());
	}
}
