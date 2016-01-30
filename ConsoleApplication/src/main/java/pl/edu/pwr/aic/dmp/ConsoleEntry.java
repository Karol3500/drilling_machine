package pl.edu.pwr.aic.dmp;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.alg.utils.MachineParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.utils.ExperimentSetup;
import pl.edu.pwr.aic.dmp.utils.Machine;

public class ConsoleEntry {

	//		public static void main(String[] args) {
	//			CityReader cr = new CityReader();
	//			cr.loadFile("/home/karol/Pobrane/resource/_maps_working/a280.tsp");
	//			
	//			MachineParameters mparams = new MachineParameters();
	//			mparams.setSaneDefaults();
	//			Machine m = new Machine(mparams);
	//			
	//			IwoParameters iwoParams = new IwoParameters();
	//			iwoParams.setSaneDefaults();
	//			IwoCore iwo = new IwoCore(cr.getMapClone(), m);
	//			
	//			GAParameters gaParams = new GAParameters();
	//			gaParams.setSaneDefaults();
	//			GACore ga = new GACore(cr.getMapClone(), m);
	//			
	//			SAParameters saParams = new SAParameters();
	//			saParams.setSaneDefaults();
	//			SACore sa = new SACore(cr.getMapClone(), m);
	//			
	//			RandomParameters p = new RandomParameters();
	//			p.setCyclesNumber(1000);
	//			RandomCore rc = new RandomCore(cr.getMapClone(), m);
	//			
	//			runAlg(iwo);
	//			runAlg(ga);
	//			//runAlg(sa);
	//			runAlg(rc);
	//		}
	//
	//		private static void runAlg(Core a) {
	//			a.run();
	//			a.showEffects();
	//		}

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

//	public static void main(String[] args){
//		MachineParameters mparams = new MachineParameters();
//		mparams.setSaneDefaults();
//		Machine m = new Machine(mparams);
//
//		SaParameters saParams = new SaParameters();
//		saParams.setSaneDefaults();
//		Core alg = new SACore();
//		List<Parameters> params = new ArrayList<Parameters>();
//		params.add(saParams);
//
//		ExperimentSetup setup = new ExperimentSetup();
//		setup.setAlgorithm(alg);
//		setup.setDrillChangeInterval(m.getDrillChangeInterval());
//		setup.setFilePath("src/main/resources/maps_working/a280.tsp");
//		setup.setParamsList(params);
//		setup.setNumberOfUnitExperimentRepetitions(4);
//		setup.setupExperimentEnvironment();
//
//		setup.performExperiment();
//	}

	//	public static void main(String[] args){
	//		MachineParameters mparams = new MachineParameters();
	//		mparams.setSaneDefaults();
	//		Machine m = new Machine(mparams);
	//
	//		GaParameters gaParams = new GaParameters();
	//		gaParams.setSaneDefaults();
	//		Core alg = new GACore();
	//		List<Parameters> params = new ArrayList<Parameters>();
	//		params.add(gaParams);
	//
	//		ExperimentSetup setup = new ExperimentSetup();
	//		setup.setAlgorithm(alg);
	//		setup.setDrillChangeInterval(m.getDrillChangeInterval());
	//		setup.setFilePath("src/main/resources/maps_working/a280.tsp");
	//		setup.setParamsList(params);
	//		setup.setNumberOfUnitExperimentRepetitions(4);
	//		setup.setupExperimentEnvironment();
	//
	//		setup.performExperiment();
	//	}
}
