
public class T3 {
	public static void main(String[] args) {
		Product auto = new Product();
		AutoBody autoBody = new AutoBody();
		AutoChassis autoChassis = new AutoChassis();
		AutoEngine autoEngine = new AutoEngine();
		LineStep1 bodyLine = new LineStep1(autoBody);
		LineStep2 chassisLine = new LineStep2(autoChassis);
		LineStep3 engineLine = new LineStep3(autoEngine);
		AssemblyLine assemblyLine = new AssemblyLine(bodyLine, chassisLine, engineLine);
		auto = (Product) assemblyLine.assembleProduct(auto);
		if (auto.isAssembled())System.out.println("Car is ready. Do you have driving license? No? So, Disassembling begins");
		else System.out.println("Gook stole the part of car!");
	}
	//Alternative main
	/*public static void main(String[]args) {
		Product auto = new Product();
		AssemblyLine assemblyLine = new AssemblyLine();
		auto = (Product) assemblyLine.assembleProduct(auto);
		if (auto.isAssembled())System.out.println("Car is ready. Do you have driving license? No? So, Disassembling begins");
		else System.out.println("Gook stole the part of car!");
	}*/
}
