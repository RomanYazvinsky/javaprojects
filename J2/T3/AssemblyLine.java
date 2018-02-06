
public class AssemblyLine implements IAssemblyLine {
	LineStep1 bodyLine;
	LineStep2 chassisLine;
	LineStep3 engineLine;
	public AssemblyLine() {
		this.bodyLine = new LineStep1();
		this.chassisLine = new LineStep2();
		this.engineLine = new LineStep3();
		System.out.println("Assembly line starts");
	}
	public AssemblyLine(LineStep1 bodyLine, LineStep2 chassisLine, LineStep3 engineLine) {
		this.bodyLine = bodyLine;
		this.chassisLine = chassisLine;
		this.engineLine = engineLine;
		System.out.println("Assembly line starts");
	}
	public IProduct assembleProduct(IProduct product) {
		product.installFirstPart(bodyLine.buildProductPart());
		product.installSecondPart(chassisLine.buildProductPart());
		product.installThirdPart(engineLine.buildProductPart());
		System.out.println("Installation complete");
		return product;
	}

}
