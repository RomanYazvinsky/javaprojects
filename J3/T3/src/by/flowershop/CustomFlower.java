package by.flowershop;

public class CustomFlower {
	private Colour[] colours;
	private int price;
	private String flowerName;
	public CustomFlower(String flowerName, int price, Colour... colours)
	{
		this.flowerName = flowerName;
		this.price = price;
		this.colours = colours;
	}
	public int getPrice() {
		return price;
	}
	public String getFlowerName() {
		return flowerName;
	}
}
