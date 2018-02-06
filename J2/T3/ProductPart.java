
public class ProductPart implements IProductPart {
	String name;
	Boolean isReady = false;
	@Override
	public String create() {
		// TODO Auto-generated method stub
		isReady = true;
		return name + " is created";
	}
	public Boolean isReady() {
		return isReady;
	}
}
