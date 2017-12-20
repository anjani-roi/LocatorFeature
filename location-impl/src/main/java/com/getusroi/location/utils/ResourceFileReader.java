package com.getusroi.location.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;



public final class ResourceFileReader {
	
	private static Properties sqlQueriesProperties = new Properties();
	private static Properties statusCodeDescProperties = new Properties();
	private static Properties resourceFileLocationsProperties = new Properties();
	
	private ResourceFileReader(){
		
	}
	
	static {
		loapProperties();
	}

	public static void loapProperties() {
		try {

			resourceFileLocationsProperties.load(ResourceFileReader.class.getResourceAsStream("/ResourceFileLocations.properties"));
			
			// Load SQL Queries Properties File
			
			sqlQueriesProperties.load(new FileInputStream(getResourceFileLocation("LOCATION_SQL_QUERIES_PROP_FILE")));
			
			// Load Status Code Description Properties File
			
			statusCodeDescProperties.load(new FileInputStream(getResourceFileLocation("STATUS_CODE_DESC_PROP_FILE")));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSQLQuery(String key) {
		return sqlQueriesProperties.getProperty(key);
	}
	
	public static String getStatusDesc(String key) {
		
		String statusDesc = statusCodeDescProperties.getProperty(key);
		
		if(statusDesc == null) {
			statusDesc = "Error occured during request processing, please contact administrator.";
		}
		
		return statusDesc;
	}
	
	public static String getResourceFileLocation(String key) {
		
		return resourceFileLocationsProperties.getProperty(key);
	}
	
}