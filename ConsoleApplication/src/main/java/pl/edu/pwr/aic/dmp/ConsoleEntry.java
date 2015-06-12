package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.RandomCore;
import pl.edu.pwr.aic.dmp.alg.SACore;
import pl.edu.pwr.aic.dmp.alg.utils.GAParameters;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.alg.utils.RandomParameters;
import pl.edu.pwr.aic.dmp.alg.utils.SAParameters;
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
			
			GAParameters gaParams = new GAParameters();
			gaParams.setSaneDefaults();
			GACore ga = new GACore(cr.getMapClone(), true, gaParams, m);
			
			SAParameters saParams = new SAParameters();
			saParams.setSaneDefaults();
			SACore sa = new SACore(cr.getMapClone(), saParams, false, m);
			
			RandomParameters p = new RandomParameters();
			p.setCyclesNumber(1000);
			RandomCore rc = new RandomCore(cr.getMapClone(), p, false, m);
			
			//runAlg(iwo);
			//runAlg(ga);
			//runAlg(sa);
			runAlg(rc);
		}

		private static void runAlg(Core a) {
			a.run();
			a.showEffects();
		}
}
