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
}
