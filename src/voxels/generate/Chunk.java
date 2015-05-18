package voxels.generate;

import voxels.map.BlockType;
import voxels.map.Coord3;
import voxels_initial_setup.Fake3DArray;

/*
 * 
 */
public class Chunk {
    public static final int SIZE_X_BITS = 4;
    public static final int SIZE_Y_BITS = 4;
    public static final int SIZE_Z_BITS = 4;
    /*
     * bitwise multiplication by a power of 2. literally we are sliding 1 to the left by SIZE_X_BITS.
     * Or in other words, 1 becomes binary 10000 which is decimal 16  
     */
    public static final int XLENGTH = 1 << SIZE_X_BITS;
    public static final int YLENGTH = 1 << SIZE_Y_BITS;
    public static final int ZLENGTH = 1 << SIZE_Z_BITS;

	public final Coord3 position;
	public final TerrainMap terrainMap;
	public final ChunkBrain brain = new ChunkBrain(this);
	public Fake3DArray lightMap = new Fake3DArray(XLENGTH, YLENGTH, ZLENGTH);


    public Chunk(Coord3 _position, TerrainMap _terrainMap) {
        position = _position;
        terrainMap = _terrainMap;
    }

        /*
         * Chunk Position Methods
         * Chunk Positions are always the same as 
         * the position of the first block in the chunk (block at lower, xneg, zneg corner with local pos: (0,0,0))
         * divided by their lengths (16 in other words)
         * example: if ChunkPos is (1, 0, 0)
         * its "first" block is at world pos (16, 0, 0)
         * (16, 0, 0) would also be the chunk's "WorldPosition"
         * Conversely, a block with world pos (16, 0, 2)
         * would have chunk local pos (0, 0, 2)
         */
        public static Coord3 ToChunkPosition(int pointX, int pointY, int pointZ) {
        	int chunkX = pointX >> SIZE_X_BITS;
            int chunkY = pointY >> SIZE_Y_BITS;
            int chunkZ = pointZ >> SIZE_Z_BITS;
            return new Coord3(chunkX, chunkY, chunkZ);
        }
        public static Coord3 toChunkLocalCoord(Coord3 woco) {
            return toChunkLocalCoord(woco.x, woco.y, woco.z);
        }
        public static Coord3 toChunkLocalCoord(int x, int y, int z) {
        	int xlocal = x & (XLENGTH - 1);
            int ylocal = y & (YLENGTH - 1);
            int zlocal = z & (ZLENGTH - 1);

            return new Coord3(xlocal, ylocal, zlocal);
        }
        
        public static Coord3 ToWorldPosition(Coord3 chunkPosition) {
        	int worldX = (chunkPosition.x << SIZE_X_BITS);
            int worldY = (chunkPosition.y << SIZE_Y_BITS);
            int worldZ = (chunkPosition.z << SIZE_Z_BITS);
        	return new Coord3(worldX,worldY,worldZ); // HINT: use the method below to find the world position of a block at localPosition = (0,0,0)
        }
        public static Coord3 ToWorldPosition(Coord3 chunkPosition, Coord3 localPosition) {
            /*
             * Opposite of ToChunkPosition
             */
        	int worldX = (chunkPosition.x << SIZE_X_BITS) + localPosition.x;
            int worldY = (chunkPosition.y << SIZE_Y_BITS) + localPosition.y;
            int worldZ = (chunkPosition.z << SIZE_Z_BITS) + localPosition.z;
            return new Coord3(worldX, worldY, worldZ);
        }
		
		public Coord3 getPosition() {
			return position;
		}

		public TerrainMap getTerrainMap() {
			return terrainMap;
		}
		
		public BlockType blockAt(Coord3 local){
			return BlockType.MISSINGNO;//DUMB PLACEHOLDER METHOD
		}
		public void setBlockAt(Coord3 local, BlockType blockType){
			//do NOTHING
		}
		
    }

