// CSC 172
// Project 4 Street Mapping
// Student Name: Zunran Guo
// Student ID: 28279136
// All work is original.

import java.util.ArrayList;
import java.util.Collections;

public class Graph {
	private int vertexCount, edgeCount = 0;

	// false for undirected graphs, true for directed
	boolean directed;

	// 2-D array for adjacency matrix
	float adj[][];

	// initial and final nodes for shortest path algorithm
	Intersection initialNode, finalNode;

	// ArrayList for vertex
	ArrayList<Integer> vertexArray = new ArrayList<Integer>();

	// ArrayLists for kruskal (under testing and development)
	ArrayList<Intersection> SubNode1Array = new ArrayList<Intersection>();
	ArrayList<Intersection> SubNode2Array = new ArrayList<Intersection>();

	// ArrayList for used vertex
	ArrayList<Intersection> usedVertexArray = new ArrayList<Intersection>();

	// ArrayList for vertex objects
	ArrayList<Intersection> vertexObjectArray = new ArrayList<Intersection>();

	// declare pathArray for paths
	ArrayList<String> pathArray = new ArrayList<String>();

	// declare RoadArray for roads
	ArrayList<Road> roadArray = new ArrayList<Road>();

	// constructor of class Graph
	public Graph(int numVerticies, boolean isDirected) {
		vertexCount = numVerticies;
		directed = isDirected;
		adj = new float[numVerticies][numVerticies];
		// initialize adj[][] with all elements to be 0
		for (int i = 0; i < numVerticies; i++) {
			for (int j = 0; j < numVerticies; j++) {
				adj[i][j] = 0;
			}
		}
	}

	// method to return whether the graph is directed
	public boolean isDirected() {
		return directed;
	}

	// method to return the number of vertices
	public int vertices() {
		return vertexCount;
	}

	// method to return the number of edges
	public int edges() {
		return edgeCount;
	}

	// method to indicate that the two nodes of the edge are connected
	// in the adjacency matrix
	public void insert(int beginningNode, int endNode, float edgeCost) {
		// if the graph is directed, insert only one direction
		if (directed == true) {
			adj[beginningNode - 1][endNode - 1] = edgeCost;
		}

		// if the graph is not directed, insert two directions
		else if (directed != true) {
			// add edge from v to w
			adj[beginningNode - 1][endNode - 1] = edgeCost;

			// add edge from w to v
			adj[endNode - 1][beginningNode - 1] = edgeCost;
		}

		// update edgeCount
		edgeCount++;
	}

	// method to indicate that the two nodes of the edge are not connected
	// in the adjacency matrix
	public void delete(int beginningNode, int endNode, float edgeCost) {
		// if the graph is directed, insert only one direction
		if (directed == true) {
			adj[beginningNode - 1][endNode - 1] = 0;
		}

		// if the graph is not directed, insert two directions
		else if (directed != true) {
			// add edge from v to w
			adj[beginningNode - 1][endNode - 1] = 0;

			// add edge from w to v
			adj[endNode - 1][beginningNode - 1] = 0;
		}

		// update edgeCount
		edgeCount--;
	}

	// method to check whether node1 and node2 are connected
	public boolean connected(int node1, int node2) {
		if (adj[node1][node2] != 0)
			return true;
		else
			return false;
	}

	// method to print the adjacency matrix
	public void printAdjacencyMatrix() {
		for (int i = 0; i < vertices(); i++) {
			System.out.print(i + 1 + ": ");
			for (int j = 0; j < vertices(); j++) {
				System.out.print(adj[i][j] + "  ");
			}
			System.out.println("");
		}
	}

	// method to read the graph information from intersectionHash
	public void readGraphVertices(IntersectionHash intersectionHash, RoadHash roadHash) {

		System.out.println("\nProcessing graph data now... Please be patient.");
		// initialize the variables
		int index = 0;

		// from roadHash, read all the intersections from the roads
		for (int i = 0; i < roadHash.hashTableArray.length; i++) {
			if (roadHash.hashTableArray[i] != null) {

				// find useful information from two intersections of each road
				roadArray.add(roadHash.hashTableArray[i]);

				float edgeCost = roadHash.hashTableArray[i].distance;
				
				if (lookupUsedVertex(roadHash.hashTableArray[i].beginningPointID, vertexObjectArray) == false) {
					++index;
					intersectionHash.retrieve(roadHash.hashTableArray[i].beginningPointID).index = index;
					vertexObjectArray.add(intersectionHash.retrieve(roadHash.hashTableArray[i].beginningPointID));
					// System.out.println("index now is " + index);
				}

				// if the intersection (end node) is not recorded, 
				// assign it an integer index
				if (lookupUsedVertex(roadHash.hashTableArray[i].endPointID, vertexObjectArray) == false) {
					++index;
					intersectionHash.retrieve(roadHash.hashTableArray[i].endPointID).index = index;
					vertexObjectArray.add(intersectionHash.retrieve(roadHash.hashTableArray[i].endPointID));
					// System.out.println("index now is " + index);
				}

				insert(intersectionHash.retrieve(roadHash.hashTableArray[i].beginningPointID).index,
						intersectionHash.retrieve(roadHash.hashTableArray[i].endPointID).index, edgeCost);
			}

		}
	}
	
