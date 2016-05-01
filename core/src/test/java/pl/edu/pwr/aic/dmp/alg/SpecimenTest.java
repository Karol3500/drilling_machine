package pl.edu.pwr.aic.dmp.alg;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.mapUtils.CityReader;

public class SpecimenTest {

	@Test
	public void shouldGiveProperRouteLength(){
		CityReader cityReader = new CityReader();
		cityReader.loadFile("src/test/resources/att48.tsp");
		List<City> cities = cityReader.getMapClone();

		Specimen s = new Specimen(cities.subList(1, cities.size()), cities.get(0), 20);
		
		double routeLength = s.getRouteLength();
		
		assertEquals(162048.569842008, routeLength, 0.1d);
	}
	
}
