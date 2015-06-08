package voxels_initial_setup;

public class Fake2DArray {
	private final int[] array;
	public final int xLength;
	public final int yLength;
	public Fake2DArray(int x,int y){
		xLength=x;
		yLength=y;
		array=new int[x*y];
	}
	public int getXLength(){
		return xLength;
	}
	public int getYLength(){
		return yLength;
	}
	public void set(int xLoc, int yLoc, int set){
		array[(xLoc*yLength)+yLoc]=set;
	}
	public int get(int xLoc, int yLoc){
		return array[(xLoc*yLength)+yLoc];
	}
}
