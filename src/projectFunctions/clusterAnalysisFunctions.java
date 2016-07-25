/* Created by Pranav Sharan   
 * Contains common functions used by ClusterAnalysis project
 * */

package projectFunctions;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import userInterface.CentralScreen;

public class clusterAnalysisFunctions {
	
	public static ArrayList<Double> x_coordinates=new ArrayList<Double>();
	public static ArrayList<Double> y_coordinates=new ArrayList<Double>();
	public static HashMap<Integer,Double> cluster_map_x=new HashMap<Integer,Double>();
	public static HashMap<Integer,Double> cluster_map_y=new HashMap<Integer,Double>();
	public static HashMap<Integer,ArrayList> centroidHashMap = new HashMap<Integer,ArrayList>();
	public static ArrayList<Double> centroidArrayList = new ArrayList<Double>();
	public static ArrayList<Integer> centroidMap = new ArrayList<Integer>();
	public static HashMap<Integer,ArrayList> tempcentroidHashMap = new HashMap<Integer,ArrayList>();
	
	public static String FileLocation = null;
	public static String comboboxchoice = null;
	public static int numberOfClusters = 2;
	
	    //Eucledian distance formula calculation
		public static double getDistance(double x1,double y1,double x2,double y2)
		{
			double distance=0;
			distance=((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)); 
			distance=Math.sqrt(distance);
			return distance;
		}
    
