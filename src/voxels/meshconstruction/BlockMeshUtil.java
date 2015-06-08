package voxels.meshconstruction;

import java.util.Arrays;
import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import voxels.map.Direction;
import voxels.generate.TerrainMap;
import voxels.map.BlockType;
import voxels.map.Coord3;

public class BlockMeshUtil {
 /*
 * Make four verts,
 * 6 indices and 4 UV vector2s
 * add them to mesh Set
 */
public static void AddFaceMeshData(Coord3 pos, MeshSet mset, int direction, int triIndexStart,BlockType block)
{
    FaceVertices(mset, pos, direction);
    UVsForDirection(mset, direction,block);
    IndicesForDirection(mset, triIndexStart);
}


private static List<Vector2f> uvs = Arrays.asList(new Vector2f(0,0),new Vector2f(0,1),new Vector2f(1,1),new Vector2f(1,0));
private static final int[] FaceIndices = new int[] {0,3,2, 0,2,1};
public static Vector3f[][] faceVertices = new Vector3f[][] {
	//Xneg
	new Vector3f[] {
		new Vector3f(-0.5f, -0.5f, -0.5f),
		new Vector3f(-0.5f,  0.5f, -0.5f),
		new Vector3f(-0.5f,  0.5f,  0.5f),
		new Vector3f(-0.5f, -0.5f,  0.5f),	
	},
	//Xpos
	new Vector3f[] {
		new Vector3f(0.5f, -0.5f,  0.5f),
		new Vector3f(0.5f,  0.5f,  0.5f),
		new Vector3f(0.5f,  0.5f, -0.5f),
		new Vector3f(0.5f, -0.5f, -0.5f),
	},
	//Yneg
	new Vector3f[] {
			new Vector3f(-0.5f, -0.5f, -0.5f),
			new Vector3f(-0.5f, -0.5f,  0.5f),
			new Vector3f( 0.5f, -0.5f,  0.5f),
			new Vector3f( 0.5f, -0.5f, -0.5f),
		},
	//Ypos
	new Vector3f[] {
			new Vector3f(-0.5f, 0.5f, -0.5f),
			new Vector3f( 0.5f, 0.5f, -0.5f),
			new Vector3f( 0.5f, 0.5f,  0.5f),
			new Vector3f(-0.5f, 0.5f,  0.5f),
		},
		//Zneg
	new Vector3f[] {
			new Vector3f( 0.5f,-0.5f, -0.5f),
			new Vector3f( 0.5f, 0.5f, -0.5f),
			new Vector3f(-0.5f, 0.5f, -0.5f),
			new Vector3f(-0.5f,-0.5f, -0.5f),
		},
		//Zpos
	new Vector3f[] {
			new Vector3f(-0.5f,-0.5f, 0.5f),
			new Vector3f(-0.5f, 0.5f, 0.5f),
			new Vector3f( 0.5f, 0.5f, 0.5f),
			new Vector3f( 0.5f,-0.5f, 0.5f),
		}
};


private static void FaceVertices(MeshSet mset, Coord3 position, int dir ) {
    //FILL IN THE LOGIC
    //GET THE 1D VECTOR ARRAY AT INDEX 'DIR' IN 2D ARRAY faceVertices
        //FOR EACH OF THE FOUR CORNER VECTORS IN THIS ARRAY
        //MAKE A NEW VECTOR BY ADDING THE CORNER VECTOR
        //TO position (CONVERTED TO A VECTOR3F)
	
        //ADD THIS NEW VECTOR TO mset's vertices ARRAYLIST USING:
        mset.vertices.add(faceVertices[dir][0].add(position.toVector3f()));
        mset.vertices.add(faceVertices[dir][1].add(position.toVector3f()));
        mset.vertices.add(faceVertices[dir][2].add(position.toVector3f()));
        mset.vertices.add(faceVertices[dir][3].add(position.toVector3f()));
}
//private static void addVoxel(Coord3 position,MeshSet mset){
//	for(int i=0;i<6;i++){
//		FaceVertices(mset,position,i);
//	}
//	
//}
private static void UVsForDirection(MeshSet mset, int dir, BlockType block) {
//  mset.uvs.addAll(uvs); // DELETE THIS!
  /*
   * CHANGE THE X AND Y OF OFFSETSTART. X AND Y CAN EACH BE 0f, .25f, .5f, or .75f 
   * TRY ANY OF THE 16 COMBINATIONS: (.25f, .25f) for stone, (0f, .75f) for side grass
   * LOOK AT THE TEXTURE "dog_64d_.jpg" TO SEE THAT YOUR CHOICE CORRESPONDS TO THE 
   * TILE OFFSET INDICATED BY X AND Y. YOU MUST EDIT THE FRAGMENT SHADER, AS DESCRIBED HERE:
   * http://voxel.matthewpoindexter.com/class/block-faces-part-2-1-fixing-the-annoyingly-mis-aligned-texture/
   * FOR THIS TO WORK (WELL). 
   */
  Vector2f offsetStart = BlockType.GetFaceTextureCoords(block,dir);
  mset.uvs.addAll(Arrays.asList(
          offsetStart ,
          new Vector2f(offsetStart.x, offsetStart.y +.25f),
          new Vector2f(offsetStart.x + .25f, offsetStart.y + .25f),
                          new Vector2f(offsetStart.x + .25f, offsetStart.y)
          ));
}

