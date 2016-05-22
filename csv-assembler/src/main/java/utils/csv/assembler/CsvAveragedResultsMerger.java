package utils.csv.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvAveragedResultsMerger {
	
	CsvFileReadingUtil util;
	
	public CsvAveragedResultsMerger(){
		util = new CsvFileReadingUtil();
	}

	protected List<String> getMergedParamAndResultLines(List<String> paramLines, List<String> resultLines){
		List<String> merged = new ArrayList<String>();
		for(int i=0; i<paramLines.size()-3; i++){
			merged.add(paramLines.get(i));
		}
		merged.add(mergeLines(paramLines.get(paramLines.size()-3), resultLines.get(0)));
		merged.add(mergeLines(paramLines.get(paramLines.size()-2), resultLines.get(1)));
		merged.add(mergeLines(paramLines.get(paramLines.size()-1), resultLines.get(2)));
		return merged;
	}
	
	private String mergeLines(String line1, String line2) {
		return line1 + " | " + line2;
	}
	
	protected List<String> mergeLinesFromManyFiles(String filePaths[]){
		List<String> mergedFilesContent = new ArrayList<String>();
		for(String file : filePaths){
			List<String> fileContent = util.readFileLines(file);
			mergedFilesContent.addAll(getMergedParamAndResultLines(
					util.getLinesContainingAlgorithmParameters(fileContent),
					util.getLinesContainingAveragedResult(fileContent)));
			mergedFilesContent.add("");
		}
		if(mergedFilesContent.size()>1){
			mergedFilesContent.remove(mergedFilesContent.size()-1);
		}
		return mergedFilesContent;
	}
	
	public void mergeManyFilesAndSaveToFile(String[] filePaths) throws IOException{
		List<String> mergedFileContent = mergeLinesFromManyFiles(filePaths);
		Files.write(Paths.get("merged.csv"), mergedFileContent);
	}

	public static void main(String args[]) throws IOException{
		CsvAveragedResultsMerger reader = new CsvAveragedResultsMerger();
		reader.mergeManyFilesAndSaveToFile(args);
	}
}
