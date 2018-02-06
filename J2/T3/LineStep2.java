
public class LineStep2 implements ILineStep {
	AutoChassis secondPart;
	public LineStep2() {
		secondPart = new AutoChassis();
	}
	public IProductPart buildProductPart() {
		System.out.println(secondPart.create());
		return secondPart;
	}
}