	// method to look up used intersection by ID and specified object array list
	public boolean lookupUsedVertex(String id, ArrayList<Intersection> ObjectArrayList) {
		for (int i = 0; i < ObjectArrayList.size(); i++) {
			if (ObjectArrayList.get(i).intersectionID.equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
	}

	// constructor of AdjArray
	public AdjList getAdjList(int vertex) {
		return new AdjArray(vertex);
	}

	// method to print the graph
	public void show() {
		for (int s = 0; s < vertices(); s++) {
			System.out.print(s + 1 + ": ");
			AdjList A = getAdjList(s);
			for (int t = A.begin(); !A.end(); t = A.next()) // use of iterator
				System.out.print(t + 1 + " ");
			System.out.println();
		}
	}

	private class AdjArray implements AdjList {
		private int v; // what vertex we are interested in
		private int i; // so we can keep track of where we are

		public AdjArray(int v) {
			// save the value of the vertex passed in
			this.v = v;
			// (that will be where the iterator starts)
			// start the "i" counter at negative one
			this.i = -1;
		}

		public int next() {
			// use a for loop to advance the value of "i"
			// and search the appropriate row return the index
			// of the next true value found
			for (++i; i < vertices(); i++) {
				if (connected(v, i) == true)
					return i;
			}

			// if the loop completes without finding anything
			return -1;
		}

		public int begin() {
			// reset i
			i = -1;
			// return the value of a call to next()
			return next();
		}

		public boolean end() {
			if (i < vertexCount)
				return false;

			// if i now equals vertexCount by increment
			return true;
		}
	}

	// method to get edge cost from the adjacency matrix
	public double getEdgeCost(Intersection initialNode, Intersection finalNode) {
		return adj[initialNode.index - 1][finalNode.index - 1];
	}

	// method to find the smallest unknown distance from all the known vertices
	public Double findTheSmallestUnknownDistanceFromAllDistanceKnownVertices() {
		Double smallestDist = -1.0;

		// check all unknown vertices
		for (int a = 0; a < vertexObjectArray.size(); a++) {
			if (vertexObjectArray.get(a).known == false) {
				// initialize smallestDist
				smallestDist = vertexObjectArray.get(a).dist;
			}
		}

		// loop through all unknown vertices and find smallestDist
		for (int b = 0; b < vertexObjectArray.size(); b++) {
			if (vertexObjectArray.get(b).known == false) {
				if (smallestDist > vertexObjectArray.get(b).dist) {
					smallestDist = vertexObjectArray.get(b).dist;
				}
			}
		}

		// System.out.println("smallest distance picked is " + smallestDist);
		return smallestDist;
	}
	
	// method for the kruskal algorithm
	public float findTheMinimumWeightfromAllUnknowRoads(ArrayList<Road> roadArray) {
		float smallestWeight = (float) -1.0;

		// check all roads
		for (int i = 0; i < roadArray.size(); i++) {
			// initialize smallestDist
			smallestWeight = roadArray.get(i).distance;

		}

		// loop through all unknown roads and find smallestWeight
		for (int j = 0; j < roadArray.size(); j++) {
			if (roadArray.get(j).known == false) {
				if (smallestWeight > roadArray.get(j).distance) {
					smallestWeight = roadArray.get(j).distance;
				}
			}
		}

		// System.out.println("smallest distance picked is " + smallestWeight);
		return smallestWeight;

	}

	// method to retrieve Intersection by index
	public Intersection retrieveIntersectionByIndex(int index) {
		int i;
		for (i = 0; i < vertexObjectArray.size(); i++) {
			if (vertexObjectArray.get(i).index == index) {
				break;
			}
		}
		return vertexObjectArray.get(i);
	}
	
	// method to retrieve Intersection by ID
	public Intersection retrieveIntersectionByID(String id) {
		int i;
		for (i = 0; i < vertexObjectArray.size(); i++) {
			if (vertexObjectArray.get(i).intersectionID.equals(id)) {
				break;
			}
		}
		return vertexObjectArray.get(i);
	}

	// method to implement kruskal minimum weight spanning tree
	public void kruskal(IntersectionHash intersectionHash, RoadHash roadHash) {

		// set all roads with known to be false
		for (int j = 0; j < roadArray.size(); j++) {
			roadArray.get(j).known = false;
			// System.out.println(roadArray.get(j).roadID);
		}

		// try a reduced size vertex array

		// set all intersections with known to be false
		for (int i = 0; i < vertexObjectArray.size(); i++) {
			vertexObjectArray.get(i).known = false;
			// System.out.println(vertexObjectArray.get(i).intersectionID);
		}

		// loop until all intersections are checked
		for (int a = 0; a < vertexObjectArray.size(); a++) {
			// if there is an unknown intersection
			if (vertexObjectArray.get(a).known == false) {
				// initialize the weight
				float smallestWeight;

				// find the minimum weighted road
				smallestWeight = findTheMinimumWeightfromAllUnknowRoads(roadArray);

				int j = 0;

				// once smallestWeight is found,
				// find the road r with smallestWeight
				for (j = 0; j < roadArray.size(); j++) {
					if (roadArray.get(j).distance == smallestWeight) {
						break;
					}
				}

				// set the two intersections of road r to be known
				intersectionHash.retrieve(roadArray.get(j).beginningPointID).known = true;
				intersectionHash.retrieve(roadArray.get(j).endPointID).known = true;

				int criterion = 0;

				// check whether both of the intersections of the roads are used
				// if we accept the road
				if (lookupUsedVertex(roadArray.get(j).beginningPointID, usedVertexArray) == true)
					criterion++;

				if (lookupUsedVertex(roadArray.get(j).endPointID, usedVertexArray) == true)
					criterion++;

				// if only one or 0 intersection of the road is used
				// we add accept the road as part of the minimum spanning tree
				if (criterion <= 1) {
					// set road r to be known
					roadArray.get(j).known = true;

					// accept the road by storing two intersections to
					// usedVertexArray
					usedVertexArray.add(intersectionHash.retrieve(roadArray.get(j).beginningPointID));
					usedVertexArray.add(intersectionHash.retrieve(roadArray.get(j).endPointID));
				}

				// if both intersections of the road is used, reject the road
				// by not inserting any intersection to usedVertexArray
				else {
					// set road r to be known
					roadArray.get(j).known = true;

					// reset the arrays
					SubNode1Array.clear();
					SubNode2Array.clear();

					for (int k = 0; k < vertexObjectArray.size(); k++) {
						// add SubNode1 elements to SubNode1Array
						if (adj[intersectionHash.retrieve(roadArray.get(j).beginningPointID).index - 1][k] != 0) {
							SubNode1Array.add(retrieveIntersectionByIndex(k));
						}
						// add SubNode2 elements to SubNode2Array
						if (adj[intersectionHash.retrieve(roadArray.get(j).endPointID).index - 1][k] != 0) {
							SubNode2Array.add(retrieveIntersectionByIndex(k));
						}
					}

					for (int o = 0; o < SubNode1Array.size(); o++) {
						if (lookupUsedVertex(SubNode1Array.get(o).intersectionID, SubNode2Array) == false) {
							usedVertexArray.add(intersectionHash.retrieve(roadArray.get(j).beginningPointID));
							usedVertexArray.add(intersectionHash.retrieve(roadArray.get(j).endPointID));
						}
					}
				}
			}
		}

	}

	// method to implement the dijkstra shortest path algorithm
	public void dijkstra(Intersection s) {
		System.out.println("\nFinding the shortest path now... Please be patient.");

		initialNode = s;

		// set all vertices with distance to be POSITIVE_INFINITY
		// and known to be false
		for (int i = 0; i < vertexObjectArray.size(); i++) {
			vertexObjectArray.get(i).dist = Double.POSITIVE_INFINITY;
			vertexObjectArray.get(i).known = false;
		}

		// initialize vertex s' distance
		s.dist = 0.0;

		// index of vertex v in vertexObjectArray
		int j = 0;

		// declare smallestDist
		Double smallestDist;

		// while there is an unknown distance vertex
		for (int i = 0; i < vertexObjectArray.size(); i++) {

			// find the smallest distance from all unknown vertices
			smallestDist = findTheSmallestUnknownDistanceFromAllDistanceKnownVertices();

			// once smallestDist is found,
			// find the vertex v with smallestDist
			for (j = 0; j < vertexObjectArray.size(); j++) {
				if (vertexObjectArray.get(j).dist == smallestDist) {
					// set vertex v to known
					vertexObjectArray.get(j).known = true;
					break;
				}
			}

			// for each vertex w adjacent to v
			for (int k = 0; k < vertexObjectArray.size(); k++) {
				// v here is vertexObjectArray.get(j)
				// w here is vertexObjectArray.get(k)
				if (adj[vertexObjectArray.get(j).index - 1][k] != 0) {
					// if w is not known
					if (vertexObjectArray.get(k).known == false) {

						// find cvw, cost of edge from v to w
						if (vertexObjectArray.get(j).dist + getEdgeCost(vertexObjectArray.get(j),
								vertexObjectArray.get(k)) < vertexObjectArray.get(k).dist) {
							// update w's distance
							vertexObjectArray.get(k).dist = vertexObjectArray.get(j).dist
									+ getEdgeCost(vertexObjectArray.get(j), vertexObjectArray.get(k));
							vertexObjectArray.get(k).path = vertexObjectArray.get(j);
						}
					}
				}
			}
		}
	}

	// method to implement prims minimum weight spanning tree
	public void prims(Intersection s, IntersectionHash intersectionHash, RoadHash roadHash) {
		System.out.println("\nFinding the minimum spanning tree now... Please be patient.");

		initialNode = s;

		// set all vertices with distance to be POSITIVE_INFINITY
		// and known to be false
		for (int i = 0; i < vertexObjectArray.size(); i++) {
			vertexObjectArray.get(i).dist = Double.POSITIVE_INFINITY;
			vertexObjectArray.get(i).known = false;
		}

		// initialize vertex s' distance
		s.dist = 0.0;

		// index of vertex v in vertexObjectArray
		int j = 0;

		// declare smallestDist
		Double smallestDist;

		// while there is an unknown distance vertex
		for (int i = 0; i < vertexObjectArray.size(); i++) {

			// find the smallest distance from all unknown vertices
			smallestDist = findTheSmallestUnknownDistanceFromAllDistanceKnownVertices();

			// once smallestDist is found,
			// find the vertex v with smallestDist
			for (j = 0; j < vertexObjectArray.size(); j++) {
				if (vertexObjectArray.get(j).dist == smallestDist) {
					// set vertex v to known
					vertexObjectArray.get(j).known = true;
					break;
				}
			}
			
			
			// for each vertex w adjacent to v
			for (int k = 0; k < vertexObjectArray.size(); k++) {
				// v here is vertexObjectArray.get(j)
				// w here is vertexObjectArray.get(k)
				if (adj[vertexObjectArray.get(j).index - 1][k] != 0) {

					if (vertexObjectArray.get(k).known == false) {
						// minimize the cost of edge from v to w
						if (getEdgeCost(vertexObjectArray.get(j), vertexObjectArray.get(k)) < vertexObjectArray.get(k).dist) {
							vertexObjectArray.get(k).dist = getEdgeCost(vertexObjectArray.get(j), vertexObjectArray.get(k));
							
							// store the path coordinates for the minimum weight spanning tree
							usedVertexArray.add(intersectionHash.retrieve(vertexObjectArray.get(j).intersectionID));
							usedVertexArray.add(intersectionHash.retrieve(vertexObjectArray.get(k).intersectionID));
						}
					}
				}
			}
		}
	}

	// method to print the shortest path from the initialNode, i.e. vertex s,
	// which was passed in public void dijkstra(Vertex s), to the endNode
	public void printShortestPath(Intersection endNode) {
		// System.out.println("previous node is " + endNode.path.value);

		// store the vertices on the shortest path in reverse order
		finalNode = endNode;
		while (finalNode.index != initialNode.index) {
			pathArray.add(finalNode.intersectionID);

			// System.out.println("working?");

			if (finalNode.path != null) {
				finalNode = finalNode.path;
			} else {
				System.out.println("\nNo path available from vertex " + initialNode.intersectionID + " to vertex "
						+ endNode.intersectionID);
				System.out.println("The graph may have negative edge costs.");
				return;
			}
		}

		// reverse the list
		Collections.reverse(pathArray);
		System.out.println("The shortest weighted path from vertex " + initialNode.intersectionID + " to vertex "
				+ endNode.intersectionID + " is: ");
		System.out.print(initialNode.intersectionID + " -> ");
		for (int i = 0; i < pathArray.size(); i++) {
			if (i == pathArray.size() - 1)
				System.out.print(pathArray.get(i));
			else
				System.out.print(pathArray.get(i) + " -> ");
		}

		System.out.println("\n\nIn terms of roads, the path is: ");
		for (int j = 0; j < roadArray.size(); j++) {
			if (roadArray.get(j).beginningPointID.equals(initialNode.intersectionID)
					&& roadArray.get(j).endPointID.equals(pathArray.get(0))) {
				System.out.println(roadArray.get(j).roadID + " -> ");
			}
		}

		for (int j = 0; j < roadArray.size(); j++) {
			for (int i = 1; i + 2 < pathArray.size(); i = i + 2) {
				if (roadArray.get(j).beginningPointID.equals(pathArray.get(i))
						&& roadArray.get(j).endPointID.equals(pathArray.get(i + 1))) {
					System.out.print(roadArray.get(j).roadID + " -> ");
				}
			}

		}

		System.out.println("");
	}
}