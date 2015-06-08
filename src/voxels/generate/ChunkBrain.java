package voxels.generate;

import voxels.Asserter;
import voxels.VoxelWorld;
import voxels.meshconstruction.ChunkBuilder;
import voxels.meshconstruction.MeshSet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;
 
public class ChunkBrain extends AbstractControl {
 
    public final Chunk chunk;
    public Mesh mesh;
    public boolean meshDirty;
    
    public ChunkBrain(Chunk _chunk) {
        chunk = _chunk;
    }
    @Override
    protected void controlRender(RenderManager arg0, ViewPort arg1) {
        // we'll never use this but it has to be here.
    }
 
    @Override
    protected void controlUpdate(float arg0) {
        // This is chunk brain's update method
        // Where it will check whether its dirty
        // if so, it build its mesh.
            if(meshDirty) {
                 buildMesh();
                 meshDirty = false;
             }
    }
 
    private void buildMesh() {
//		System.out.println("building the mesh! (chunkBrain)"); //meddler
    	MeshSet mset = new MeshSet();
        ChunkBuilder.BuildMesh(chunk, mset, chunk.terrainMap);
//        ChunkBuilder.ApplyMeshSet(mset, mesh);
        //if (mset.vertices.size() == 0) return;
        //Asserter.Assert(mset.vertices.size() > 0, "weird no vertices at all? guess its possible. this chunk at: " + chunk.position.toString() + "\n");
        ChunkBuilder.ApplyMeshSet(mset, getMesh()); //MEDDLER
        getGeometry().updateModelBound();
    }
    /*
     * Get the geometry we're attached to. Instantiate it lazily if needed.
     */
    public Geometry getGeometry() {
      
    	Geometry geom = (Geometry) getSpatial(); // an AbstractControl method
        if (geom == null) {
        	//System.out.println("getGeom = null");
            Mesh mesh = new Sphere(24, 24, 20); //***TEST/WANT-> new Mesh(); // placeholder mesh to be filled later
            
            mesh.setDynamic(); // hint to openGL that the mesh may change occasionally
            mesh.setMode(Mesh.Mode.Triangles); // GL draw mode 
            geom = new Geometry("chunk_geometry", mesh);
            //For the next line, make a change to the VoxelWorld class:
            //make its materialLibrarian public and static
            
            geom.setMaterial(VoxelWorld.materialLibrarian.getTexturedBlockMaterial()); //****WANT
          //  geom.setMaterial(VoxelWorld.materialLibrarian.getBlockMaterial()); //****TEST
            
            geom.addControl(this);
        }
        if (geom.getMesh() == null) {
        	System.out.println("argg...mesh null already!!");
        }
        return geom;
    }
    private Mesh getMesh() { //this is NOT the same as buildMesh!
        return getGeometry().getMesh(); 
    }
    
}







//package voxels.generate;
//
//import voxels.meshconstruction.ChunkBuilder;
//import voxels.meshconstruction.MeshSet;
//
//import com.jme3.renderer.RenderManager;
//import com.jme3.renderer.ViewPort;
//import com.jme3.scene.Mesh;
//import com.jme3.scene.control.AbstractControl;
//
//public class ChunkBrain extends AbstractControl{
//
//Chunk chunk;
//boolean meshDirty;
//	public ChunkBrain(Chunk _chunk) {
//		chunk = _chunk;
//	}
//	@Override
//	protected void controlRender(RenderManager arg0, ViewPort arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	protected void controlUpdate(float timePerFrame) {
//        if(meshDirty) {
//         buildMesh();
//         meshDirty = false;
//     }
// }
//	public boolean isMeshDirty() {
//		return meshDirty;
//	}
//	public void setMeshDirty(boolean meshDirty) {
//		this.meshDirty = meshDirty;
//	}
//	
//
//
//        private void buildMesh(boolean onlyLight, boolean onlyLiquid) {
//            MeshSet mset = new MeshSet();
//            ChunkBuilder.BuildMesh(chunk, mset);
//            ChunkBuilder.ApplyMeshSet(mset, mesh);
//    }
//
//}
