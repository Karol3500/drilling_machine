package pl.edu.pwr.aic.dmp.utils.export;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.utils.export.CsvExporterUtil;

public class CsvExporterUtilTest {

	private static final String TEST_FILE_NAME = "testFile";
	private static final String EXPECTED_RESULT = "first | second | 1 | 33.0";

	@Test
	public void shouldCreateFile() throws Exception{
		createFile(TEST_FILE_NAME);
		
		assertTrue(Files.exists(getPath(TEST_FILE_NAME)));
		assertFalse(Files.isDirectory(getPath(TEST_FILE_NAME)));
	}

	@Test
	public void shouldIncrementalyCreateFiles() throws WriteException, IOException{
		createFile(TEST_FILE_NAME);
		createFile(TEST_FILE_NAME);
		
		assertTrue(Files.exists(getPath(TEST_FILE_NAME)));
		assertTrue(Files.exists(getIncrementedPath(TEST_FILE_NAME, 1)));
	}
	
	@Test
	public void shouldWriteLineToFile() throws WriteException, IOException{
		List<Object> row = prepareRow();
		String fileName = createFile(TEST_FILE_NAME);
		CsvExporterUtil util = new CsvExporterUtil();
		
		util.writeRow(row, fileName);
		
		String firstLine = Files.readAllLines(Paths.get(fileName)).get(0);
		assertEquals(EXPECTED_RESULT, firstLine);
	}
	
	@Test
	public void shouldWriteMultipleLinesToFile() throws WriteException, IOException{
		List<Object> row = prepareRow();
		String fileName = createFile(TEST_FILE_NAME);
		CsvExporterUtil util = new CsvExporterUtil();
		
		util.writeRows(Arrays.asList(row,row), fileName);
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> expectedResult = Arrays.asList(EXPECTED_RESULT, EXPECTED_RESULT);
		assertEquals(expectedResult, lines);
	}

	private List<Object> prepareRow() {
		List<Object> row = new ArrayList<Object>();
		row.add("first");
		row.add("second");
		row.add(1);
		row.add(33d);
		return row;
	}
	
	private String createFile(String fileName) throws IOException, WriteException {
		return new CsvExporterUtil().createFile(fileName);
	}

	private Path getPath(String fileName) {
		return Paths.get(fileName + CsvExporterUtil.FILE_EXT);
	}
	
	private Path getIncrementedPath(String fileName, int i) {
		return Paths.get(fileName + "_" + i + CsvExporterUtil.FILE_EXT);
	}
	
	@After
	public void removeCreatedFiles() throws IOException{
		File[] files = new File(".").listFiles();
		if(files == null){
			return;
		}
		for(File f : files){
			if(!f.isDirectory() && f.getName().matches(TEST_FILE_NAME + "\\w*" + CsvExporterUtil.FILE_EXT)){
				f.delete();
			}
		}
	}
}
