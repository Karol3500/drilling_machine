package pl.edu.pwr.aic.dmp.utils.excel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import jxl.write.WriteException;

public class ExcelExporterUtilTest {

	private static final String TEST_FILE_NAME = "testFile";

	@Test
	public void shouldCreateSpreadsheet() throws Exception{
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
	
	private void createFile(String fileName) throws IOException, WriteException {
		new ExcelExporterUtil().createFile(fileName);
	}

	private Path getPath(String fileName) {
		return Paths.get(fileName + ExcelExporterUtil.FILE_EXT);
	}
	
	private Path getIncrementedPath(String fileName, int i) {
		return Paths.get(fileName + "_" + i + ExcelExporterUtil.FILE_EXT);
	}
	
	@After
	public void removeCreatedFiles() throws IOException{
		File[] files = new File(".").listFiles();
		for(File f : files){
			if(!f.isDirectory() && f.getName().matches(TEST_FILE_NAME + "\\w*" + ExcelExporterUtil.FILE_EXT)){
				f.delete();
			}
		}
	}
}
