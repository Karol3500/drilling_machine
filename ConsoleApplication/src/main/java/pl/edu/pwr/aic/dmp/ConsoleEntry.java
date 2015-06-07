package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.utils.Machine;
import pl.edu.pwr.aic.dmp.utils.MachineParameters;

public class ConsoleEntry {

		public static void main(String[] args) {
			CityReader cr = new CityReader();
			cr.loadFile("/home/karol/Pobrane/resource/_maps_working/a280.tsp");
			
			MachineParameters mparams = new MachineParameters();
			mparams.setSaneDefaults();
			Machine m = new Machine(mparams);
			IwoParameters iwoParams = new IwoParameters();
			iwoParams.setSaneDefaults();
			
			IwoCore iwo = new IwoCore(cr.getMapClone(), false, iwoParams, m);
			
			iwo.run();
			iwo.showEffects();
		}

	//	public static void main(String[] args) {
	//		CityReader cr = new CityReader();
	//		cr.loadFile("/home/karol/Pobrane/resource/_maps_working/a280.tsp");
	//		
	//		MachineParameters mparams = new MachineParameters();
	//		mparams.setSaneDefaults();
	//		Machine m = new Machine(mparams);
	//		GAParameters gaParams = new GAParameters();
	//		gaParams.setSaneDefaults();
	//		
	//		GACore ga = new GACore(cr.getMapClone(), true, gaParams, m);
	//		
	//		ga.run();
	//		
	//		ga.showEffects();
	//	}

//	public static void main(String[] args) {
//		CityReader cr = new CityReader();
//		cr.loadFile("/home/karol/Pobrane/resource/_maps_working/att13.tsp");
//
//		MachineParameters mparams = new MachineParameters();
//		mparams.setSaneDefaults();
//		Machine m = new Machine(mparams);
//		SAParameters saParams = new SAParameters();
//		saParams.setSaneDefaults();
//
//		SACore sa = new SACore(cr.getMapClone(), saParams, false, m);
//		sa.run();
//
//		sa.showEffects();
//	}
	
//	public static void main(String[] args) {
//		CityReader cr = new CityReader();
//		cr.loadFile("/home/karol/Pobrane/resource/_maps_working/a280.tsp");
//
//		MachineParameters mparams = new MachineParameters();
//		mparams.setSaneDefaults();
//		Machine m = new Machine(mparams);
//		RandomParameters p = new RandomParameters();
//		p.setCyclesNumber(1000);
//
//		RandomCore rc = new RandomCore(cr.getMapClone(), p, false, m);
//		rc.run();
//
//		rc.showEffects();
//	}
}
