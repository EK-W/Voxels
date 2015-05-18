package voxels.map;

public class Direction {
    public static final int XNEG = 0, XPOS = 1, YNEG = 2, YPOS = 3, ZNEG=4, ZPOS=5;
    
    public static Coord3[] DirectionCoords = new Coord3[]{
        new Coord3(-1, 0, 0),
        new Coord3(1, 0, 0),
        new Coord3(0, -1, 0),
        new Coord3(0, 1, 0),
        new Coord3(0, 0, -1),
        new Coord3(0, 0, 1),
    };
    public static int oppositeOf(int dir){
    	switch(dir){
    		case XNEG: return XPOS;
    		case XPOS: return XNEG;
    		case YNEG: return YPOS;
    		case YPOS: return YNEG;
    		case ZNEG: return ZPOS;
    		case ZPOS: return ZNEG;
    		default: System.err.println("oppositeOf(direction) direction can only be 0-5 inclusive."); System.exit(1); break;
    	}
    	return -1;
    }
    public static boolean isX(int dir){
    	return dir==XNEG||dir==XPOS;
    }
    public static boolean isY(int dir){
    	return dir==YNEG||dir==YPOS;
    }
    public static boolean isZ(int dir){
    	return dir==ZNEG||dir==ZPOS;
    }
    
}
