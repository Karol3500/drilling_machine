package pl.edu.pwr.aic.dmp.mapUtils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import pl.edu.pwr.aic.dmp.alg.City;

public class CityReader {

	protected List<City> map;
	private int numberOfCities = 0;

	public void loadFile(String filePath) throws InputMismatchException {
		FileParser fileParser = new FileParser();
		Scanner scanner = MapReader.readFileAsScanner(filePath);
		if(scanner == null){
			System.err.println("Failure: Cannot access file.");
			return;
		}
		if(!fileParser.parseFile(scanner)){
			System.err.println("Failure: File malformed.");
		}
		numberOfCities = fileParser.getNumberOfCities();
		map = fileParser.getCityList();
		if(map.size()<2){
			System.err.println("Failure: Map has less than 2 drilling points.");
		}
		System.out.println("Read " + numberOfCities + " points");
	}

	public boolean isMapLoaded(){
		return (map != null) && !map.isEmpty() ? true : false;
	}

	public List<City> getMapClone() {
		return new ArrayList<City>(map);
	}

	public int getNumberOfCities() {
		return numberOfCities;
	}
}
