package animator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.Operation;
import tools.FileTools;


public class PLReader {

	
	private File plFile;
	
	
	
	public PLReader(File plFile) {
		this.plFile = plFile;
	}

	
	
	public List<String> readValuesFor(String operationName) {
		String plFileText = FileTools.getFileContent(plFile);
		String regEx = "'" + operationName + "\\((.*?)\\)" + "'";

		List<String> values = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(plFileText);
		
		while(matcher.find()) {
			values.add(matcher.group(1));
		}
		
		return values;
	}
	
	
	
	public List<Map<String, String>> readParamValuesFor(Operation operation) {
		List<Map<String, String>> combinations = new ArrayList<Map<String, String>>();

		List<String> paramHeaders = readValuesFor(operation.getName());
		
		for(String value : paramHeaders) {
			Map<String, String> combination = mapParamNameToValue(operation.getParameters(), value);
			combinations.add(combination);
		}
		
		return combinations;
	}
	
	
	
	private Map<String, String> mapParamNameToValue(List<String> parameters, String paramText) {
		Map<String, String> combination = new HashMap<String, String>();
		List<String> paramValues = splitParameterValues(paramText);
		
		for(int paramIndex = 0; paramIndex < paramValues.size(); paramIndex++) {
			String paramName = parameters.get(paramIndex);
			String paramValue = paramValues.get(paramIndex);
			combination.put(paramName, paramValue);
		}
		
		return combination;
	}
	

	
	public List<String> splitParameterValues(String paramsText) {
		List<String> params = new ArrayList<String>();
		
		if(paramsText.length() == 0) {
			return params;
		} else {
			StringBuffer paramBuffer = new StringBuffer("");
			int openBracket = 0;
			int openSqureBracket = 0;
			boolean hasBracket = false;
			boolean hasSquareBracket = false;
			
			for(int i = 0; i < paramsText.length(); i++) {
				if(paramsText.charAt(i) == '{') {
					hasBracket = true;
					openBracket++;
				}
				
				if(paramsText.charAt(i) == '[') {
					hasSquareBracket = true;
					openSqureBracket++;
				}
				
				if(paramsText.charAt(i) == '}') {
					openBracket--;
				}
				
				if(paramsText.charAt(i) == ']') {
					openSqureBracket--;
				}
				
				if(paramsText.charAt(i) != ',') {
					paramBuffer.append(paramsText.charAt(i));
				} else {
					if((hasBracket && openBracket > 0) ||
						(hasSquareBracket && openSqureBracket > 0)) {
						paramBuffer.append(paramsText.charAt(i));
					} else {
						params.add(paramBuffer.toString().trim());
						paramBuffer = new StringBuffer("");	
					}
				}
			}
			
			params.add(paramBuffer.toString().trim());
			
			return params;	
		}
	}
}
