package configurations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Configurations {

	private static String getPathToPropertiesFile() {
		File jarFile = new File(Configurations.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String jarFileDirectory = jarFile.getParentFile().getPath();

		String propertiesFilePath = jarFileDirectory + "/config.properties";

		return propertiesFilePath;
	}



	/**
	 * Gets the path to the ProB directory stored on the config.properties file
	 * 
	 * @return path to ProB directory
	 */
	public static String getProBPath() {
		String probPath = null;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			probPath = config.getString("prob_path");
		} catch (ConfigurationException e) {
			System.err.println("Could not get ProB path: " + e.getMessage());
		}

		return probPath;
	}



	/**
	 * Sets the path to the ProB directory on the config.properties file
	 * 
	 * @param probPath
	 *            path to ProB directory
	 */
	public static void setProBPath(String probPath) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("prob_path", probPath);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set ProB path: " + e.getMessage());
		}
	}



	/**
	 * Sets the boolean values for the tool oracle strategies on the
	 * config.properties file
	 * 
	 * @param stateVariables
	 *            value for State Variables oracle strategy
	 * @param returnVariables
	 *            value for Return Variables oracle strategy
	 * @param invariantOK
	 *            value for Invariant OK oracle strategy
	 */
	public static void setOracleStrategy(boolean stateVariables, boolean returnVariables, boolean invariantOK) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("oracle_strategy_state_variables", stateVariables);
			config.setProperty("oracle_strategy_return_variables", returnVariables);
			config.setProperty("oracle_strategy_invariant_ok", invariantOK);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set oracles strategies: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the States Variables oracle strategy stored on the
	 * config.properties file
	 * 
	 * @return boolean value for State Variables Oracle Strategy
	 */
	public static boolean isStateVariablesActive() {
		boolean isStateVariablesActive = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			isStateVariablesActive = config.getBoolean("oracle_strategy_state_variables");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for oracle_strategy_state_variables: " + e.getMessage());
		}

		return isStateVariablesActive;
	}



	/**
	 * Gets the value for the Return Variables oracle strategy stored on the
	 * config.properties file
	 * 
	 * @return boolean value for Return Variables Oracle Strategy
	 */
	public static boolean isReturnVariablesActive() {
		boolean isReturnVariablessActive = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			isReturnVariablessActive = config.getBoolean("oracle_strategy_return_variables");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for oracle_strategy_return_variables: " + e.getMessage());
		}

		return isReturnVariablessActive;
	}



	/**
	 * Gets the value for the Invariant OK oracle strategy stored on the
	 * config.properties file
	 * 
	 * @return boolean value for Invariant OK Oracle Strategy
	 */
	public static boolean isInvariantOKActive() {
		boolean isInvariantOKActive = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			isInvariantOKActive = config.getBoolean("oracle_strategy_invariant_ok");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for oracle_strategy_invariant_ok: " + e.getMessage());
		}

		return isInvariantOKActive;
	}



	/**
	 * Sets the value for the Delete Temporary Files configuration on the
	 * config.properties file
	 * 
	 * @param deleteTempFiles
	 *            boolean value for the Delete Temporary Files
	 */
	public static void setDeleteTempFiles(boolean deleteTempFiles) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("delete_temp_files", deleteTempFiles);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for delete_temp_files: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the Delete Temporary Files configuration on the
	 * config.properties file
	 * 
	 * @return boolean value for Delete Temporary Files
	 */
	public static boolean isDeleteTempFiles() {
		boolean deleteTempFiles = true;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			deleteTempFiles = config.getBoolean("delete_temp_files");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for delete_temp_files: " + e.getMessage());
		}

		return deleteTempFiles;
	}



	/**
	 * Gets the value for the Automatic Oracle Evaluation configuration on the
	 * config.properties file
	 * 
	 * @return boolean value for Automatic Oracle Evaluation
	 */
	public static boolean isAutomaticOracleEvaluation() {
		boolean automaticOracleEvaluation = true;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			automaticOracleEvaluation = config.getBoolean("automatic_oracle_evaluation");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for automatic_oracle_evaluation: " + e.getMessage());
		}

		return automaticOracleEvaluation;
	}



	/**
	 * Gets the value for the MINTINT on the config.properties file
	 * 
	 * @return int value for minint
	 */
	public static int getMinIntProperties() {
		int minint = -5;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			minint = config.getInt("minint");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for minint: " + e.getMessage());
		}

		return minint;
	}



	/**
	 * Gets the value for the MAXINT on the config.properties file
	 * 
	 * @return int value for maxint
	 */
	public static int getMaxIntProperties() {
		int maxint = 5;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			maxint = config.getInt("maxint");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for maxint: " + e.getMessage());
		}

		return maxint;
	}



	/**
	 * Sets the value for the minint properties on the config.properties file
	 * 
	 * @param minint
	 *            int value for the minint properties
	 */
	public static void setMinIntProperties(int minint) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("minint", minint);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for minint: " + e.getMessage());
		}
	}



	/**
	 * Sets the value for the maxint properties on the config.properties file
	 * 
	 * @param maxint
	 *            int value for the maxint properties
	 */
	public static void setMaxIntProperties(int maxint) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("maxint", maxint);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for maxint: " + e.getMessage());
		}
	}



	/**
	 * Creates a Map that represents the preferences used by ProB Api
	 * 
	 * @return prob api preferences
	 */
	public static Map<String, String> getProBApiPreferences() {
		Map<String, String> probApiPrefs = new HashMap<String, String>();

		probApiPrefs.put("MAXINT", Integer.toString(getMaxIntProperties()));
		probApiPrefs.put("MININT", Integer.toString(getMinIntProperties()));
		probApiPrefs.put("CLPFD", "true");
		probApiPrefs.put("CHR", "false");
		probApiPrefs.put("SYMBOLIC", "true");
		probApiPrefs.put("KODKOD", Boolean.toString(getUseKodkod()));
		probApiPrefs.put("RANDOMISE_ENUMERATION_ORDER", Boolean.toString(getRandomiseEnumerationOrder()));

		return probApiPrefs;
	}



	/**
	 * Sets the value for the Automatic Oracle Evaluation configuration on the
	 * config.properties file
	 * 
	 * @param automaticOracleEvaluation
	 *            boolean value for the Automatic Oracle Evaluation
	 */
	public static void setAutomaticOracleEvaluation(boolean automaticOracleEvaluation) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("automatic_oracle_evaluation", automaticOracleEvaluation);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for automatic_oracle_evaluation: " + e.getMessage());
		}

	}



	/**
	 * Gets the value for the randomise_enumeration_order on the
	 * config.properties file
	 * 
	 * @return boolean value for randomise_enumeration_order
	 */
	public static boolean getRandomiseEnumerationOrder() {
		boolean randomiseEnumerationOrder = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			randomiseEnumerationOrder = config.getBoolean("randomise_enumeration_order");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for randomise_enumeration_order: " + e.getMessage());
		}

		return randomiseEnumerationOrder;
	}



	/**
	 * Sets the value for the randomise_enumeration_order on the
	 * config.properties file
	 * 
	 * @param randomiseEnumerationOrder
	 *            boolean value for randomise_enumeration_order
	 */
	public static void setRandomiseEnumerationOrder(boolean randomiseEnumerationOrder) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("randomise_enumeration_order", randomiseEnumerationOrder);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for randomise_enumeration_order: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the use_kodkod on the config.properties file
	 * 
	 * @return boolean value for use_kodkod
	 */
	public static boolean getUseKodkod() {
		boolean usekodkod = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			usekodkod = config.getBoolean("use_kodkod");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for use_kodkod: " + e.getMessage());
		}

		return usekodkod;
	}



	/**
	 * Sets the value for the use_kodkod on the config.properties file
	 * 
	 * @param useKodkod
	 *            boolean value for use_kodkod
	 */
	public static void setUseKodkod(boolean useKodkod) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("use_kodkod", useKodkod);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for use_kodkod: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the use_prob_api on the config.properties file
	 * 
	 * @return boolean value for use_prob_api
	 */
	public static boolean isUseProBApiToSolvePredicates() {
		boolean useprobapi = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			useprobapi = config.getBoolean("use_prob_api");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for use_prob_api: " + e.getMessage());
		}

		return useprobapi;
	}



	/**
	 * Sets the value for the use_prob_api on the config.properties file
	 * 
	 * @param useProBApi
	 *            boolean value for use_prob_api
	 */
	public static void setUseProBApiToSolvePredicates(boolean useProBApi) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("use_prob_api", useProBApi);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for use_prob_api: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the prob_timeout on the config.properties file
	 * 
	 * @return int value for prob_timeout
	 */
	public static int getProBTimeout() {
		int probTimeout = 2000;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			probTimeout = config.getInt("prob_timeout");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for prob_timeout: " + e.getMessage());
		}

		return probTimeout;
	}



	/**
	 * Sets the value for the prob_timeout on the config.properties file
	 * 
	 * @param probTimeout
	 *            int value for prob_timeout
	 */
	public static void setProBTimeout(int probTimeout) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("prob_timeout", probTimeout);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for prob_timeout: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the cbc_depth on the config.properties file
	 * 
	 * @return int value for cbc_depth
	 */
	public static int getCBCDepth() {
		int cbcDepth = 5;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			cbcDepth = config.getInt("cbc_depth");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for cbc_depth: " + e.getMessage());
		}

		return cbcDepth;
	}



	/**
	 * Sets the value for the cbc_depth on the config.properties file
	 * 
	 * @param cbcDepth
	 *            int value for cbc_depth
	 */
	public static void setCBCDepth(int cbcDepth) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("cbc_depth", cbcDepth);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for cbc_depth: " + e.getMessage());
		}
	}



	/**
	 * Gets the value for the find_preamble on the config.properties file
	 * 
	 * @return boolean value for find_preamble
	 */
	public static boolean isFindPreamble() {
		boolean findPreamble = false;

		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			findPreamble = config.getBoolean("find_preamble");
		} catch (ConfigurationException e) {
			System.err.println("Could not get value for find_preamble: " + e.getMessage());
		}

		return findPreamble;
	}



	/**
	 * Sets the value for the find_preamble on the config.properties file
	 * 
	 * @param findPreamble
	 *            boolean value for find_preamble
	 */
	public static void setFindPreamble(boolean findPreamble) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(getPathToPropertiesFile());
			config.setProperty("find_preamble", findPreamble);
			config.save();
		} catch (ConfigurationException e) {
			System.err.println("Could not set value for find_preamble: " + e.getMessage());
		}
	}

}