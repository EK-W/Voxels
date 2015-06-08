package voxels_initial_setup;

public class Fake3DArray {
	private final int[] array;
	public final int xLength;
	public final int yLength;
	public final int zLength;
	public Fake3DArray(int x,int y, int z){
		xLength=x;
		yLength=y;
		zLength=z;
		array=new int[x*y*z];
	}
	public int getXLength(){
		return xLength;
	}
	public int getYLength(){
		return yLength;
	}
	public int getZLength(){
		return zLength;
	}	
	public void set(int xLoc, int yLoc, int zLoc, int set){
		array[((xLoc*yLength*zLength)+yLoc*zLength)+zLoc]=set;
	}
	public int get(int xLoc, int yLoc, int zLoc){
		return array[((xLoc*yLength*zLength)+yLoc*zLength)+zLoc];
	}
}
