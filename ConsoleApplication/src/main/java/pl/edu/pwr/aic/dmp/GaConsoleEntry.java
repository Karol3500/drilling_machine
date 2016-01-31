package pl.edu.pwr.aic.dmp;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.alg.utils.MachineParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.utils.ExperimentSetup;
import pl.edu.pwr.aic.dmp.utils.Machine;

public class GaConsoleEntry {

		public static void main(String[] args){
			MachineParameters mparams = new MachineParameters();
			mparams.setSaneDefaults();
			Machine m = new Machine(mparams);
	
			GaParameters gaParams = new GaParameters();
			gaParams.setSaneDefaults();
			Core alg = new GACore();
			List<Parameters> params = new ArrayList<Parameters>();
			params.add(gaParams);
	
			ExperimentSetup setup = new ExperimentSetup();
			setup.setAlgorithm(alg);
			setup.setDrillChangeInterval(m.getDrillChangeInterval());
			setup.setFilePath("src/main/resources/maps_working/a280.tsp");
			setup.setParamsList(params);
			setup.setNumberOfUnitExperimentRepetitions(4);
			setup.setupExperimentEnvironment();
	
			setup.performExperiment();
		}
}
