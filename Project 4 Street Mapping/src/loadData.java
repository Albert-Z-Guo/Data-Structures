// CSC 172
// Project 4 Street Mapping
// Student Name: Zunran Guo
// Student ID: 28279136
// All work is original.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class loadData {

	// method to load the database
	public static void readFile(String DataFileName, IntersectionHash intersectionHash, RoadHash roadHash) {
		String fileName = DataFileName;
		
		// declare time variables
		long startTime, endTime, elapsedTime;

		// String line will reference one line at a time
		String line = null;
		
		// start counting time
		startTime = System.currentTimeMillis();

		try {
			// fileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// split each line read by tab
				String[] tokens = line.split("\\t");
				
				// in the intersection case
				// add newIntersection objects to intersectionArrayList
				if (tokens[0].equalsIgnoreCase("i")) {
					Intersection newIntersection = new Intersection();
					newIntersection.intersectionID = tokens[1];
					newIntersection.x = (float) Double.parseDouble(tokens[2]);
					newIntersection.y = (float) Double.parseDouble(tokens[3]);
					
					intersectionHash.insert(newIntersection);
					//intersectionArrayList.add(newIntersection);
				}
				
				// in the road case case
				// add newRoad objects to roadArrayList
				else if (tokens[0].equalsIgnoreCase("r")){
					Road newRoad = new Road();
					newRoad.roadID = tokens[1];
					newRoad.beginningPointID = tokens[2];
					newRoad.endPointID = tokens[3];
					
					roadHash.insert(newRoad);
					//roadArrayList.add(newRoad);
				}
			}

			// always close files
			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		// end counting time
		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;

		// print the information about data loaded
		System.out.println("File '" + DataFileName + "' is loaded.");
		System.out.println("Process time: " + elapsedTime + " milliseconds");
	}
	
	// method to analyze the loaded data
	public static void analyzeData(IntersectionHash intersectionHash, RoadHash roadHash){
		
	}
}
