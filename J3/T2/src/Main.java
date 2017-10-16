
public class Main {
	public static void main(String[] args) {
		int number = Library.getThreeDigitNumber();
		System.out.println("Random number: " + number);
		System.out.println("Greatest digit is " + Library.getGreatestDigit(number));
	}
}
