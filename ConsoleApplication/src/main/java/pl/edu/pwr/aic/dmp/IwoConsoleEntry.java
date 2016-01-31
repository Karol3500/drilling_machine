package pl.edu.pwr.aic.dmp;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.alg.utils.MachineParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.utils.ExperimentSetup;
import pl.edu.pwr.aic.dmp.utils.Machine;

public class IwoConsoleEntry {
		public static void main(String[] args){
			MachineParameters mparams = new MachineParameters();
			mparams.setSaneDefaults();
			Machine m = new Machine(mparams);
			
			IwoParameters iwoParams = new IwoParameters();
			iwoParams.setSaneDefaults();
			iwoParams.setMaxSpecimenInPopulation(500);
			iwoParams.setNumberOfIterations(300);
			IwoCore iwo = new IwoCore();
			List<Parameters> params = new ArrayList<Parameters>();
			params.add(iwoParams);
			
			ExperimentSetup setup = new ExperimentSetup();
			setup.setAlgorithm(iwo);
			setup.setDrillChangeInterval(m.getDrillChangeInterval());
			setup.setFilePath("src/main/resources/maps_working/a280.tsp");
			setup.setParamsList(params);
			setup.setNumberOfUnitExperimentRepetitions(4);
			setup.setupExperimentEnvironment();
			
			setup.performExperiment();
	}
}