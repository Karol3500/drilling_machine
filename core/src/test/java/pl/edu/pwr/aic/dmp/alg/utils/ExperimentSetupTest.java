package pl.edu.pwr.aic.dmp.alg.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class ExperimentSetupTest {

	@Test
	public void shouldGetCorrectMapName(){
		ExperimentSetup s = new ExperimentSetup();
		String filePath = "fdsfsdfsd" + File.separator + "ghfdgdfgdfgdf" + File.separator + "mapa13.tsp";
		String expectedMapName = "mapa13.tsp";
		
		assertEquals(expectedMapName, s.getMapNameFromPath(filePath));
	}
}
