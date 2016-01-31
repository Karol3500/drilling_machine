package pl.edu.pwr.aic.dmp.alg.utils;

import java.util.List;

public interface Parameters {
	Parameters setSaneDefaults();
	List<? extends Object> getParameterNamesAsList();
	List<? extends Object> getParameterValuesAsList();
	Object clone();
}
