package actions;

import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import criteria.AllCombinations;
import criteria.Criteria;
import criteria.EachChoice;
import criteria.Pairwise;
import parser.Characteristic;
import parser.Operation;
import testgeneration.BVBlockBuilder;
import testgeneration.Block;
import testgeneration.BlockBuilder;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;
import views.Application;





public class DisplayInfoAction extends AbstractAction {

	
	private Application application;
	private int operationIndex;
	private PartitionStrategy partitionStrategy;
	private CombinatorialCriterias combinatorialCriteria;
	
	
	public DisplayInfoAction(Application application) {
		this.application = application;
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setTestParameters();
		
		if(this.operationIndex != -1) {
			Operation operation = this.application.getMachine().getOperation(this.operationIndex);
			Partitioner partitioner = new Partitioner(operation);

			populateInputSpaceTable(operation, partitioner);
			
			List<List<String>> parametersInputValues = new ArrayList<List<String>>();
			
			if(this.partitionStrategy == PartitionStrategy.EQUIVALENT_CLASSES) {
				BlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
				parametersInputValues = parseHashToList(blockBuilder.getBlocks());
				populateCharacteristicsTable(operation, partitioner, blockBuilder);
			} else if (this.partitionStrategy == PartitionStrategy.BOUNDARY_VALUES) {
				BlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
				parametersInputValues = parseHashToList(blockBuilder.getBlocks());
				populateCharacteristicsTable(operation, partitioner, blockBuilder);
			}
			
			if(this.combinatorialCriteria == CombinatorialCriterias.ALL_COMBINATIONS) {
				Criteria criteria = new AllCombinations(parametersInputValues);
				application.setCombinations(criteria.getCombinationsAsStrings());
				populateCombinationsTable(operation, partitioner, criteria);
			} else if (this.combinatorialCriteria == CombinatorialCriterias.EACH_CHOICE) {
				Criteria criteria = new EachChoice(parametersInputValues);
				application.setCombinations(criteria.getCombinationsAsStrings());
				populateCombinationsTable(operation, partitioner, criteria);
			} else if (this.combinatorialCriteria == CombinatorialCriterias.PAIRWISE) {
				Criteria criteria = new Pairwise(parametersInputValues);
				application.setCombinations(criteria.getCombinationsAsStrings());
				populateCombinationsTable(operation, partitioner, criteria);
			}
			
		} else {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "You must select an operation.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	private void setTestParameters() {
		this.operationIndex = application.getOperationList().getSelectedIndex();
		this.application.setSelectedOperation(this.operationIndex);
		this.partitionStrategy = PartitionStrategy.get(this.application.getPartitionStrategy().getSelectedIndex());
		this.combinatorialCriteria = CombinatorialCriterias.get(application.getCombinatorialCriteria().getSelectedIndex());		
	}
	
	
	
	private void populateInputSpaceTable(Operation operation, Partitioner partitioner) {
		Set<String> operationInputSpace = partitioner.getOperationInputSpace();
		
		String[][] inputSpaceValues = new String[operationInputSpace.size()][];
		
		int i = 0;
		for (String value : operationInputSpace) {
			inputSpaceValues[i] = new String[] {value};
			i++;
		}
		
		JTable inputSpaceTable = application.getInputSpaceTable();
		
		String[] tableHeader = {"Input Space:"};
		
		TableModel dataModel = new DefaultTableModel(inputSpaceValues, tableHeader);
		inputSpaceTable.setModel(dataModel);
	}



	private List<List<String>> parseHashToList(Map<Characteristic, List<Block>> partitionBlocks) {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		for (Characteristic characteristic : partitionBlocks.keySet()) {
			List<String> blocksForCharacteristic = new ArrayList<String>();
			
			for (Block block : partitionBlocks.get(characteristic)) {
				blocksForCharacteristic.add(block.toString());
			}
			
			parameterInputValues.add(blocksForCharacteristic);
		}
		
		return parameterInputValues;
	}

	

	private void populateCharacteristicsTable(Operation operation, Partitioner partitioner, BlockBuilder blockBuilder) {
		Map<String, List<Block>> partitionBlocks = new HashMap<String, List<Block>>();
		
		if(this.partitionStrategy == PartitionStrategy.EQUIVALENT_CLASSES) {
			partitionBlocks = blockBuilder.getBlocksWithStringKeys();			
		} else if (this.partitionStrategy == PartitionStrategy.BOUNDARY_VALUES) {
			partitionBlocks = blockBuilder.getBlocksWithStringKeys();
		}
		
		Set<Characteristic> characteristics = partitioner.getOperationCharacteristics();
		
		int totalNumberOfBlocks = getTotalNumberOfBlocks(operation, partitioner, partitionBlocks);
		
		String[][] characteristicsAndBlocks = new String[totalNumberOfBlocks][];
		
		int line = 0;
		for(Characteristic charac : characteristics) {
			characteristicsAndBlocks[line] = new String[] { charac.toString(), partitionBlocks.get(charac.toString()).get(0).toString() };
			line++;
			for (int i = 1; i < partitionBlocks.get(charac.toString()).size(); i++) {
				characteristicsAndBlocks[line] = new String [] { " ", partitionBlocks.get(charac.toString()).get(i).toString()  };
				line++;
			}
		}
		
		JTable characteristicsAndBlocksTable = application.getCharacteristicsAndBlocksTable();
		
		String[] tableHeader = { "Characteristics:", "Blocks:" };
		TableModel dataModel = new DefaultTableModel(characteristicsAndBlocks, tableHeader);
		characteristicsAndBlocksTable.setModel(dataModel);
		
	}
	
	
	
	private int getTotalNumberOfBlocks(Operation operation, Partitioner partitionHandler, Map<String, List<Block>> partitionBlocks) {
		int totalNumberOfBlocks = 0;
		
		for(Characteristic charac : partitionHandler.getOperationCharacteristics()) {
			totalNumberOfBlocks += partitionBlocks.get(charac.toString()).size();
		}
		
		return totalNumberOfBlocks;
	}
	
	
	
	private void populateCombinationsTable(Operation operation, Partitioner partitionHandler, Criteria criteria) {
		Set<String> combinations = criteria.getCombinationsAsStrings();
		
		int totalCombinations = combinations.size();
		
		String[][] combinationsData = new String[totalCombinations][];
		
		int combinationCounter = 0;
		
		for (String combination : combinations) {
			combinationsData[combinationCounter] = new String[] { String.valueOf(combinationCounter + 1), combination };
			combinationCounter++;
		}
		
		JTable combinationsTable = application.getCombinationsTable();
		
		String[] tableHeader = { "Num:", "Combination:" };
		TableModel dataModel = new DefaultTableModel(combinationsData, tableHeader);
		
		combinationsTable.setModel(dataModel);
		
		TableColumn firstColumn = combinationsTable.getColumnModel().getColumn(0);
		firstColumn.setMaxWidth(45);
	}
	
}
