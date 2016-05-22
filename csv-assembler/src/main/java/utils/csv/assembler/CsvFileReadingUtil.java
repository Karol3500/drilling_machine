package utils.csv.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvFileReadingUtil {
	public List<String> readFileLines(String filePath){
		try {
			return Files.readAllLines(Paths.get(filePath));

		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
	}

	public int findLineWithText(String regex, List<String> file){
		Pattern pattern = Pattern.compile(regex);
		for(int i=0; i<file.size(); i++){
			Matcher m = pattern.matcher(file.get(i));
			if(m.find()){
				return i;
			}
		}
		return -1;
	}
	
	public int find2LinesWithTextAndGetSecondIndex(String regex1, String regex2, List<String> file){
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		
		for(int i=0; i<file.size() -1; i++){
			Matcher m = pattern1.matcher(file.get(i));
			if(m.find()){
				Matcher m2 = pattern2.matcher(file.get(i+1));
				if(m2.find()){
					return i+1;
				}
			}
		}
		return -1;
	}
	
	public List<String> getLinesContainingAlgorithmParameters(List<String> lines){
		int parametersHeaderIndex = findLineWithText("Algorithm parameters:", lines);
		return getLinesFromGivenIndexToFirstEmptyLine(lines, parametersHeaderIndex);
	}

	public List<String> getLinesContainingAveragedResult(List<String> lines){
		int avgResHeaderIndex = findLineWithText("Averaged result:", lines);
		return getLinesFromGivenIndexToFirstEmptyLine(lines, avgResHeaderIndex);
	}
	
	public List<String> getLinesContainingResults(List<String> lines){
		int resultsHeaderIndex = find2LinesWithTextAndGetSecondIndex("", "Best route length", lines);
		return getLinesFromGivenIndexToFirstEmptyLine(lines, resultsHeaderIndex);
	}

	private List<String> getLinesFromGivenIndexToFirstEmptyLine(List<String> lines, int headerIndex) {
		List<String> linesBelowHeader = new ArrayList<String>();
		for(int i=headerIndex; i< lines.size(); i++){
			if(lines.get(i).equals("")){
				break;
			}
			linesBelowHeader.add(lines.get(i));
		}
		return linesBelowHeader;
	}
}
