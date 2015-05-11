package voxels.map;

import com.jme3.math.Vector3f;

public class Coord3 {
    public int x,y,z;
    
    public Coord3(int xx, int yy, int zz) {
        x = xx; y = yy; z = zz;
    }
    
    public Vector3f toVector3f() {
        return new Vector3f(x,y,z);
    }


      @Override
    public boolean equals(Object other) {
                //this is optional but a good idea: if the exactly same object return true
        if (this == other) return true; 
 
        if (other instanceof Coord3) { // if other is a Coord3 type
            return x == ((Coord3) other).x && y == ((Coord3) other).y && z == ((Coord3) other).z;
        }
        return false;
    }
      
    public Coord3(int a) { 
        this(a,a,a); 
    }


    @Override
    public int hashCode() {
        return (z & Integer.MIN_VALUE) |  // z negative ?
               ((y & Integer.MIN_VALUE) >>> 1 ) | // y negative ?
               ((x & Integer.MIN_VALUE) >>> 2 ) | // x negative ?
               ((z & 4095) << 17) | // first 12 binary digits
               ((y & 31) << 12) | // first 5 binary digits
               (x & 4095); // first 12 binary digits
    }
    public Coord3(double _x, double _y, double _z) { 
        this((int) _x, (int) _y,(int) _z); 
    }
 
    public Coord3 multy(Coord3 other) {
       return new Coord3(x*other.x,y*other.y,z*other.z);
    }
    public Coord3 multy(int i) {
    	return new Coord3(x*i,y*i,z*i);
    }
    public Coord3 divideBy(Coord3 other) {
    	return new Coord3(x/other.x,y/other.y,z/other.z);
    }
    public Coord3 divideBy(int i) {
    	return new Coord3(x/i,y/i,z/i);
    }
    public Coord3 add(Coord3 other) {
    	return new Coord3(x+other.x,y+other.y,z+other.z);       
    }
    public Coord3 minus (Coord3 other) {
    	return new Coord3(x-other.x,y-other.y,z-other.z);      
    }
    public boolean greaterThan(Coord3 other) {
        if(x>other.x&&y>other.y&&z>other.z){
        	return true;
        }else{
        	return false;
        }
    }
    @Override
    public String toString() {
    	return  super.toString() + "Coord3: x: " + x + " y: " + y + " z: " + z;
    }
}
