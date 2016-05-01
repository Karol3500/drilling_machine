package pl.edu.pwr.aic.dmp.mapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import pl.edu.pwr.aic.dmp.alg.City;

public class FileParser {
	int numberOfCities;
	List<City> cityList;

	public boolean parseFile(Scanner scanner) {
		try{
			numberOfCities = getNumberOfCities(scanner);
			omitNodeCoordSection(scanner);
			readCities(scanner);
		}
		catch(NoSuchElementException ex){
			return false;
		}
		finally{
			scanner.close();
		}
		return true;
	}

	private int getNumberOfCities(Scanner scanner) {
		while (!scanner.next().contains("DIMENSION")) {

		}
		while (!scanner.hasNextInt()) {
			scanner.next();
		}
		return scanner.nextInt();
	}

	private void omitNodeCoordSection(Scanner scanner) {
		while (!scanner.next().contains("NODE_COORD_SECTION")) {

		}
		while (!scanner.hasNextInt()) {
			scanner.next();
		}
	}

	private void readCities(Scanner scanner) {
		cityList = new ArrayList<City>();
		for(int i=0;i<numberOfCities;i++){
			cityList.add(new City(scanner.nextInt(),scanner.nextDouble(), scanner.nextDouble()));
		}
	}
	
	public int getNumberOfCities() {
		return numberOfCities;
	}

	public List<City> getCityList() {
		return cityList;
	}
}
