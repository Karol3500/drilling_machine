package pl.edu.pwr.aic.dmp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.City;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class ExperimentSetup {

	List<ExperimentResult> results;
	Core algorithm;
	String filePath;
	String mapName;
	CityReader cr;
	List<Parameters> paramsList;
	int drillChangeInterval;
	int numberOfUnitExperimentRepetitions;

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
				algorithm.run();
				res.getResults().add(algorithm.getResult());
			}
			results.add(res);
		}
	}
	
	void setupMap(String filePath){
		cr = new CityReader();
		cr.loadFile(filePath);
		this.filePath = filePath;
		mapName = getMapNameFromPath(filePath);
	}
	
	protected String getMapNameFromPath(String fp) {
		String[] c = fp.split(File.separator);
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
