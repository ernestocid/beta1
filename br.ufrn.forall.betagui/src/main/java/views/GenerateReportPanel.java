package views;
import general.CombinatorialCriteria;
import general.PartitionStrategy;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import configurations.Configurations;
import builders.CppUnitBuilder;
import builders.JUnitBuilder;
import builders.UnitTestBuilder;
import models.OracleStrategy;
import parser.Operation;
import reports.HTMLReport;
import reports.XMLReport;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.BoundaryValueAnalysis;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
import animator.ConventionTools;


public class GenerateReportPanel extends JPanel implements ActionListener,
		PropertyChangeListener {
	

	private JCheckBox htmlReportCheckBox;
	private JCheckBox xmlReportCheckBox;
	private JCheckBox javaTestScriptCheckBox;
	private JCheckBox cppTestScriptCheckBox;
	private JButton generateReportButton;
	private JProgressBar progressBar;
	private JTextArea taskOutput;
    private Task task;
    private Application application;
    
    
    private GenerateReportPanel(Application application) {
    	super(new BorderLayout());

    	this.application = application;
    	
    	htmlReportCheckBox = new JCheckBox("HTML Report");
    	htmlReportCheckBox.setSelected(true);
    	
    	xmlReportCheckBox = new JCheckBox("XML Report");
    	xmlReportCheckBox.setSelected(false);
    	
    	javaTestScriptCheckBox = new JCheckBox("Java Test Script");
    	javaTestScriptCheckBox.setSelected(false);
    	
    	cppTestScriptCheckBox = new JCheckBox("C++ Test Script");
    	cppTestScriptCheckBox.setSelected(false);
    	
        generateReportButton = new JButton("Generate Report");
        generateReportButton.setActionCommand("start");
        generateReportButton.addActionListener(this);
 
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        // progressBar.setStringPainted(true);
 
        taskOutput = new JTextArea(10, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
        
        JPanel panel = new JPanel();
        panel.add(htmlReportCheckBox);
        panel.add(xmlReportCheckBox);
        panel.add(javaTestScriptCheckBox);
        panel.add(cppTestScriptCheckBox);
        panel.add(generateReportButton);
        
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        add(panel, BorderLayout.PAGE_START);
        add(progressBar, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    
    
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!htmlReportCheckBox.isSelected() && !xmlReportCheckBox.isSelected()) {
			JOptionPane.showMessageDialog(null, 
					"Select at least one kind of report. ",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			htmlReportCheckBox.setEnabled(false);
			xmlReportCheckBox.setEnabled(false);
			generateReportButton.setEnabled(false);
			javaTestScriptCheckBox.setEnabled(false);
			cppTestScriptCheckBox.setEnabled(false);
	        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        //Instances of javax.swing.SwingWorker are not reusuable, so
	        //we create new instances as needed.
	        task = new Task(this.application);
	        task.addPropertyChangeListener(this);
	        task.execute();	
		}
		
	}
	
	
	
	/**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    public static void createAndShowGUI(Application application) {
        // Create and set up the window.
        JFrame frame = new JFrame("Generate Test Report");
        frame.setPreferredSize(new Dimension(700,300));
        frame.setBounds(220, 220, 1000, 600);
        
        // Create and set up the content pane.
        JComponent generateReportPanel = new GenerateReportPanel(application);
        generateReportPanel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(generateReportPanel);
 
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
    class Task extends SwingWorker<Void, Void> {
        
    	private Application application;
    	
    	public Task(Application application) {
    		this.application = application;
    	}
    	
    	
    	/*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            int progress = 0;

            //Initialize progress property.
            
            setProgress(0);
            taskOutput.setText("");
            
            // Gathering specification data
            
            taskOutput.append("Gathering specification data... \n");
            progress += 5;
            setProgress(Math.min(progress, 100));
            
            Operation operationUnderTest = this.application.getMachine().getOperation(this.application.getSelectedOperation());
    		
    		// Generating test suite
            
            taskOutput.append("Generating test suite... \n");
            progress += 55;
            setProgress(Math.min(progress, 100));
    		
            CoverageCriterion coverageCriterion;
            
            if(PartitionStrategy.get(application.getChosenPartitionStrategy()) == PartitionStrategy.EQUIVALENT_CLASSES) {
            	coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.get(application.getChosenCombinatorialCriteria()));
            } else if (PartitionStrategy.get(application.getChosenPartitionStrategy()) == PartitionStrategy.BOUNDARY_VALUES) {
            	coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriteria.get(application.getChosenCombinatorialCriteria()));
            } else {
            	coverageCriterion = null;
            }
            
            
    		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

    		
    		// Creating report files
                
            taskOutput.append("Creating report files... \n");
            progress += 30;
            setProgress(Math.min(progress, 100));
    		
    		if(!testSuite.getTestCases().isEmpty()) {
    			if(htmlReportCheckBox.isSelected()) {
                	createHTMLReportFile(operationUnderTest, testSuite);
                }
                
                if(xmlReportCheckBox.isSelected()) {
                	createXMLReportFile(operationUnderTest, testSuite);
                }
                
                if(javaTestScriptCheckBox.isSelected()) {
                	createJavaTestScript(operationUnderTest, testSuite);
                }
                
                if(cppTestScriptCheckBox.isSelected()) {
                	createCppTestScript(operationUnderTest, testSuite);
                }
    			
    		} else {
    			JOptionPane.showMessageDialog(null, 
    					"We don't have any valid combinations for this operation. \n" +
    					"No report was generated.", 
    					"Error", 
    					JOptionPane.ERROR_MESSAGE);
    		}
                
    		// Done!
            
            taskOutput.append("Done!");
            progress += 10;
            setProgress(Math.min(progress, 100));
            
            return null;
        }
 


		/*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            htmlReportCheckBox.setEnabled(true);
            xmlReportCheckBox.setEnabled(true);
            generateReportButton.setEnabled(true);
            javaTestScriptCheckBox.setEnabled(true);
            cppTestScriptCheckBox.setEnabled(true);
            
            // turn off the wait cursor
            setCursor(null); 
        }
        
        
        
        private void createHTMLReportFile(Operation operationUnderTest, BETATestSuite testSuite) {
    		String sourceMachineDirectory = operationUnderTest.getMachine().getFile().getParent();
    		PartitionStrategy chosenPartitionStrategy = PartitionStrategy.get(application.getChosenPartitionStrategy());
    		CombinatorialCriteria chosenCombinatorialCriteria = CombinatorialCriteria.get(application.getChosenCombinatorialCriteria());
			
    		String reportFileName = ConventionTools.getReportFileName(operationUnderTest, chosenPartitionStrategy, chosenCombinatorialCriteria, "html");
			String outputFilePath = sourceMachineDirectory + System.getProperty("file.separator") + reportFileName;
			
			HTMLReport htmlReport = new HTMLReport(testSuite, new File(outputFilePath));
			htmlReport.generateReport();
			
			taskOutput.append("HTML Report generated at: " + outputFilePath + "\n");
    	}
        
        
        
        private String createXMLReportFile(Operation operationUnderTest, BETATestSuite testSuite) {
    		String sourceMachineDirectory = operationUnderTest.getMachine().getFile().getParent();
    		PartitionStrategy chosenPartitionStrategy = PartitionStrategy.get(application.getChosenPartitionStrategy());
    		CombinatorialCriteria chosenCombinatorialCriteria = CombinatorialCriteria.get(application.getChosenCombinatorialCriteria());
    		
			String reportFileName = ConventionTools.getReportFileName(operationUnderTest, chosenPartitionStrategy, chosenCombinatorialCriteria, "xml");
			String outputFilePath = sourceMachineDirectory + System.getProperty("file.separator") + reportFileName;
			
			XMLReport xmlReport = new XMLReport(testSuite, new File(outputFilePath));
			xmlReport.generateReport();
			
			taskOutput.append("XML Report generated at: " + outputFilePath + "\n");
			
			return outputFilePath;
    	}
        
        
        
        private void createJavaTestScript(Operation operationUnderTest, BETATestSuite testSuite) {
        	String xmlFilePath = createXMLReportFile(operationUnderTest, testSuite);
        	File xmlFile = new File(xmlFilePath);
        	String codeOutputDirectory = xmlFile.getParent() + System.getProperty("file.separator");
        	
        	UnitTestBuilder builder = new JUnitBuilder();
    		builder.generateUnitTest(xmlFilePath, codeOutputDirectory, getSelectedOracleStrategy()); 

    		taskOutput.append("Java Test Script generated at: " + codeOutputDirectory + "\n");
		}
        
        
        
        private void createCppTestScript(Operation operationUnderTest, BETATestSuite testSuite) {
        	String xmlFilePath = createXMLReportFile(operationUnderTest, testSuite);
        	File xmlFile = new File(xmlFilePath);
        	String codeOutputDirectory = xmlFile.getParent() + System.getProperty("file.separator");
        	
        	UnitTestBuilder builder = new CppUnitBuilder();
    		builder.generateUnitTest(xmlFilePath, codeOutputDirectory, getSelectedOracleStrategy()); 

    		taskOutput.append("C++ Test Script generated at: " + codeOutputDirectory + "\n");
		}


        
		private OracleStrategy getSelectedOracleStrategy() {
			boolean invariantOKActive = Configurations.isInvariantOKActive();
			boolean returnVariablesActive = Configurations.isReturnVariablesActive();
			boolean stateVariablesActive = Configurations.isStateVariablesActive();
			
			if(invariantOKActive == true & returnVariablesActive == true & stateVariablesActive == true) {
				return OracleStrategy.ALL;
			} else if(invariantOKActive == false & returnVariablesActive == false & stateVariablesActive == false) {
				return OracleStrategy.EXCEPTION;
			} else if(invariantOKActive == false & returnVariablesActive == true & stateVariablesActive == false) {
				return OracleStrategy.RETURN_VALUES;
			} else if(invariantOKActive == true & returnVariablesActive == false & stateVariablesActive == false) {
				return OracleStrategy.STATE_INVARIANT;
			} else if(invariantOKActive == true & returnVariablesActive == true & stateVariablesActive == false) {
				return OracleStrategy.STATE_INVARIANT_RETURN_VALUES;
			} else if(invariantOKActive == true & returnVariablesActive == false & stateVariablesActive == true) {
				return OracleStrategy.STATE_INVARIANT_VARIABLES;
			} else if(invariantOKActive == false & returnVariablesActive == false & stateVariablesActive == true) {
				return OracleStrategy.STATE_VARIABLES;
			} else if(invariantOKActive == false & returnVariablesActive == true & stateVariablesActive == true) {
				return OracleStrategy.STATE_VARIABLES_RETURN_VALUES;
			} else {
				return OracleStrategy.EXCEPTION;
			}
		}
  
    }

}
