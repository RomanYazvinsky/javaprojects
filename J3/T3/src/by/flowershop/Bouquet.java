package by.flowershop;

public class Bouquet {
	private CustomFlower[] flowers;
	private int flowerCount = 0;
	private int price = 0;

	public Bouquet() {
		flowers = new CustomFlower[6];
	}

	private void calculatePrice() {
		price = 0;
		for (int i = 0; i < flowerCount; i++) {
			price += flowers[i].getPrice();
		}
	}

	public int getPrice() {
		return price;
	}

	public String[] getFlowerNames() {
		String[] flowerNames = new String[flowerCount];
		for (int i = 0; i < flowerCount; i++) {
			flowerNames[i] = flowers[i].getFlowerName();
		}
		return flowerNames;
	}

	public void addFlower(CustomFlower flower) {
		if (flowerCount == flowers.length) {
			CustomFlower[] newFlowers = new CustomFlower[flowerCount + flowerCount / 3];
			System.arraycopy(flowers, 0, newFlowers, 0, flowerCount);
			newFlowers[flowerCount++] = flower;
			flowers = newFlowers;
		} else {
			flowers[flowerCount] = flower;
			flowerCount++;
		}
		calculatePrice();
	}
}
