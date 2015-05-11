package voxels;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

//import org.lwjgl.opengl.DisplayMode;


import voxels.generate.Chunk;
import voxels.generate.TerrainMap;
import voxels.map.Coord3;
import voxels.map.Direction;
import voxels.meshconstruction.BlockMeshUtil;
import voxels.meshconstruction.MeshSet;

import com.google.common.primitives.Ints;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;

/**
 * Created by didyouloseyourdog on 8/1 0/14.
 */
public class VoxelWorld extends SimpleApplication
{
    public static MaterialLibrarian materialLibrarian;
    TerrainMap map = new TerrainMap();
    @Override
    public void simpleUpdate(float secondsPerFrame) {}

    @Override
    public void simpleInitApp() {
        materialLibrarian = new MaterialLibrarian(assetManager);
        setUpTheCam();
        makeSomeChunks();
        this.viewPort.setBackgroundColor(new ColorRGBA(0, 0.75f, 1f, 1));
      //  addTestBlockFace();
    }


    private void makeSomeChunks() {
        //Coord3 chunkCoord = new Coord3(0,0,0); // arbitrary chunk coord
       for(int x=0;x<TerrainMap.WORLD_XLENGTH_CHUNKS;x++){
    	   for(int z=0;z<TerrainMap.WORLD_ZLENGTH_CHUNKS;z++){
    		   for(int y=0; y<TerrainMap.WORLD_HEIGHT_CHUNKS;y++){
	    		   Chunk chunk = map.createOrLookupChunkAt(new Coord3(x,y,z));
	    		   chunk.brain.meshDirty = true;
	    		   rootNode.attachChild(chunk.brain.getGeometry());
    		   }
    	   }
       }
        // TODO: implement chunk brain class and its getGeometry() method (see below in the blog post)

        Mesh texturedTestMesh = new Mesh(); 
        Geometry someTexturedGeometry = new Geometry("textured test geom", texturedTestMesh); 
        someTexturedGeometry.setMaterial(materialLibrarian.getTexturedBlockMaterial());
        rootNode.attachChild(someTexturedGeometry);
        attachCoordinateAxes(Vector3f.ZERO);
    }
    /*
     * TEST METHOD. THIS CODE WILL MOVE!!
     */
    //I wrote this
    private void addTestBlockFace() {
        MeshSet mset = new MeshSet(); // 1
        Coord3 pos = new Coord3(0,0,0); // 2
       // for(int i=0;i<6;i++) BlockMeshUtil.AddFaceMeshData(pos, mset, i, i);
        //createVoxel(mset,pos);
        //BlockMeshUtil.AddFaceMeshData(pos, mset, Direction.XPOS, 0);
        Mesh testMesh = new Mesh(); // 4
        ApplyMeshSet(mset, testMesh); // 5
        Geometry someGeometry = new Geometry("test geom", testMesh); // 6
        someGeometry.setMaterial(materialLibrarian.getBlockMaterial()); // 7
        rootNode.attachChild(someGeometry); // 8
        
        Mesh texturedTestMesh = new Mesh(); 
        ApplyMeshSet(mset, texturedTestMesh);
  
        Geometry someTexturedGeometry = new Geometry("textred test geom", texturedTestMesh); 
        someTexturedGeometry.setMaterial(materialLibrarian.getTexturedBlockMaterial());
        rootNode.attachChild(someTexturedGeometry);
        attachCoordinateAxes(Vector3f.ZERO);}
     /*
     * TEST METHOD. THIS CODE WILL MOVE!!
     */
    public static void ApplyMeshSet(MeshSet mset, Mesh bigMesh)
    {
        if (bigMesh == null) {
            System.out.println("Something is not right. This mesh is null. We should really be throwing an exception here.");
            return;
        }
        
        bigMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(mset.vertices.toArray(new Vector3f[0])));
        bigMesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(mset.uvs.toArray(new Vector2f[0])));
 
        /* google guava library helps with turning Lists into primitive arrays
        * "Ints" and "Floats" are guava classes.
        * */ 
        bigMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(Ints.toArray(mset.indices)));
    }
    private void makeADemoMeshAndAdditToTheRootNode() {
        Mesh m = new Cylinder(12,24,5,11);
        Geometry g = new Geometry("demo geom", m);
        g.setMaterial(materialLibrarian.getBlockMaterial());
        rootNode.attachChild(g);
        attachCoordinateAxes(Vector3f.ZERO);
    }
    private void attachCoordinateAxes(Vector3f pos){
		  Arrow arrow = new Arrow(Vector3f.UNIT_X);
		  arrow.setLineWidth(4); // make arrow thicker
		  putShape(arrow, ColorRGBA.Red).setLocalTranslation(pos);
		 
		  arrow = new Arrow(Vector3f.UNIT_Y);
		  arrow.setLineWidth(4); // make arrow thicker
		  putShape(arrow, ColorRGBA.Green).setLocalTranslation(pos);
		 
		  arrow = new Arrow(Vector3f.UNIT_Z);
		  arrow.setLineWidth(4); // make arrow thicker
		  putShape(arrow, ColorRGBA.Blue).setLocalTranslation(pos);
    }
    	 
	private Geometry putShape(Mesh shape, ColorRGBA color){
	  Geometry g = new Geometry("coordinate axis", shape);
	  Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	  mat.getAdditionalRenderState().setWireframe(true);
	  mat.setColor("Color", color);
	  g.setMaterial(mat);
	  rootNode.attachChild(g);
	  return g;
	}
    
    private void setUpTheCam() {
        flyCam.setMoveSpeed(30);
    }

    private static void ScreenSettings(VoxelWorld app, boolean fullScreen) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int SCREEN_MODE=0; // note: there are usually several, let's pick the first
        AppSettings settings = new AppSettings(true);
        float scale_screen = fullScreen ? 1f : .6f;
        Vector2f screenDims = new Vector2f((int)(modes[SCREEN_MODE].getWidth() * scale_screen ),(int)(modes[SCREEN_MODE].getHeight() * scale_screen ));
        settings.setResolution((int)screenDims.x,(int) screenDims.y);
        settings.setFrequency(modes[SCREEN_MODE].getRefreshRate());
        settings.setBitsPerPixel(modes[SCREEN_MODE].getBitDepth());
        if (fullScreen) {
            settings.setFullscreen(device.isFullScreenSupported());
        }
        app.setSettings(settings);
        app.setShowSettings(false);
    }

    /*******************************
     * Program starts here... ******
     *******************************/
    
    public static void main(String[] args) {
        VoxelWorld app = new VoxelWorld();
        ScreenSettings(app, false); //<--- call new method here
        app.start(); // start the game
    }

    public class MaterialLibrarian
    {
        private Material texturedBlockMaterial;
        private Material blockMaterial;
        private AssetManager _assetManager;

        public MaterialLibrarian(AssetManager assetManager_) {
            _assetManager = assetManager_;
        }

        public Material getBlockMaterial() {
            if (blockMaterial == null) {
                Material wireMaterial = new Material(assetManager, "/Common/MatDefs/Misc/Unshaded.j3md");
                wireMaterial.setColor("Color", ColorRGBA.Green);
                wireMaterial.getAdditionalRenderState().setWireframe(true);
                wireMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
                blockMaterial = wireMaterial;
            }
            return blockMaterial;
        }

        public Material getTexturedBlockMaterial() {
            if (texturedBlockMaterial == null) {
                Material mat = new Material(assetManager, "BlockTex2.j3md");
                Texture blockTex = assetManager.loadTexture("dog_64d_.jpg");
                blockTex.setMagFilter(Texture.MagFilter.Nearest);
                blockTex.setWrap(Texture.WrapMode.Repeat);
                mat.setTexture("ColorMap", blockTex);
                texturedBlockMaterial = mat;
            }
            return texturedBlockMaterial;
        }
    }


}
