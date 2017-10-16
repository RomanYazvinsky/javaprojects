public class Library {
	public static int getThreeDigitNumber()
	{
		return new java.util.Random().nextInt(900) + 100;
	}
	public static int getGreatestDigit(int number) {
		int digit = 0;
		do{
			if (digit < number%10)digit = number%10;
			number /= 10;
		} while (number > 0);
		return digit;
	}
}
