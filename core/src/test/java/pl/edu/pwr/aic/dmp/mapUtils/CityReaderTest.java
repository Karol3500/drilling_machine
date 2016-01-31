package pl.edu.pwr.aic.dmp.mapUtils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.alg.City;


public class CityReaderTest {
	public static final String MAP_PATH_WITH_13_POINTS = "src/test/resources/att13.tsp";
	
	@Test
	public void shouldReturnFalseIfNoMapRead(){
		CityReader r = new CityReader();
		
		assertFalse(r.isMapLoaded());
	}
	
	@Test
	public void shouldReadMapFromFile(){
		CityReader cr = getCityReaderWithFileRead(MAP_PATH_WITH_13_POINTS);
		
		assertTrue(cr.isMapLoaded());
		assertTrue(cr.getNumberOfCities() == 13);
	}
	
	@Test
	public void shouldReturnCopyOfTheList(){
		CityReader cr = getCityReaderWithFileRead(MAP_PATH_WITH_13_POINTS);
		List<City> map = cr.getMapClone();
		
		assertFalse(map == cr.map);
		assertFalse(map.isEmpty());
		assertTrue(map.get(0).equals(cr.map.get(0)));
	}
	
	private CityReader getCityReaderWithFileRead(String path) {
		CityReader cr = new CityReader();
		cr.loadFile(path);
		return cr;
	}
}
