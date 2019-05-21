package similaritycheck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Prog {

	public static void main(String[] args) throws IOException {
		
		ArrayList<Artwork> DataBase1 = new ArrayList<Artwork>();
		ArrayList<Artwork> DataBase2 = new ArrayList<Artwork>();
								
		File DB1 = new File("C:\\DB1folder");
		File DB2 = new File("C:\\DB2folder");
		String outputDirPath = ("C:\\resultfolder\\") ;
		
		File[] DB1Listing = DB1.listFiles();
		File[] DB2Listing = DB2.listFiles();
		
		//Start Reading DataBases
		//Start reading files in DB1 and store the resuls in the ArrayList DataBase1
		if (DB1Listing != null) {
			for (File child : DB1Listing)
			{
				System.out.println();
				System.out.println(child);
				BufferedReader DataBase1Reader = new BufferedReader(new FileReader(child));
				String CurrentLine;
				while ((CurrentLine = DataBase1Reader.readLine()) != null)
				{
					String[] tokens = CurrentLine.split(",,,");
					DataBase1.add(new Artwork(tokens[tokens.length-3], tokens[tokens.length-2], tokens[tokens.length-1].split("T")[0]));
				}
				DataBase1Reader.close();
			}
		} else {
			System.err.println(DB1Listing+" is empty !");
		}
		System.out.println("DB1 read done!");
				
		//Start reading files in DB2 and store the resuls in the ArrayList DataBase2
		if (DB2Listing != null) {
			for (File child : DB2Listing)
			{
				System.out.println();
				System.out.println(child);
				BufferedReader DataBase2Reader = new BufferedReader(new FileReader(child));
				String CurrentLine;
				while ((CurrentLine = DataBase2Reader.readLine()) != null)
				{
					String[] tokens = CurrentLine.split(",,,");
					DataBase2.add(new Artwork(tokens[tokens.length-3], tokens[tokens.length-2], tokens[tokens.length-1]));
				}
				DataBase2Reader.close();
			}
		} else {
			System.err.println(DB2Listing+" is empty !");
		}
		System.out.println("DB2 read done!");
		
		//End Reading DataBases and storing the results in 2 ArrayLists
		
		//Testing in the console if the results written successfully
		System.out.println();
		System.out.println("DB1: " + DataBase1.get(3).getUri() + " " + DataBase1.get(3).getCatnb() + " " + DataBase1.get(3).getName());
		System.out.println("DB2: " + DataBase2.get(3).getUri() + " " + DataBase2.get(3).getCatnb() + " " + DataBase2.get(3).getName());
		System.out.println();
	
		//Showing in the Console the sizes of the DataBases
		System.out.println("DB1 size " + DataBase1.size());
		System.out.println("DB2 size " + DataBase2.size());
		System.out.println();
		
		//Start similarity check and output
		final HashMap<String, Double> thresholdMap = new HashMap<>();
		
		//Define a thresholdValue. The Similarity will be measured based on this value.
		thresholdMap.put("ThresholdValue ", 0.5);
	
		//test thresholdValue in the console
		System.out.println("Threshold Map value " + thresholdMap.values());
		System.out.println();
		
		for (HashMap.Entry<String,Double> entry : thresholdMap.entrySet()) {
			//Create a writer to write into a file with defining the filename and extension
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(new File(outputDirPath + "threshold of " +entry.getValue()+".txt")));
			
		//make a loop to find the entries with same catalogue number, compare the corresponding names and write the result into a file.
		for (int i=0; i<DataBase1.size(); i++) 
		{ 				
			for (int j=0; j<DataBase2.size(); j++)
			{
				//verify if the catalogue number is the same
				if ((DataBase1.get(i).getCatnb()).equals(DataBase2.get(j).getCatnb()))
				{
					//verify similarity of the names
					double similarityScore = SimLevenshtein.getSimilarityScore(DataBase1.get(i).getName(), DataBase2.get(j).getName());
					
					//if similarity value is greater or equal to the defined threshold Value write the results in a file and show the results in the console
					if (similarityScore>=entry.getValue())
					{
						buffWriter.write("Measure of Similarity  " +  SimLevenshtein.getSimilarityScore(DataBase1.get(i).getName(), DataBase2.get(j).getName()));
						buffWriter.newLine();
						buffWriter.write("DataBase 1: " + DataBase1.get(i).getName() +"  "+ DataBase1.get(i).getCatnb() +"  "+ DataBase1.get(i).getUri());
						buffWriter.newLine();
						buffWriter.write("DataBase 2: " + DataBase2.get(j).getName() +"  "+ DataBase2.get(j).getCatnb() +"  "+ DataBase2.get(j).getUri());
						buffWriter.newLine();
						buffWriter.newLine();
						System.out.println("Measure of Similarity  " +  SimLevenshtein.getSimilarityScore(DataBase1.get(i).getName(), DataBase2.get(j).getName()));
						System.out.println("DataBase 1: " + DataBase1.get(i).getName() +"  "+ DataBase1.get(i).getCatnb() +"  "+ DataBase1.get(i).getUri());
	  					System.out.println("DataBase 2: " + DataBase2.get(j).getName() +"  "+ DataBase2.get(j).getCatnb() +"  "+ DataBase2.get(j).getUri());
	  					System.out.println();
	  				}
				}
			}
		}
		System.out.println("Finished comparison!");
		buffWriter.close();
		//End similarity and output
		}
	}
}
