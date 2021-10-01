package graph.move;

public class Data {
	private int R;
	private int T;
	private int P;
	private double accuracy;

	public Data(String R,String T,String P, String accuracy) {
		this.R = Integer.parseInt(R);
		this.T = Integer.parseInt(T);
		this.P = Integer.parseInt(P);
		this.accuracy = Double.parseDouble(accuracy);
	}

	public int getR() {
		return R;
	}

	public int getT() {
		return T;
	}

	public int getP() {
		return P;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public int getByParameta(Parameta parameta) {
		if(parameta == Parameta.R)
			return R;
		else if(parameta == Parameta.T)
			return T;
		else if(parameta == Parameta.P)
			return P;
		else {
			System.out.println("引数エラー");
			System.exit(0);
		}
		return 0;
	}
}
