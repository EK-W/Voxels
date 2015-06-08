package voxels.generate;

import voxels.map.BlockType;

import com.sudoplay.joise.module.Module;
import com.sudoplay.joise.module.ModuleAutoCorrect;
import com.sudoplay.joise.module.ModuleFractal;
import com.sudoplay.joise.module.ModuleGradient;
import com.sudoplay.joise.module.ModuleScaleDomain;
import com.sudoplay.joise.module.ModuleScaleOffset;
import com.sudoplay.joise.module.ModuleSelect;
import com.sudoplay.joise.module.ModuleTranslateDomain;
import com.sudoplay.joise.module.ModuleBasisFunction.BasisType;
import com.sudoplay.joise.module.ModuleBasisFunction.InterpolationType;
import com.sudoplay.joise.module.ModuleFractal.FractalType;

public class TerrainModulePresets {
	public static final int ISLAND = 0;
	public static final int PLANE = 1;
	
	
	
	public static Module getAndSetupIslandModule(long seed){
		 ModuleGradient groundGradient = new ModuleGradient();
         groundGradient.setGradient(0, 0, 0, 1, 0, 0);
         
         ModuleFractal mountainShapeFractal = new ModuleFractal(FractalType.FBM, BasisType.GRADIENT, InterpolationType.QUINTIC);
         mountainShapeFractal.setNumOctaves(8);
         mountainShapeFractal.setFrequency(1);
         mountainShapeFractal.setSeed(seed);
         
         ModuleAutoCorrect mountainAutoCorrect = new ModuleAutoCorrect(-1, 1);
         mountainAutoCorrect.setSource(mountainShapeFractal);
         mountainAutoCorrect.calculate();
         
         ModuleScaleOffset mountainScale = new ModuleScaleOffset();
         mountainScale.setScale(0.15);
         mountainScale.setOffset(0.15);
         mountainScale.setSource(mountainAutoCorrect);
         
         ModuleScaleDomain mountainYScale = new ModuleScaleDomain();
         mountainYScale.setScaleY(0.1);
         mountainYScale.setSource(mountainScale);
         
         ModuleTranslateDomain mountainTerrain = new ModuleTranslateDomain();
         mountainTerrain.setAxisYSource(mountainYScale);
         mountainTerrain.setSource(groundGradient);
         
         ModuleSelect groundSelect = new ModuleSelect();
         groundSelect.setHighSource(BlockType.AIR.getIndex());
         groundSelect.setLowSource(BlockType.GRASS.getIndex());
         groundSelect.setThreshold(0.1);
         groundSelect.setControlSource(mountainTerrain);
		return groundSelect;
	}
	
	public static Module getAndSetupPlanesModule(long seed){
		 ModuleGradient groundGradient = new ModuleGradient();
        groundGradient.setGradient(0, 0, 0, 2, 0, 0);
        
        ModuleFractal mountainShapeFractal = new ModuleFractal(FractalType.FBM, BasisType.GRADIENT, InterpolationType.QUINTIC);
        mountainShapeFractal.setNumOctaves(8);
        mountainShapeFractal.setFrequency(1);
        mountainShapeFractal.setSeed(seed);
        
        ModuleAutoCorrect mountainAutoCorrect = new ModuleAutoCorrect(-1, 1);
        mountainAutoCorrect.setSource(mountainShapeFractal);
        mountainAutoCorrect.calculate();
        
        ModuleScaleOffset mountainScale = new ModuleScaleOffset();
        mountainScale.setScale(0.15);
        mountainScale.setOffset(-0.05);
        mountainScale.setSource(mountainAutoCorrect);
        
        ModuleScaleDomain mountainYScale = new ModuleScaleDomain();
        mountainYScale.setScaleY(0.1);
        mountainYScale.setSource(mountainScale);
        
        ModuleTranslateDomain mountainTerrain = new ModuleTranslateDomain();
        mountainTerrain.setAxisYSource(mountainYScale);
        mountainTerrain.setSource(groundGradient);
        
        ModuleSelect groundSelect = new ModuleSelect();
        groundSelect.setHighSource(BlockType.AIR.getIndex());
        groundSelect.setLowSource(BlockType.GRASS.getIndex());
        groundSelect.setThreshold(0.1);
        groundSelect.setControlSource(mountainTerrain);
		return groundSelect;
	}
	
//	public static Module getAndSetupDeepOceanModule(long seed){
//		 ModuleGradient groundGradient = new ModuleGradient();
//       groundGradient.setGradient(0, 0, 0, 2, 0, 0);
//       
//       ModuleFractal mountainShapeFractal = new ModuleFractal(FractalType.FBM, BasisType.GRADIENT, InterpolationType.QUINTIC);
//       mountainShapeFractal.setNumOctaves(8);
//       mountainShapeFractal.setFrequency(1);
//       mountainShapeFractal.setSeed(seed);
//       
//       ModuleAutoCorrect mountainAutoCorrect = new ModuleAutoCorrect(-1, 1);
//       mountainAutoCorrect.setSource(mountainShapeFractal);
//       mountainAutoCorrect.calculate();
//       
//       ModuleScaleOffset mountainScale = new ModuleScaleOffset();
//       mountainScale.setScale(0.15);
//       mountainScale.setOffset(-0.05);
//       mountainScale.setSource(mountainAutoCorrect);
//       
//       ModuleScaleDomain mountainYScale = new ModuleScaleDomain();
//       mountainYScale.setScaleY(0.1);
//       mountainYScale.setSource(mountainScale);
//       
//       ModuleTranslateDomain mountainTerrain = new ModuleTranslateDomain();
//       mountainTerrain.setAxisYSource(mountainYScale);
//       mountainTerrain.setSource(groundGradient);
//       
//       ModuleSelect groundSelect = new ModuleSelect();
//       groundSelect.setHighSource(BlockType.AIR.getIndex());
//       groundSelect.setLowSource(BlockType.GRASS.getIndex());
//       groundSelect.setThreshold(0.1);
//       groundSelect.setControlSource(mountainTerrain);
//		return groundSelect;
//	}
}
