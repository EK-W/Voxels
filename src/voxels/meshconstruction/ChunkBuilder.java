package voxels.meshconstruction;

import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import voxels.generate.Chunk;
import voxels.generate.TerrainDataProvider;
import voxels.generate.TerrainMap;
import voxels.map.BlockType;
import voxels.map.Coord3;
import voxels.map.Direction;

public class ChunkBuilder {
	public static int triIndex = 0;
	
	public static void BuildMesh(Chunk chunk, MeshSet mset, TerrainMap map)
    {
		TerrainMap terrainMap = chunk.getTerrainMap();
		triIndex = 0;
        for(int x=0;x<Chunk.XLENGTH;x++)
        	for(int y=0;y<Chunk.YLENGTH;y++)
        		for(int z=0;z<Chunk.ZLENGTH;z++){
        			Coord3 blockLoc = Chunk.ToWorldPosition(chunk.position).add(new Coord3(x,y,z));
        			
        			for(int dir = 0; dir<6;dir++){
        				if(terrainMap.getBlockAt(blockLoc).isSolid()&&IsFaceVisible(terrainMap,blockLoc,dir)){
        					BlockMeshUtil.AddBlockMeshLightData(blockLoc, mset, map, dir); //<---PROBLEMS. DOING THIS FOR ALL DIRS
        					BlockMeshUtil.AddFaceMeshData(blockLoc, mset, dir, triIndex, terrainMap.getBlockAt(blockLoc));
        					
        					//BlockMeshUtil.AddFaceMeshLightData(blockLoc, mset, dir, map);
        					triIndex += 4;
        				}
        			}	
        		}
    }
	
	public static void BuildMeshMeddlerVersion(Chunk chunk, MeshSet mset, TerrainMap map)
    {
		TerrainMap terrainMap = chunk.getTerrainMap();
		triIndex = 0;
        for(int x=0;x<Chunk.XLENGTH;x++)
        	for(int y=0;y<Chunk.YLENGTH;y++)
        		for(int z=0;z<Chunk.ZLENGTH;z++){
        			Coord3 blockLoc = Chunk.ToWorldPosition(chunk.position).add(new Coord3(x,y,z));
        			if(terrainMap.getBlockAt(blockLoc).isSolid()&&IsBlockVisible(terrainMap,blockLoc)){
        				for(int dir = 0; dir<6;dir++){
        					//if (IsFaceVisible // HARRY: I KNOW THE REASON FOR THE LIGHT ANOMOLIES....
        							// THIS IS THE START OF A FIX
        					BlockMeshUtil.AddBlockMeshLightData(blockLoc, mset, map,dir);
    						BlockMeshUtil.AddFaceMeshData(blockLoc, mset, dir, triIndex,terrainMap.getBlockAt(blockLoc));
    						//BlockMeshUtil.AddFaceMeshLightData(blockLoc, mset, dir, map);
    						triIndex += 4;
        				}
        			}
        		}
    }
	
 
    // SAME METHOD AS THE ONE NOW IN VOXELWORLD.JAVA. NOW IN A BETTER HOME
    public static void ApplyMeshSet(MeshSet mset, Mesh bigMesh)
    {
//        if (bigMesh == null) {
//        	System.out.println("Applymeshset bigmesh = null");
//            return;
//        } 
//        System.out.println("Applymeshset bigmesh != null");
            bigMesh.clearBuffer(Type.Position);
            bigMesh.clearBuffer(Type.TexCoord);
            bigMesh.clearBuffer(Type.Index);
            
            
            bigMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(mset.vertices.toArray(new Vector3f[0])));
            bigMesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(mset.uvs.toArray(new Vector2f[0])));
               
            // google guava library helps with turning Lists into primitive arrays
            // "Ints" and "Floats" are guava classes. 
            bigMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(Ints.toArray(mset.indices)));
            
        
        bigMesh.clearBuffer(Type.Color);
        bigMesh.setBuffer(Type.Color, 4, Floats.toArray(mset.colors));
 
        bigMesh.setDynamic();
//        bigMesh.setMode(Mesh.Mode.Triangles);
//        BoundingBox bbox = new BoundingBox(new Vector3f(0,0,0), new Vector3f(Chunk.XLENGTH, Chunk.YLENGTH, Chunk.ZLENGTH));
//        bigMesh.setBound(bbox);
        
        bigMesh.updateBound();
    }
 
    /*(
     * HARRY: something weird here: never returns true, it seems
     */
        // YOU'LL NEED THIS METHOD IN BUILDMESH()
    private static boolean IsFaceVisible(TerrainMap terrainMap, Coord3 globalCo, int direction) {
        // DA LOGIC:
                // THE FACE IS VISIBLE IF THE NEIGHBOR BLOCK IN 'DIRECTION' 
                // IS NOT SOLID
                // MAKE A NEW COORD3 THAT IS THE COORD FOR THIS NEIGHBOR: 
    	Coord3 facingBlock = globalCo.add(Direction.DirectionCoords[direction]);
                // EXAMPLE: globalCo is (3, 4, 6) AND direction is Direction.ZNEG: neighborCo = (3, 4, 5)
                // ASK terrainMap FOR THE BLOCK AT COORD neighborCo.
    	if(terrainMap.getBlockAt(facingBlock)==null)System.out.println("Yes is null");
    	if(terrainMap.getBlockAt(facingBlock).isSolid()){
    		return false;
    	}else{
    		return true;
    	}
                // byte blockType = terrainMap.lookupOrCreateBlock(neighborCo);\
    }
    public static void generateChunk(Chunk chunk){
    	for(int x=0;x<Chunk.XLENGTH;x++){
    		for(int z=0;z<Chunk.ZLENGTH;z++){
    			for(int y=0;y<Chunk.YLENGTH;y++){
//    				chunk.blockMap.set(x,y,z,chunk.terrainMap.createOrLookupBlockAt(Chunk.ToWorldPosition(chunk.position,new Coord3(x,y,z))).getIndex());
    				//chunk.blockMap.set(x,y,z,chunk.terrainMap.getBlockAt(Chunk.ToWorldPosition(chunk.position,new Coord3(x,y,z))).getIndex());
    				if(chunk.terrainMap.getBlockAt(Chunk.ToWorldPosition(chunk.position,new Coord3(x,y,z))).isSolid()){
    					chunk.lightMap.set(x, y, z, TerrainMap.MIN_LIGHT);
    				}else{
    					chunk.lightMap.set(x, y, z, TerrainMap.MAX_LIGHT);//TEMPORARY OMG
    				}
    			}
    			
    		}
    	}
    }
    private static boolean IsBlockVisible(TerrainMap terrainMap, Coord3 globalCo) {
    	for(int dir=0;dir<6;dir++){
    		if(IsFaceVisible(terrainMap,globalCo,dir)){
    			return true;
    		}
    	}
    	return false;
    }
}
