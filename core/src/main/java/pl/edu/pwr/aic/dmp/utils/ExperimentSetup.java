package pl.edu.pwr.aic.dmp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.City;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;

public class ExperimentSetup {

	private List<ExperimentResult> results;
	private Core algorithm;
	private String filePath;
	private String mapName;
	private CityReader cr;
	private List<Parameters> paramsList;
	private int drillChangeInterval;
	private int numberOfUnitExperimentRepetitions;

	public void setupExperimentEnvironment(String filePath, Core algorithm, List<Parameters> params, 
			int numberOfUnitExperimentRepetitions){
		setupMap(filePath);
		this.algorithm = algorithm;
		this.paramsList = params;
		this.numberOfUnitExperimentRepetitions = numberOfUnitExperimentRepetitions;
		results = new ArrayList<ExperimentResult>();
	}
	public void setupExperimentEnvironment(){
		setupMap(filePath);
		results = new ArrayList<ExperimentResult>();
	}
	
	public void performExperiment(){
		for(Parameters p : paramsList){
			ExperimentResult res = new ExperimentResult(p, mapName, algorithm.getClass());
			algorithm.setAlgorithmParameters(p);
			for(int i=0; i<numberOfUnitExperimentRepetitions; i++){
				algorithm.setCities(cr.getMapClone());
				algorithm.setDrillChangeInterval(drillChangeInterval);
				algorithm.start();
				res.getResults().add(algorithm.getResult());
			}
			results.add(res);
		}
	}
	
	private void setupMap(String filePath){
		cr = new CityReader();
		cr.loadFile(filePath);
		this.filePath = filePath;
		mapName = getMapNameFromPath(filePath);
	}
	
	protected String getMapNameFromPath(String fp) {
		String[] c = fp.split(File.separatorChar=='\\' ? "\\\\" : File.separator);
		return c[c.length-1];
	}

	public List<City> getMap(){
		return cr.getMapClone();
	}

	public List<ExperimentResult> getResults() {
		return results;
	}
	public Core getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(Core algorithm) {
		this.algorithm = algorithm;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<Parameters> getParamsList() {
		return paramsList;
	}
	public void setParamsList(List<Parameters> paramsList) {
		this.paramsList = paramsList;
	}
	public int getNumberOfUnitExperimentRepetitions() {
		return numberOfUnitExperimentRepetitions;
	}
	public void setNumberOfUnitExperimentRepetitions(
			int numberOfUnitExperimentRepetitions) {
		this.numberOfUnitExperimentRepetitions = numberOfUnitExperimentRepetitions;
	}
	public int getDrillChangeInterval() {
		return drillChangeInterval;
	}
	public void setDrillChangeInterval(int drillChangeInterval) {
		this.drillChangeInterval = drillChangeInterval;
	}
}
