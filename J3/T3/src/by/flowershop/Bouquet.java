package by.flowershop;

public class Bouquet {
	private CustomFlower[] flowers;
	private String name;
	private int price = 0;
	public Bouquet(String name, CustomFlower...flowers) {
		this.name = name;
		this.flowers = flowers;
		for (CustomFlower flower:flowers) {
			price += flower.getPrice();
		}
	}
	public int getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
	public String[] getFlowerNames() {
		String[] flowerNames = new String[flowers.length];
		for (int i = 0; i<flowers.length; i++) {
			flowerNames[i] = flowers[i].getFlowerName();
		}
		return flowerNames;
	}
}
