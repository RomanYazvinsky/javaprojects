
public class Product implements IProduct {
	int modules;
	AutoBody body;
	AutoChassis chassis;
	AutoEngine engine;
	public Product() {
		System.out.println("Auto assembling starts");
		modules = 0;
	}
	public void installFirstPart(IProductPart firstPart) {
		body = (AutoBody) firstPart;
		System.out.println("Body is installed");
		modules++;
	}

	public void installSecondPart(IProductPart secondPart) {
		chassis = (AutoChassis) secondPart;
		System.out.println("Chassis is installed");
		modules++;
	}

	public void installThirdPart(IProductPart thirdPart) {
		engine = (AutoEngine) thirdPart;
		System.out.println("Engine is installed");
		modules++;
	}
	public Boolean isAssembled() {
		if (modules==3)return true;
		return false;
	}
}
