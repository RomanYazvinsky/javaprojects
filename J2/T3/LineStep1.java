
public class LineStep1 implements ILineStep {
	AutoBody firstPart;
	public LineStep1() {
		firstPart = new AutoBody();
	}
	public LineStep1(AutoBody firstPart) {
		this.firstPart = firstPart;
	}
	public IProductPart buildProductPart() {
		System.out.println(firstPart.create());
		return firstPart;
	}

}