		// Listeners for elements present in CentralScreen.java
		public static class ButtonClickListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				final JFileChooser  fileDialog = new JFileChooser();
				
				
				if(command.equals("Import"))
				{
					int returnVal = fileDialog.showOpenDialog(CentralScreen.mainFrame);
		            if (returnVal == JFileChooser.APPROVE_OPTION) 
		            {
		               java.io.File file = fileDialog.getSelectedFile();
		               CentralScreen.statusLabel.setText("File Selected :" 
		               + file.getName());
		            FileLocation=file.getAbsolutePath();
		            }
		                 
				}
				else if(command.equals("Generate"))
				{
					
					readfromtxt();
					comboboxchoice=CentralScreen.clusterOptionsCombobox.getSelectedItem().toString();
					JOptionPane.showMessageDialog(CentralScreen.mainFrame,
						    comboboxchoice+" clusters will be processed for the input data");
					int noc =CentralScreen.clusterOptionsCombobox.getSelectedIndex();
					
					switch(noc)
					{
					case 0:
						numberOfClusters=2;
						break;
						
					case 1:
						numberOfClusters=3;
						break;	
					
					case 2:
						numberOfClusters=4;
						break;
					
					case 3:
						numberOfClusters=5;
						break;
					}
					
					performClusterAnalysis();					
					CentralScreen.prepareUI();
					
				}
				else if (command.equals("Recompute"))
				{
					performClusterAnalysis();
					
				}
				else if (command.equals("Save"))
				{
					JOptionPane.showMessageDialog(CentralScreen.mainFrame,
						    "Feature coming soon");
				}
			}
		}
		
		//Create dataset for chart 
		public static XYDataset createDataset() {

	        DefaultXYDataset ds = new DefaultXYDataset();
	       
	        XYSeries series = new XYSeries("Cluster1");
	        XYSeries series2 = new XYSeries("Cluster2");
	        XYSeries series3 = new XYSeries("Cluster3");
	        XYSeries series4 = new XYSeries("Cluster4");
	        XYSeries series5 = new XYSeries("Cluster5");
	        
	        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
	        	        
	        for(int i=0;i<centroidMap.size();i++)
	        {
	        	switch(centroidMap.get(i))
	        	{
	        	case 1:
	        	series.add(x_coordinates.get(i),y_coordinates.get(i));	
	        	break;
	        	
	        	case 2:
		        	series2.add(x_coordinates.get(i),y_coordinates.get(i));	
		        	break;
	        	
	        	case 3:
		        	series3.add(x_coordinates.get(i),y_coordinates.get(i));	
		        	break;
	        	
	        	case 4:
		        	series4.add(x_coordinates.get(i),y_coordinates.get(i));	
		        	break;
	        	
	        	case 5:
		        	series5.add(x_coordinates.get(i),y_coordinates.get(i));	
		        	break;
	        	}
	        }
	        
	        switch(numberOfClusters)
	        {
	        case 2:
	        	xySeriesCollection.addSeries(series);
		        xySeriesCollection.addSeries(series2);
	        	break;
	        
	        case 3:
	        	xySeriesCollection.addSeries(series);
		        xySeriesCollection.addSeries(series2);
		        xySeriesCollection.addSeries(series3);
	        	break;
	        	
	        case 4:
	        	xySeriesCollection.addSeries(series);
		        xySeriesCollection.addSeries(series2);
		        xySeriesCollection.addSeries(series3);
		        xySeriesCollection.addSeries(series4);
	        	break;
	        
	        case 5:
	        	xySeriesCollection.addSeries(series);
		        xySeriesCollection.addSeries(series2);
		        xySeriesCollection.addSeries(series3);
		        xySeriesCollection.addSeries(series4);
		        xySeriesCollection.addSeries(series5);
	        	break;
	        }
	        
	        
	        return xySeriesCollection;
	    }
		
		
		//Read data from imported file
		public static void readfromtxt()
		{
			String currentline=null;
			 String[] linecontent=null;			 
			
				try{
				BufferedReader br=new BufferedReader(new FileReader(FileLocation));
			    
				//Get name of x and y series
				currentline=br.readLine();
				linecontent=currentline.split(",");
				CentralScreen.x_coordinate_name=linecontent[0];
				CentralScreen.y_coordinate_name=linecontent[1];
				
				while((currentline=br.readLine())!=null)
			    {
			    	linecontent=currentline.split(",");
			    	x_coordinates.add(Double.parseDouble(linecontent[0]));
			    	y_coordinates.add(Double.parseDouble(linecontent[1]));
			    	cluster_map_x.put(1,Double.parseDouble(linecontent[0]));
			    	cluster_map_y.put(1,Double.parseDouble(linecontent[1]));
			    }	
				br.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(CentralScreen.mainFrame,
						    "Please import a valid input file");
					ex.printStackTrace();
				}
				
		}
		
		//Perform Cluster analysis
		public static void performClusterAnalysis()
	    {
			CentralScreen.analysisResults = new StringBuilder("");
			Random r = new Random();
			System.out.println("Clusters:" + numberOfClusters);
			int flag = 1,iteration=1;
			
			//Compute the centroids related to the input
			for(int z=0;z<numberOfClusters;z++)
			{
				centroidArrayList=new ArrayList<Double>();
				int i=r.nextInt(((x_coordinates.size()-1) - 0) + 1) + 0;
				centroidArrayList.add(x_coordinates.get(i));
				centroidArrayList.add(y_coordinates.get(i));
				centroidHashMap.put(z+1, centroidArrayList);			
			}
			
			while(flag==1){
				centroidMap=new ArrayList<Integer>();
				
			//Calculate distance between centroids and input data points
			for(int a=0;a<x_coordinates.size();a++)
			{double minimumDistance=1000;
			 int centroid_count = 1;
			 
				
				Set set=clusterAnalysisFunctions.centroidHashMap.entrySet();
		        Iterator iterator=set.iterator();
		        while(iterator.hasNext())
		        {
		        	Map.Entry mentry = (Map.Entry)iterator.next();
		        	String result = mentry.getValue().toString();
		        	result=result.replace("[", " ");
		        	result=result.replace("]", " ");
		        	String[] linecontent=null;
		        	linecontent=result.split(",");		    
		    		double x_coordinate_centroid = Double.parseDouble(linecontent[0]);
		    		double y_coordinate_centroid = Double.parseDouble(linecontent[1]);
		            double calculateDistance = getDistance(x_coordinate_centroid,y_coordinate_centroid,x_coordinates.get(a),
		            		y_coordinates.get(a));
		            
		            if(minimumDistance > calculateDistance)
		            { minimumDistance = calculateDistance;
		              centroid_count=(int) mentry.getKey();}
		            
		        }
		        centroidMap.add(centroid_count);
		      		        
			}
			
			HashMap<Integer,ArrayList> tempcentroidHashMap = new HashMap<Integer,ArrayList>();
			//Recompute Centroid values
			for(int b=1;b<=numberOfClusters;b++)
			{ArrayList<Double> input_x= new ArrayList<Double>();
			 ArrayList<Double> input_y= new ArrayList<Double>();
			  	for(int c=0;c<centroidMap.size();c++)
			  	{
			  		if(centroidMap.get(c)==b)
			  		{
			  	      input_x.add(x_coordinates.get(c));
			  	      input_y.add(y_coordinates.get(c));
			  		}
			  	}
			
			  	
				ArrayList<Double> tempcentroidArrayList = new ArrayList<Double>();
			  	
			  	tempcentroidArrayList.add(calculateMean(input_x));
			  	tempcentroidArrayList.add(calculateMean(input_y));
			  	tempcentroidHashMap.put(b, tempcentroidArrayList);
			}
			
			if(tempcentroidHashMap.equals(centroidHashMap))
			{System.out.println("Equals");
			CentralScreen.analysisResults.append("Convergence has been acheived in "+(iteration-1)+" iterations");
			flag=0;break;}
			else
			{System.out.println("Not equals");
			centroidHashMap.clear();
			centroidHashMap.putAll(tempcentroidHashMap);
			}
			
			CentralScreen.analysisResults.append("\nIteration"+iteration+":\n");
			Set set=clusterAnalysisFunctions.centroidHashMap.entrySet();
	        Iterator iterator=set.iterator();
	        while(iterator.hasNext())
	        {
	        	Map.Entry mentry = (Map.Entry)iterator.next();	        	
	            CentralScreen.analysisResults.append("Centroid"+mentry.getKey().toString()+": "+mentry.getValue().toString()+"\n");
	        }
			iteration++;
			//
			}
	    }
		
		public static double calculateMean(ArrayList<Double> input)
		{
			int count=0;
			double sum=0,average=0;
			
			for(int i=0;i<input.size();i++)
			{
				count++;
				sum=sum+input.get(i);
			}
			average=sum/count;
			return average;
		}
		
}
