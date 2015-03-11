package pl.wroc.pwr.aic.dmp.mapUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MapReaderTest {

	private static final String PATH = "src/test/resources/file";
	
	@Test
	public void shouldReadFile(){
		assertNotNull(MapReader.readFileAsScanner(PATH));
	}
	
	@Test
	public void shouldReturnNullWhenAskedForScannerAndNoFileRead(){
		assertNull(MapReader.readFileAsScanner("dummypath"));
	}
}
