
public class Library {
	public static String[] toStringArray(int[] integerArray) {
		String[] stringArray = new String[integerArray.length];
		for (int i = 0; i<integerArray.length; i++) {
			stringArray[i] = Integer.toString(integerArray[i]);
		}
		return stringArray;
	}
	public static String toAdd(String[] stringArray) {
		int sum = 0;
		for (String number:stringArray) {
			sum+=Integer.parseInt(number);
		}
		return Integer.toString(sum);
	}
}
