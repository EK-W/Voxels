package voxels.generate;

import java.util.HashMap;

import voxels.map.BlockType;
import voxels.map.Coord3;

public class TerrainMap {
	
	private HashMap<Coord3, Chunk> chunks = new HashMap<>(WORLD_XLENGTH_CHUNKS * WORLD_HEIGHT_CHUNKS * WORLD_ZLENGTH_CHUNKS); 
//	 public static final int WORLD_HEIGHT_CHUNKS = 6;
//	 public static final int WORLD_XLENGTH_CHUNKS = 14;
//	 public static final int WORLD_ZLENGTH_CHUNKS = 14;
	 public static final int WORLD_HEIGHT_CHUNKS = 6;
	 public static final int WORLD_XLENGTH_CHUNKS = 4;
	 public static final int WORLD_ZLENGTH_CHUNKS = 4;
	 public static final int MAX_LIGHT = 15;
	public static final int MIN_LIGHT = 0;
	 TerrainDataProvider tdp = new TerrainDataProvider();
	 
	  // TODO: add a method 'worldHeightBlocks()' // return world_height_chunks * Chunk.YLENGTH;
	    public static int worldHeightBlocks(){
	    	return WORLD_HEIGHT_CHUNKS*Chunk.YLENGTH;
	    }
	    public static int worldXLengthBlocks(){
	    	return WORLD_XLENGTH_CHUNKS*Chunk.XLENGTH;
	    }
	    public static int worldZLengthBlocks(){
	    	return WORLD_ZLENGTH_CHUNKS*Chunk.ZLENGTH;
	    }
	    
	    public Chunk createOrLookupChunkAt(Coord3 chunkCo) {
	        if(chunkCo.y>=WORLD_HEIGHT_CHUNKS||chunkCo.y<0){
	        	return null;
	        }
	    	Chunk chunk = chunks.get(chunkCo);
//	        if (chunk != null) {
//	        	System.out.println(chunkCo.toString());
//	        	System.exit(234);
//	        }
	        if(chunk==null){
	        	chunk=new Chunk(chunkCo,this);
	        	chunks.put(chunkCo, chunk);
	        }
	        return chunk;
	    }
	    
//	    public BlockType createOrLookupBlockAt(Coord3 global){
//	    	if(global.x<0||global.x>worldXLengthBlocks()||global.y<0||global.y>worldHeightBlocks()||global.z<0||global.z>worldZLengthBlocks()){
//	    		return BlockType.MISSINGNO;
//	    	}
//	    	Chunk chunkBlockIsIn = createOrLookupChunkAt(global);
//	    	if(chunkBlockIsIn==null)return BlockType.MISSINGNO;
//	    	
//	    	BlockType blockType = chunkBlockIsIn.blockAt(Chunk.toChunkLocalCoord(global));
//	    	if(blockType==BlockType.MISSINGNO)blockType = tdp.getBlockDataAtPosition(global);
//	    	chunkBlockIsIn.setBlockAt(Chunk.toChunkLocalCoord(global), blockType);
//	    	return blockType;
//	    }
	    
	public Chunk getChunk(Coord3 chunkPos){
		return new Chunk(chunkPos,this);
	}
	 
	public int getLight(Coord3 woco){
		if(getBlockAt(woco).isSolid()){
			return 0;
		}
		return MAX_LIGHT;	//TEMPORARY
	}
	
	public BlockType getBlockAt(Coord3 woco){
		if(woco.y<0||woco.y>=worldHeightBlocks()){
			return BlockType.MISSINGNO;
		}
		Coord3 chunkCo = Chunk.ToChunkPosition(woco.x,woco.y,woco.z);
		Chunk chunk = createOrLookupChunkAt(chunkCo);
		BlockType blockType = chunk.blockAt(Chunk.toChunkLocalCoord(woco));
		if(blockType==BlockType.MISSINGNO){
			blockType = tdp.getBlockDataAtPosition(woco);
			// TODO: set the blockType in chunk.setBlockAt() instead of BlockType.DIRT.ordinal()
//			chunk.setBlockAt(Chunk.toChunkLocalCoord(woco),BlockType.DIRT.ordinal());
			chunk.setBlockAt(Chunk.toChunkLocalCoord(woco), blockType); // **** MEDDLER
		}
		return blockType; // <--MEDDLER ***  BlockType.DIRT.ordinal(); // ******** HA: silliness!
	}
}
