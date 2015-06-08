package voxels.map;

import java.util.HashMap;

import com.jme3.math.Vector2f;

public class BlockType {
	private static HashMap<Integer,BlockType> blockIds = new HashMap<Integer,BlockType>();
	public final static BlockType MISSINGNO = new BlockType(false,null,null,null,null,null,null);
	public final static BlockType AIR = new BlockType(false,null,null,null,null,null,null);
	public final static BlockType GRASS = new BlockType(true,Faces.GRASS_SIDE,Faces.GRASS_SIDE,Faces.DIRT,Faces.GRASS_TOP,Faces.GRASS_SIDE,Faces.GRASS_SIDE);
	public final static BlockType DIRT = new BlockType(true,Faces.DIRT,Faces.DIRT,Faces.DIRT,Faces.DIRT,Faces.DIRT,Faces.DIRT);

//	private Vector2f texCoord;
	private Vector2f[] textureCoords = new Vector2f[6];
	private boolean solid;
	private int index;
	private static int indexCounter = 0;
	
	static {
		//If missingNo isnt 0, I can fix that here
	}
	
//	public static final int XNEG = 0, XPOS = 1, YNEG = 2, YPOS = 3, ZNEG=4, ZPOS=5;
	private BlockType(boolean isSolid,Vector2f XNEG,Vector2f XPOS,Vector2f YNEG,Vector2f YPOS,Vector2f ZNEG,Vector2f ZPOS){
		index=indexCounter;
		indexCounter++;
		solid = isSolid;
		textureCoords[0]=XNEG;
		textureCoords[1]=XPOS;
		textureCoords[2]=YNEG;
		textureCoords[3]=YPOS;
		textureCoords[4]=ZNEG;
		textureCoords[5]=ZPOS;
		System.out.println(this.toString());
		System.out.println(index);
		System.out.println(blockIds.toString());
		blockIds.put(index, this);
	}
	
//	public int ordinal(){
//		return ordinal;
//	}
	
	public  boolean isSolid(){
		return solid;
	}

	public static Vector2f GetFaceTextureCoords(BlockType toTest, int dir){
		return toTest.textureCoords[dir];
	}

	public int getIndex() {
		return index;
	}

	public static BlockType findBlock(int i) {
		return blockIds.get(i);
	}


}
