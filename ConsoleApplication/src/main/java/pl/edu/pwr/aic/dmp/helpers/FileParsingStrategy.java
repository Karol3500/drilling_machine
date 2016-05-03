package pl.edu.pwr.aic.dmp.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.pwr.aic.dmp.alg.utils.Parameters;

public abstract class FileParsingStrategy {
	
	public List<Parameters> parseLines(List<String> manyParamsLines){
		List<Parameters> params = new ArrayList<>();
		for(String line : manyParamsLines){
			Scanner s = new Scanner(line);
			List<String> parametersAsString = new ArrayList<>();
			while(s.hasNext()){
				parametersAsString.add(s.next());
			}
			s.close();
			params.add(parseParameters(parametersAsString));
		}
		return params;
	}

	protected abstract Parameters parseParameters(List<String> params);
	
}