    private static void IndicesForDirection(MeshSet mset, int triIndexStart) {
        for (int i : FaceIndices) {
//            mset.indices.add(i + (triIndexStart*4)); //
            mset.indices.add(i + (triIndexStart));
        }
    }
//	public static void AddFaceMeshLightData(Coord3 pos, MeshSet mset, int dir, TerrainMap map) {
//		for(Vector3f ver : faceVertices[dir]) {
//            float[] color = GetSmoothVertexLight(pos, ver, map, dir);
//            
//            //To Do: Get correct light for given corner of face or something
//            for (float c : color) {
//                mset.colors.add(c);
//            }
//        }
//		
//	}
	public static void AddBlockMeshLightData(Coord3 pos, MeshSet mset, TerrainMap map, int dir) {
//		float averageLightLevel = -1;
		//for(int dir=0;dir<6;dir++){
			for(Vector3f ver : faceVertices[dir]) {
	            float[] color = getFirstShaderColor(pos, ver, map, dir);
//	            if(averageLightLevel==-1){
//	            	averageLightLevel=color[3]/6;
//	            }else{
//	            	averageLightLevel=averageLightLevel+(color[3]/6);
//	            }
	            //To Do: Get correct light for given corner of face or something
	            for (float c : color) {
	                mset.colors.add(c);
	            }
	        }
		//}
//		map.
		
	}
	
	public static float[] getFirstShaderColor(Coord3 pos, Vector3f ver, TerrainMap map, int dir) {
		int dx = (int)Math.signum( ver.x );
		int dy = (int)Math.signum( ver.y );
		int dz = (int)Math.signum( ver.z );
		
		Coord3 a, b, c, d;
		if(Direction.isX(dir)) { // X
			a = pos.add(new Coord3(dx, 0,  0));
			b = pos.add(new Coord3(dx, dy, 0));
			c = pos.add(new Coord3(dx, 0,  dz));
			d = pos.add(new Coord3(dx, dy, dz));
		} else 
		if(Direction.isY(dir)) { // Y
			a = pos.add(new Coord3(0,  dy, 0));
			b = pos.add(new Coord3(dx, dy, 0));
			c = pos.add(new Coord3(0,  dy, dz));
			d = pos.add(new Coord3(dx, dy, dz));
		} else { // Z
			a = pos.add(new Coord3(0,  0,  dz));
			b = pos.add(new Coord3(dx, 0,  dz));
			c = pos.add(new Coord3(0,  dy, dz));
			d = pos.add(new Coord3(dx, dy, dz));
		}
		float average;
		if(map.getBlockAt(b).isSolid()&&map.getBlockAt(c).isSolid()){
			average = (map.getLight(a) + map.getLight(b) + map.getLight(c))/3;
		}else{
			average = (map.getLight(a) + map.getLight(b) + map.getLight(c)+map.getLight(d))/4;
		}
		if(average<TerrainMap.MIN_LIGHT){
			average=TerrainMap.MIN_LIGHT;
		}
		average/=TerrainMap.MAX_LIGHT;
		return new float[] { 0f, 0f, 0f, average };
	}
}
