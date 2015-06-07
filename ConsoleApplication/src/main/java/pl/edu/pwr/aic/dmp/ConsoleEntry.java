package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.utils.GAParameters;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.utils.Machine;
import pl.edu.pwr.aic.dmp.utils.MachineParameters;

public class ConsoleEntry {
	
//	public static void main(String[] args) {
//		CityReader cr = new CityReader();
//		cr.loadFile("/home/karol/Pobrane/resource/_maps_working/a280.tsp");
//		
//		MachineParameters mparams = new MachineParameters();
//		mparams.setSaneDefaults();
//		Machine m = new Machine(mparams);
//		IwoParameters iwoParams = new IwoParameters();
//		iwoParams.setSaneDefaults();
//		
//		IwoCore iwo = new IwoCore(cr.getMapClone(), false, iwoParams, m);
//		
//		iwo.run();
//		
//		System.out.println(iwo.getMessage());
//	}
	
	public static void main(String[] args) {
		CityReader cr = new CityReader();
		cr.loadFile("/home/karol/Pobrane/resource/_maps_working/a280.tsp");
		
		MachineParameters mparams = new MachineParameters();
		mparams.setSaneDefaults();
		Machine m = new Machine(mparams);
		GAParameters gaParams = new GAParameters();
		gaParams.setSaneDefaults();
		
		GACore ga = new GACore(cr.getMapClone(), false, gaParams, m);
		
		ga.run();
		
		ga.showEffects();
	}

}
