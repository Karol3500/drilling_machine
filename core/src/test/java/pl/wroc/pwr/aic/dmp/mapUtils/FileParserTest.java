package pl.wroc.pwr.aic.dmp.mapUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Scanner;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.City;

public class FileParserTest {

	private static final String PATH = "src/test/resources/fileGoodHeader";
	private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile";
	
	@Test
	public void shouldReturnFalseWhenCouldntParseFile(){
		FileParser fileParser = new FileParser();
		
		assertFalse(fileParser.parseFile(MapReader.readFileAsScanner(EMPTY_FILE_PATH)));
	}
	
	@Test
	public void shouldGetNumberOfCitiesProperlyWhenOneCity(){
		Scanner sc = MapReader.readFileAsScanner(PATH);
		FileParser fileParser = new FileParser();
		fileParser.parseFile(sc);
		
		assertEquals(1,fileParser.getNumberOfCities());
	}
	
	@Test
	public void shouldReadCityProperly(){
		Scanner sc = MapReader.readFileAsScanner(PATH);
		FileParser fileParser = new FileParser();
		City city = new City(1, 288, 149);
		
		fileParser.parseFile(sc);

		assertEquals(city,fileParser.getCityList().get(0));
	}
}
