package pl.edu.pwr.aic.dmp.tuner;

import java.io.IOException;

import org.coinor.opents.MoveManager;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;
import pl.edu.pwr.aic.dmp.metaEA.SolutionsSingleton;
import pl.edu.pwr.aic.dmp.metaEA.export.CsvTuningExperimentResultExporter;

public abstract class AbstractTuningApplicationEntry {

	private static final int TABU_ITERATIONS_NUMBER = 10;
	private static String map;
	private static String fileName;
	private static AlgorithmTuner tuner;
	
	protected static void runExperiment(String[] args, String defaultMapFileName, String defaultResultFileName, Core alg, Parameters algParams, MoveManager mm)
			throws WriteException, IOException {
		commonInit(args, defaultMapFileName, defaultResultFileName);
		tuner = initTuner(alg, algParams, mm);
		SolutionsSingleton.setResultExporter(new CsvTuningExperimentResultExporter(fileName));
		tuner.performExperiment(TABU_ITERATIONS_NUMBER);
	}
	
	private static String getFileName(String[] args, String defaultResultFileName) {
		return args.length==2 ? args[1] : defaultResultFileName;
	}

	private static String getMap(String[] args, String defaultResultFileName) {
		return args.length >0 ? args[0] : defaultResultFileName;
	}

	private static void commonInit(String[] args, String degaultMapFileName, String defaultResultFileName) {
		map = getMap(args, degaultMapFileName);
		fileName = getFileName(args, defaultResultFileName);
	}

	private static AlgorithmTuner initTuner(Core alg, Parameters parameters, MoveManager mm) {
		return new AlgorithmTuner(alg,parameters,map,mm);
	}
}
