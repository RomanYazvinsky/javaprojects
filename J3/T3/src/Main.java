import by.flowershop.*;
import by.flowershop.flowers.*;

public class Main {
	public static void main(String[] args) {
		Bouquet bouquet = createBouquet();
		Printer.printComposition(bouquet.getFlowerNames());
		Printer.printPrice(bouquet.getPrice());
	}

	private static Bouquet createBouquet() {
		CustomFlower Romashka = new CustomFlower("Romashka", new Colour[] {Colour.WHITE, Colour.YELLOW}, 99999);
		Rose rose = new Rose();
		Bouquet bouquet = new Bouquet();
		bouquet.addFlower(new Cactus());
		bouquet.addFlower(Romashka);
		bouquet.addFlower(rose);
		return bouquet;

	}
}
