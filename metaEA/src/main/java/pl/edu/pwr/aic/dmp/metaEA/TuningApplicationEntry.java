package pl.edu.pwr.aic.dmp.metaEA;

import java.io.IOException;

import org.coinor.opents.MoveManager;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.metaEA.export.CsvTuningExperimentResultExporter;

public abstract class TuningApplicationEntry {
	
	protected static final int TABU_ITERATIONS_NUMBER = 10;
	protected static String map;
	protected static String fileName;
	protected static AlgorithmTuner tuner;
	
	public static String getFileName(String[] args, String defaultResultFileName) {
		return args.length==2 ? args[1] : defaultResultFileName;
	}

	public static String getMap(String[] args, String defaultResultFileName) {
		return args.length >0 ? args[0] : defaultResultFileName;
	}

	protected static void commonInit(String[] args, String degaultMapFileName, String defaultResultFileName) {
		map = getMap(args, degaultMapFileName);
		fileName = getFileName(args, defaultResultFileName);
	}

	protected static AlgorithmTuner initTuner(Core alg, Parameters parameters, MoveManager mm) {
		return new AlgorithmTuner(alg,parameters,map,mm);
	}

	protected static void runExperiment(String[] args, String defaultMapFileName, String defaultResultFileName, Core alg, Parameters algParams, MoveManager mm)
			throws WriteException, IOException {
				commonInit(args, defaultMapFileName, defaultResultFileName);
				tuner = initTuner(alg, algParams, mm);
				SolutionsSingleton.setResultExporter(new CsvTuningExperimentResultExporter(fileName));
				tuner.performExperiment(TABU_ITERATIONS_NUMBER);
			}
}
