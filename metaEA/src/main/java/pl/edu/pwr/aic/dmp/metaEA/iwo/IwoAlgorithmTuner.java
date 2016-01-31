package pl.edu.pwr.aic.dmp.metaEA.iwo;

import java.io.IOException;

import org.coinor.opents.MoveManager;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;
import pl.edu.pwr.aic.dmp.metaEA.SolutionsSingleton;
import pl.edu.pwr.aic.dmp.metaEA.export.CsvTuningExperimentResultExporter;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.IwoMoveManager;

public class IwoAlgorithmTuner extends AlgorithmTuner {

	public IwoAlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath, MoveManager moveManager) {
		super(algorithm, algParams, mapFilePath, moveManager);
	}

	public static void main(String[] args) throws WriteException, IOException{
		String map = "src/main/resources/maps_working/a280.tsp";
		
		AlgorithmTuner tuner = new AlgorithmTuner(new IwoCore(), new IwoParameters().setSaneDefaults(), map, new IwoMoveManager());
		tuner.performExperiment(10);
		CsvTuningExperimentResultExporter e = new CsvTuningExperimentResultExporter();
		e.createFile("IwoA280");
		e.writeMany(SolutionsSingleton.getResultsList());
//		tuner.printAllSolutions();
//		tuner.printBestFoundSolution();
	}
}
