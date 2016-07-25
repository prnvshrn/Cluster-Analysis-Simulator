/* Created by Pranav Sharan   
 * UI for initial screen of the project
 * */

package userInterface;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import net.miginfocom.swing.MigLayout;
import projectFunctions.clusterAnalysisFunctions;

public class CentralScreen {	
	//Global variable declaration section
	public static JFrame mainFrame=new JFrame();
	public static JLabel statusLabel=new JLabel("File Selected : None");
	public static JComboBox<String> clusterOptionsCombobox = new JComboBox<>();
	public static JTextArea textarea=new JTextArea("");
	public static StringBuilder analysisResults = new StringBuilder("Analysis Result will be displayed in this section");
	public static String x_coordinate_name = "x";
	public static String y_coordinate_name = "y";
	
	public static void main(String[] args) {		
		prepareUI();
	}

	public static void prepareUI()
	{
		mainFrame.getContentPane().removeAll();	    
		mainFrame.getContentPane().repaint();
		mainFrame.setSize(700,600);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton importButton = new JButton("Import File");
		importButton.setActionCommand("Import");
		importButton.addActionListener(new clusterAnalysisFunctions.ButtonClickListener());		
						
		JButton generateButton = new JButton("Run Simulation");
		generateButton.setActionCommand("Generate");
		generateButton.addActionListener(new clusterAnalysisFunctions.ButtonClickListener());
				
		JButton saveButton = new JButton("Save Chart");
		saveButton.setActionCommand("Save");
		saveButton.addActionListener(new clusterAnalysisFunctions.ButtonClickListener());
		
		JLabel clustercomboboxLabel = new JLabel("Number of clusters: ");
		String[] clusterOptions = new String[] {"2", "3","4", "5"};
		clusterOptionsCombobox = new JComboBox<>(clusterOptions);
		
		BufferedImage myPicture = null,myPicture2 = null,myPicture3 = null;
		try 
		{			
			myPicture2 = ImageIO.read(new File("src\\projectImages\\LogoMakr4.png"));	
			myPicture = ImageIO.read(new File("src\\projectImages\\question_mark.png"));
			myPicture3 = ImageIO.read(new File("src\\projectImages\\drawing4.png"));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel logoLabel = new JLabel(new ImageIcon(myPicture2));		
		JLabel importLabel = new JLabel("Select a file to import");
		JLabel hintLabel = new JLabel(new ImageIcon(myPicture));
		JLabel selfLabel = new JLabel(new ImageIcon(myPicture3));
		
		JPanel chartpanel = new JPanel(new FlowLayout());	    	
        JFreeChart chart = prepareChart();
        ChartPanel cp = new ChartPanel(chart);
        cp.setBackground(Color.LIGHT_GRAY);
        
        textarea.setText(analysisResults.toString());
    	textarea.setSize(100,100);
    	textarea.setRows(25);
    	textarea.setColumns(30);
    	textarea.setBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(textarea);  
        scrollPane.setBackground(Color.lightGray);
        
        JPanel inputPanel = new JPanel(new MigLayout());       
        inputPanel.add(importButton,"span,wrap,grow");        
        inputPanel.add(statusLabel,       "grow,span,wrap");	    
        inputPanel.add(clustercomboboxLabel);
        inputPanel.add(clusterOptionsCombobox,"span,grow,wrap");
        inputPanel.add(generateButton,"span,grow,wrap");
        inputPanel.add(scrollPane,"span,grow");
		inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
        JPanel chartPanel=new JPanel(new MigLayout());
        chartPanel.add(logoLabel,"skip,skip,skip,skip,skip,skip,skip,skip,wrap");        
        chartPanel.add(cp,"span,grow,wrap");
        chartPanel.add(saveButton);        
        chartPanel.setBackground(Color.LIGHT_GRAY);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainFrame.setBackground(Color.RED);
        mainFrame.add(inputPanel);    
		mainFrame.add(chartPanel);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true); 
	}
	
	public static JFreeChart prepareChart()
	{
		XYDataset ds = clusterAnalysisFunctions.createDataset();
        JFreeChart chart = ChartFactory.createScatterPlot("Cluster Analysis",
        		x_coordinate_name, y_coordinate_name, ds, PlotOrientation.VERTICAL, true, true,
                false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.DARK_GRAY);
        return chart;
	}	
	
}
