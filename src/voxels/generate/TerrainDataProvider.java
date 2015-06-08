package voxels.generate;

import voxels.map.BlockType;
import voxels.map.Coord3;

import com.sudoplay.joise.module.Module;
import com.sudoplay.joise.module.ModuleAutoCorrect;
import com.sudoplay.joise.module.ModuleBasisFunction.BasisType;
import com.sudoplay.joise.module.ModuleBasisFunction.InterpolationType;
import com.sudoplay.joise.module.ModuleFractal;
import com.sudoplay.joise.module.ModuleFractal.FractalType;
import com.sudoplay.joise.module.ModuleGradient;
import com.sudoplay.joise.module.ModuleScaleDomain;
import com.sudoplay.joise.module.ModuleScaleOffset;
import com.sudoplay.joise.module.ModuleSelect;
import com.sudoplay.joise.module.ModuleTranslateDomain;

/*
 * This class's job is to tell
 * whoever wants to know
 * what block-type exists
 * at a given x, y and z
 */
 
public class TerrainDataProvider {
    private static long seed = -21234;
    private Module module;
    private int preset =TerrainModulePresets.PLANE;
 
    
    public TerrainDataProvider() {
    	setUpModule();
    }


    private void setUpModule() {
    	 switch(preset){
    	 	case TerrainModulePresets.ISLAND: module = TerrainModulePresets.getAndSetupIslandModule(seed); break;
    	 	case TerrainModulePresets.PLANE: module = TerrainModulePresets.getAndSetupPlanesModule(seed); break;
    	 	default: TerrainModulePresets.getAndSetupIslandModule(seed);
    	 }
    }

    
    /*
     * PROBLEM: two methods that duplicate each other
     * choose one, delete other -- MEDDLER
     */
    public BlockType getBlockDataAtPosition(Coord3 global) {
//    	double xx = global.x / (double) WORLD_HEIGHT_BLOCKS;
//    	double yy = global.y / (double) WORLD_HEIGHT_BLOCKS;
//    	double zz = global.z / (double) WORLD_HEIGHT_BLOCKS;
//    	int blockTypeAtXYZ = module.get(xx,yy,zz); // returns grass, air, stone etc. values
//    	return blockTypeAtXYZ;
//    	if (global.y > 10) return BlockType.AIR;
//    	if (global.y == 2) return BlockType.GRASS;
    	if (false) {
	    	if (global.y < 1) {
	    		return BlockType.DIRT;
	    	}
//	    	if (global.y > TerrainMap.worldHeightBlocks() - 2) {
//	    		return BlockType.AIR;
//	    	}
	    	return global.x %16+global.z%16 + global.y%16 < 16 ? BlockType.GRASS : BlockType.AIR;
    	}
    	else {
    		return BlockType.findBlock((int) module.get(global.x/(double)TerrainMap.worldHeightBlocks(), global.y/(double)TerrainMap.worldHeightBlocks(), global.z/(double)TerrainMap.worldHeightBlocks()));
    	}

    }
    
    /*
    public int getBlock(Coord3 global) {
//    	double xx = global.x / (double) WORLD_HEIGHT_BLOCKS;
//    	double yy = global.y / (double) WORLD_HEIGHT_BLOCKS;
//    	double zz = global.z / (double) WORLD_HEIGHT_BLOCKS;
//    	int blockTypeAtXYZ = module.get(xx,yy,zz); // returns grass, air, stone etc. values
//    	return blockTypeAtXYZ;
    	return 2;
    }
    */


//private int testPatternPlane(Coord3 global) {
//    if (global.y < 5) { // arbitrary plane height
//      return BlockType.GRASS.ordinal();
//    }
//    return BlockType.AIR.ordinal();
//}

}
