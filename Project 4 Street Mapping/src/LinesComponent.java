// CSC 172
// Project 4 Street Mapping
// Student Name: Zunran Guo
// Student ID: 28279136
// All work is original.
// reference: http://stackoverflow.com/questions/5801734/how-to-draw-lines-in-java

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LinesComponent extends JPanel {

	// construct two hash data structures for "road" and "intersection" objects
	RoadHash roadHash = new RoadHash();
	IntersectionHash intersectionHash = new IntersectionHash();

	// declare pathArray for shortest paths
	ArrayList<String> pathArray = new ArrayList<String>();

	// declare spanningRoadArray for spanning roads
	ArrayList<Intersection> spanningRoadArray = new ArrayList<Intersection>();
	
	int dijkstra = 0;
	int prims = 0;
	int kruskal = 0;
	int intersectionIndex = 0;

	public class Line {
		float x1;
		float y1;
		float x2;
		float y2;
		Color color;

		public Line(float x1, float y1, float x2, float y2, Color color) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;
		}
	}

	// constructor for a new line
	Line newLine = new Line(5, 5, 100, 100, Color.black);

	// graphics
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		paint(g2d);

		if (dijkstra == 1)
			paintDijkstra(g2d);

		if (prims == 1)
			paintPrims(g2d);
		
		// method to add a image in the background
				// credit: help from Joshua Ostroff
		//Image image = new ImageIcon("monroe-county-network.png").getImage();
		//g.drawImage(image, 0, 0, this);

	}
	
	private void paintPrims(Graphics2D g2d) {
		g2d.setPaint(Color.yellow);
		g2d.setStroke(new BasicStroke(1));

		for (int i = 0; i + 2 < spanningRoadArray.size(); i = i + 2) {
			
			//System.out.println(spanningRoadArray.get(i).intersectionID);
			
			// vertex 1 coordinates
			newLine.x1 = spanningRoadArray.get(i).x;
			newLine.y1 = spanningRoadArray.get(i).y;

			// vertex 2 coordinates
			newLine.x2 = spanningRoadArray.get(i + 1).x;
			newLine.y2 = spanningRoadArray.get(i + 1).y;

			// paint newLine
			g2d.draw(new Line2D.Float(newLine.x1, newLine.y1, newLine.x2, newLine.y2));
		}
	}

	private void paintDijkstra(Graphics2D g2d) {
		g2d.setPaint(Color.red);
		g2d.setStroke(new BasicStroke(8));

		for (int i = 0; i < pathArray.size(); i = i + 2) {

			// vertex 1 coordinates
			newLine.x1 = intersectionHash.retrieve(pathArray.get(i)).x;
			newLine.y1 = intersectionHash.retrieve(pathArray.get(i)).y;

			// vertex 2 coordinates
			newLine.x2 = intersectionHash.retrieve(pathArray.get(i + 1)).x;
			newLine.y2 = intersectionHash.retrieve(pathArray.get(i + 1)).y;

			// paint newLine
			g2d.draw(new Line2D.Float(newLine.x1, newLine.y1, newLine.x2, newLine.y2));
		}
	}

	public void paint(Graphics2D g2d) {
		g2d.setPaint(Color.black);

		// go through hashTableArray and find stored Road object
		for (int i = 0; i < roadHash.hashTableArray.length; i++) {
			if (roadHash.hashTableArray[i] != null) {

				// vertex 1 coordinates
				newLine.x1 = intersectionHash.retrieve(roadHash.hashTableArray[i].beginningPointID).x;
				newLine.y1 = intersectionHash.retrieve(roadHash.hashTableArray[i].beginningPointID).y;

				// vertex 2 coordinates
				newLine.x2 = intersectionHash.retrieve(roadHash.hashTableArray[i].endPointID).x;
				newLine.y2 = intersectionHash.retrieve(roadHash.hashTableArray[i].endPointID).y;

				
				// store the distance
				float distanceSquare = (newLine.x1 - newLine.x2) * (newLine.x1 - newLine.x2)
						+ (newLine.y1 - newLine.y2) * (newLine.y1 - newLine.y2);
				roadHash.hashTableArray[i].distance = (float) Math.sqrt(distanceSquare);
						
				// paint newLine
				g2d.draw(new Line2D.Float(newLine.x1, newLine.y1, newLine.x2, newLine.y2));
			}
		}
	}

	// method to retrieve the object based on its ID for Dijkstra's algorithm
	public Intersection findIntersectionObject(Graph graph, String id) {
		int i;
		for (i = 0; i < graph.vertexObjectArray.size(); i++) {
			if (graph.vertexObjectArray.get(i).intersectionID.equalsIgnoreCase(id)) {
				break;
			}
		}
		return graph.vertexObjectArray.get(i);
	}
	
	// method to perform all sorts of drawing functions
	public void drawlines() {
		// load data
		loadData.readFile("monroe-county.tab", intersectionHash, roadHash);

		// System.out.println("intersectionHash.totalItems is " +
		// intersectionHash.totalItems);

		// construct a undirected graph structure
		Graph graph = new Graph(intersectionHash.totalItems, false);

		// set up the frame
		JFrame testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		testFrame.add(this);
		testFrame.setSize(680, 680);
		testFrame.setVisible(true);

		System.out.println("\nThe final size of the roadHash is: " + intersectionHash.size());
		System.out.println("The final size of the intersectionHash is: " + roadHash.size());
		System.out.println("The number of total intersections is: " + intersectionHash.totalItems);
		System.out.println("The number of total roads is: " + roadHash.totalItems);
		System.out.println("The number of collisions in hashing: " + intersectionHash.collision);
		System.out.println("The number of collisions in hashing: " + roadHash.collision);

		// processing graph data 
		graph.readGraphVertices(intersectionHash, roadHash);

		// testing
		// System.out.println("\ngraph.roadArray size is " + graph.roadArray.size());
		// System.out.println("graph.vertexObjectArray size is " + graph.vertexObjectArray.size());

		String initialRoadIntersection = null;
		String finalRoadIntersection = null;

		System.out.println("\nFind minimum weight spanning tree? (yes/no)");
		Scanner scannerOption = new Scanner(System.in);
		String Option = scannerOption.nextLine();
		if (Option.equalsIgnoreCase("yes")) {
			prims = 1;
			//kruskal = 1; 
		}
		
		if (prims == 1){
			graph.prims(graph.vertexObjectArray.get(0), intersectionHash, roadHash);
			spanningRoadArray = graph.usedVertexArray;
			repaint();
		}
		
//		if (kruskal == 1){
//			graph.kruskal(intersectionHash, roadHash);
//			spanningRoadArray = graph.usedVertexArray;
//			repaint();
//		}
		
		System.out.println("\nFind the shortest path of two intersections? (yes/no)");
		String Option2 = scannerOption.nextLine();
		if (Option2.equalsIgnoreCase("yes")) {
			dijkstra = 1;
			System.out.println("OK. Please type your initial road intersection. (e.g. i212658584)");
			initialRoadIntersection = scannerOption.nextLine();
			System.out.println("OK. Please type your final road intersection. (e.g. i212618290)");
			finalRoadIntersection = scannerOption.nextLine();

		}
		
		if (dijkstra == 1){
			// print the path form two intersections
			graph.dijkstra(findIntersectionObject(graph, initialRoadIntersection));
			graph.printShortestPath(findIntersectionObject(graph, finalRoadIntersection));
			pathArray = graph.pathArray;
			repaint();
		}
		
		// close the scanners
		scannerOption.close();
		System.out.println("\nProgram now terminated.");
	}

}