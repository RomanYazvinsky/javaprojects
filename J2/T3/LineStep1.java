
public class LineStep1 implements ILineStep {
	AutoBody firstPart;
	public LineStep1() {
		firstPart = new AutoBody();
	}
	public IProductPart buildProductPart() {
		System.out.println(firstPart.create());
		return firstPart;
	}

}
