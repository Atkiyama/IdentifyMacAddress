package others;

public class Friis {
	public static void main(String[] args) {
		double rssi = -60;
		
		double TxPower = rssi + 20 * Math.log10(0.1);
		double d = Math.pow(10,((TxPower - rssi) / 20));
		System.out.println(d);

	}

}
