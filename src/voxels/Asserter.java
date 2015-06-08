package voxels;

public class Asserter {

	public static void Assert(boolean assertion, String s){
		if (!assertion) {
			System.out.print("all is not well: " + s);
			try {
				throw new Exception ("yikes");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(21);
		}
	}
}
