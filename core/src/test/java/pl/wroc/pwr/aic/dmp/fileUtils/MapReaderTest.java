package pl.wroc.pwr.aic.dmp.fileUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class MapReaderTest {

	private static final String PATH = "src/test/resources/file";
	
	MapReader reader;
	
	@Before
	public void setup(){
		reader = new MapReader();
	}
	
	@Test
	public void shouldReadFile(){
		assertNotNull(reader.readFileAsScanner(PATH));
	}
	
	@Test
	public void shouldReturnNullWhenAskedForScannerAndNoFileRead(){
		assertNull(reader.readFileAsScanner("dummypath"));
	}
}
