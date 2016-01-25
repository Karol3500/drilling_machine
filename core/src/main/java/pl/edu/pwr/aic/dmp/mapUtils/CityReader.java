package pl.edu.pwr.aic.dmp.mapUtils;

import java.util.InputMismatchException;
import java.util.LinkedList;
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
		}
		if(!fileParser.parseFile(scanner)){
			System.err.println("Failure: File malformed");
		}

		numberOfCities = fileParser.getNumberOfCities();
		map = fileParser.getCityList();
		System.out.println("Read " + numberOfCities + " points");
	}

	public boolean isMapLoaded(){
		return (map != null) && !map.isEmpty() ? true : false;
	}

	public List<City> getMapClone() {
		return new LinkedList<City>(map);
	}

	public int getNumberOfCities() {
		return numberOfCities;
	}
	
	public void clear(){
		map = null;
	}
}
