package benchmark;

import general.CombinatorialCriteria;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import parser.Machine;
import parser.Operation;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.ActiveClauseCoverage;
import testgeneration.coveragecriteria.BoundaryValueAnalysis;
import testgeneration.coveragecriteria.ClauseCoverage;
import testgeneration.coveragecriteria.CombinatorialClauseCoverage;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
import testgeneration.coveragecriteria.PredicateCoverage;
import tools.FileTools;
import configurations.Configurations;

public class Benchmark {

	private static CsvBeanWriter csvBeanWriter;
	private static int errorCount = 0;

	public static void main(String[] args) {
		errorCount = 0;
		configure();
		File benchmarkDirectory = new File("/Users/ernestocid/git/BenchmarkModelsTest2");

		try {
			startCSVWriterAndCreateCSVFile(benchmarkDirectory);

			List<File> machineFiles = getAllFilesRecursive(benchmarkDirectory);

			String[] header = new String[] { "operationName", 
												"equivalentClassesEachChoiceFeasible", 
												"equivalentClassesEachChoiceInfeasible",
												"equivalentClassesEachChoiceTime", 
												
												"equivalentClassesPairwiseFeasible", 
												"equivalentClassesPairwiseInfeasible", 
												"equivalentClassesPairwiseTime",
												
												"equivalentClassesAllCombinationsFeasible",
												"equivalentClassesAllCombinationsInfeasible",
												"equivalentClassesAllCombinationsTime",									
												
												"boundaryValuesEachChoiceFeasible",
												"boundaryValuesEachChoiceInfeasible",
												"boundaryValuesEachChoiceTime",
												
												"boundaryValuesPairwiseFeasible",
												"boundaryValuesPairwiseInfeasible",
												"boundaryValuesPairwiseTime",
												
												"boundaryValuesAllCombinationsFeasible",
												"boundaryValuesAllCombinationsInfeasible",
												"boundaryValuesAllCombinationsTime",
												
												"predicateCoverageFeasible",
												"predicateCoverageInfeasible",
												"predicateCoverageTime",
												
												"clauseCoverageFeasible",
												"clauseCoverageInfeasible",
												"clauseCoverageTime",
												
												"combinatorialClauseCoverageFeasible",
												"combinatorialClauseCoverageInfeasible",
												"combinatorialClauseCoverageTime",
												
												"activeClauseCoverageFeasible",
												"activeClauseCoverageInfeasible",
												"activeClauseCoverageTime"
			};

			writeCSVHeader(header);

			for (File machineFile : machineFiles) {
				Machine machine = new Machine(machineFile);
				List<Operation> operations = machine.getOperations();

				for (Operation operation : operations) {
					String operationName = operation.getMachine().getName() + "." + operation.getName();
					BenchmarkEntryBean entry = new BenchmarkEntryBean();

					entry.setOperationName(operationName);

					equivalentClassesEachChoice(operation, entry);
					equivalentClassesPairwise(operation, entry);
					equivalentClassesAllCombinations(operation, entry);

					boundaryValuesEachChoice(operation, entry);
					boundaryValuesPairwise(operation, entry);
					boundaryValuesAllCombinations(operation, entry);
					
					predicateCoverage(operation, entry);
					clauseCoverage(operation, entry);
					combinatorialClauseCoverage(operation, entry);
					activeClauseCoverage(operation, entry);

					writeEntry(header, entry);
				}
			}

			closeCSVWriter();
		} catch (Exception e) {
			String errorFilePath = benchmarkDirectory.getAbsolutePath() + "/error" + errorCount + ".txt";
			FileTools.createFileWithContent(errorFilePath, e.getMessage());
			errorCount++;
		}
	}

	
	
	private static void activeClauseCoverage(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite activeClauseCoverage = generateActiveClauseCoverageTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setActiveClauseCoverageFeasible(activeClauseCoverage.getTestCases().size());
		entry.setActiveClauseCoverageInfeasible(activeClauseCoverage.getUnsolvableFormulas().size());
		entry.setActiveClauseCoverageTime(executionTime);
	}
	
	
	
