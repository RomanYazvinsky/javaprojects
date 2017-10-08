
public class LineStep3 implements ILineStep {
	AutoEngine secondPart;
	public LineStep3() {
		secondPart = new AutoEngine();
	}
	public IProductPart buildProductPart() {
		System.out.println(secondPart.create());
		return secondPart;
	}
}
