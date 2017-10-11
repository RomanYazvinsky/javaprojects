
public class LineStep3 implements ILineStep {
	AutoEngine thirdPart;
	public LineStep3() {
		thirdPart = new AutoEngine();
	}
	public LineStep3(AutoEngine thirdPart) {
		this.thirdPart = thirdPart;
	}
	public IProductPart buildProductPart() {
		System.out.println(thirdPart.create());
		return thirdPart;
	}
}