	private static void combinatorialClauseCoverage(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite combinatorialClauseCoverage = generateCombinatorialClauseCoverageTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setCombinatorialClauseCoverageFeasible(combinatorialClauseCoverage.getTestCases().size());
		entry.setCombinatorialClauseCoverageInfeasible(combinatorialClauseCoverage.getUnsolvableFormulas().size());
		entry.setCombinatorialClauseCoverageTime(executionTime);
	}
	


	private static void clauseCoverage(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite clauseCoverage = generateClauseCoverageTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setClauseCoverageFeasible(clauseCoverage.getTestCases().size());
		entry.setClauseCoverageInfeasible(clauseCoverage.getUnsolvableFormulas().size());
		entry.setClauseCoverageTime(executionTime);
	}



	private static void predicateCoverage(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite predicateCoverage = generatePredicateCoverageTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setPredicateCoverageFeasible(predicateCoverage.getTestCases().size());
		entry.setPredicateCoverageInfeasible(predicateCoverage.getUnsolvableFormulas().size());
		entry.setPredicateCoverageTime(executionTime);
	}



	private static BETATestSuite generatePredicateCoverageTestSuite(Operation operation) {
		CoverageCriterion ecpw = new PredicateCoverage(operation);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static BETATestSuite generateClauseCoverageTestSuite(Operation operation) {
		CoverageCriterion ecpw = new ClauseCoverage(operation);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static BETATestSuite generateCombinatorialClauseCoverageTestSuite(Operation operation) {
		CoverageCriterion ecpw = new CombinatorialClauseCoverage(operation);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static BETATestSuite generateActiveClauseCoverageTestSuite(Operation operation) {
		CoverageCriterion ecpw = new ActiveClauseCoverage(operation);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static void boundaryValuesAllCombinations(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite bvPairwise = generateBoundaryValuesAllCombinationsTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setBoundaryValuesAllCombinationsFeasible(bvPairwise.getTestCases().size());
		entry.setBoundaryValuesAllCombinationsInfeasible(bvPairwise.getUnsolvableFormulas().size());
		entry.setBoundaryValuesAllCombinationsTime(executionTime);
	}



	private static BETATestSuite generateBoundaryValuesAllCombinationsTestSuite(Operation operation) {
		CoverageCriterion ecpw = new BoundaryValueAnalysis(operation, CombinatorialCriteria.ALL_COMBINATIONS);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static void boundaryValuesPairwise(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite bvPairwise = generateBoundaryValuesPairwiseTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setBoundaryValuesPairwiseFeasible(bvPairwise.getTestCases().size());
		entry.setBoundaryValuesPairwiseInfeasible(bvPairwise.getUnsolvableFormulas().size());
		entry.setBoundaryValuesPairwiseTime(executionTime);
	}



	private static BETATestSuite generateBoundaryValuesPairwiseTestSuite(Operation operation) {
		CoverageCriterion ecpw = new BoundaryValueAnalysis(operation, CombinatorialCriteria.PAIRWISE);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static void boundaryValuesEachChoice(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite bvEachChoice = generateBoundaryValuesEachChoiceTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setBoundaryValuesEachChoiceFeasible(bvEachChoice.getTestCases().size());
		entry.setBoundaryValuesEachChoiceInfeasible(bvEachChoice.getUnsolvableFormulas().size());
		entry.setBoundaryValuesEachChoiceTime(executionTime);
	}



	private static BETATestSuite generateBoundaryValuesEachChoiceTestSuite(Operation operation) {
		CoverageCriterion ecpw = new BoundaryValueAnalysis(operation, CombinatorialCriteria.EACH_CHOICE);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static void equivalentClassesAllCombinations(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite equivalentClassesAllCombinations = generateEquivalentClassesAllCombinationsTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setEquivalentClassesAllCombinationsFeasible(equivalentClassesAllCombinations.getTestCases().size());
		entry.setEquivalentClassesAllCombinationsInfeasible(equivalentClassesAllCombinations.getUnsolvableFormulas().size());
		entry.setEquivalentClassesAllCombinationsTime(executionTime);
		
	}



	private static BETATestSuite generateEquivalentClassesAllCombinationsTestSuite(Operation operation) {
		CoverageCriterion ecpw = new EquivalenceClasses(operation, CombinatorialCriteria.ALL_COMBINATIONS);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static void closeCSVWriter() {
		if(csvBeanWriter != null) {
			try {
				csvBeanWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	private static void equivalentClassesPairwise(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite equivalentClassesPairwise = generateEquivalentClassesPairwiseTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setEquivalentClassesPairwiseFeasible(equivalentClassesPairwise.getTestCases().size());
		entry.setEquivalentClassesPairwiseInfeasible(equivalentClassesPairwise.getUnsolvableFormulas().size());
		entry.setEquivalentClassesPairwiseTime(executionTime);
	}



	private static BETATestSuite generateEquivalentClassesPairwiseTestSuite(Operation operation) {
		CoverageCriterion ecpw = new EquivalenceClasses(operation, CombinatorialCriteria.PAIRWISE);
		BETATestSuite testSuite = new BETATestSuite(ecpw);
		return testSuite;
	}



	private static void writeEntry(String[] header, BenchmarkEntryBean entry) {
		try {
			csvBeanWriter.write(entry, header);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private static void writeCSVHeader(String[] header) {
		try {
			csvBeanWriter.writeHeader(header);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private static void startCSVWriterAndCreateCSVFile(File benchmarkDirectory) {
		File benchmarkCSV = new File(benchmarkDirectory.getAbsolutePath() + "/benchmark.csv");

		try {
			csvBeanWriter = new CsvBeanWriter(new FileWriter(benchmarkCSV), CsvPreference.STANDARD_PREFERENCE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private static void equivalentClassesEachChoice(Operation operation, BenchmarkEntryBean entry) {
		long startTime = System.currentTimeMillis();

		BETATestSuite equivalentClassesEachChoice = generateEquivalentClassesEachChoiceTestSuite(operation);

		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime) / 1000;

		entry.setEquivalentClassesEachChoiceFeasible(equivalentClassesEachChoice.getTestCases().size());
		entry.setEquivalentClassesEachChoiceInfeasible(equivalentClassesEachChoice.getUnsolvableFormulas().size());
		entry.setEquivalentClassesEachChoiceTime(executionTime);
	}



	private static void configure() {
		Configurations.setDeleteTempFiles(true);
		Configurations.setUseKodkod(true);
		Configurations.setFindPreamble(true);
		Configurations.setAutomaticOracleEvaluation(false);
	}



	private static BETATestSuite generateEquivalentClassesEachChoiceTestSuite(Operation operation) {
		CoverageCriterion ecac = new EquivalenceClasses(operation, CombinatorialCriteria.EACH_CHOICE);
		BETATestSuite testSuite = new BETATestSuite(ecac);
		return testSuite;
	}



	private static List<File> getAllFilesRecursive(File file) {
		List<File> filesList = new ArrayList<File>();

		searchAndAddFiles(file, filesList);

		return filesList;
	}



	private static void searchAndAddFiles(File file, List<File> filesList) {
		if (file.isDirectory()) {
			File[] directoryFiles = file.listFiles();

			for (int i = 0; i < directoryFiles.length; i++) {
				searchAndAddFiles(directoryFiles[i], filesList);
			}

		} else {
			if (FilenameUtils.getExtension(file.getAbsolutePath()).equals("mch")) {
				filesList.add(file);
			}
		}
	}

}
