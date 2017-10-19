package by.flowershop;

public class Bouquet {
	private CustomFlower[] flowers;
	private int flowerCount = 0;
	private int price = 0;

	/*
	 * public Bouquet( CustomFlower...flowers) { this.flowers = flowers; for
	 * (CustomFlower flower:flowers) { price += flower.getPrice(); } }
	 */
	public Bouquet() {
		flowers = new CustomFlower[1];
	}

	public Bouquet(CustomFlower[] flowers) {
		this.flowers = flowers;
		calculatePrice();
		flowerCount = flowers.length;
	}

	private void calculatePrice() {
		price = 0;
		for (CustomFlower flower : flowers) {
			price += flower.getPrice();
		}
	}

	public int getPrice() {
		return price;
	}

	public String[] getFlowerNames() {
		String[] flowerNames = new String[flowers.length];
		for (int i = 0; i < flowers.length; i++) {
			flowerNames[i] = flowers[i].getFlowerName();
		}
		return flowerNames;
	}

	public void addFlower(CustomFlower flower) {
		if (flowerCount > 0) {
			CustomFlower[] newFlowers = new CustomFlower[flowerCount + 1];
			System.arraycopy(flowers, 0, newFlowers, 0, flowerCount);
			newFlowers[flowerCount++] = flower;
			flowers = newFlowers;
		} else {
			flowerCount++;
			flowers[0] = flower;
		}
		calculatePrice();
	}
}
