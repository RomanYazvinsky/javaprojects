
public class T3 {
	public static void main(String[] args) {
		Product auto = new Product();
		LineStep1 bodyLine = new LineStep1();
		LineStep2 chassisLine = new LineStep2();
		LineStep3 engineLine = new LineStep3();
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
