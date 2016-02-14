CSC 172
Project 4 Street Mapping

Student Name: Zunran Guo
Student ID: 28279136

File Description:

The file monroe-streets.tab contains two parts describing a graph The graph contains approximately 20000 intersections and about the same number of roads connecting intersections.The first part of the file contains information about intersections Intersections are described by a the letter “i” followed by a unique Id, followed by an (x,y) coordinate.i i1250717798 407.27386164817847 355.58565000000374 i i1251697920 371.8033488114149 349.6009805555548
The second part of the file contains information about roads Roads are described by a the letter “r” followed by a unique Id, followed by a pair of intersection identifiers..r r14087276-1 i134123750 i134122617 r r14087276-2 i134122617 i134122618

There are 13 java files in this project.

Intersection.java, Intersection Hashing, IntersectionHash.java, Road.java, RoadHashing, RoadIntersectionHash are the classes and hash maps, respectively, of objects Road and Intersection. AdjList.java, Edge.java, and Vertex.java are classes in this project as well, created for adjacency list, edge, and vertex. loadData.java loads the the data file in .tab or .txt format. And LinesComponent is the class created for drawing maps using lines.

The public static void main method is called in Mapping.java.

In this project, I have a function hidden for the background image to make the map look nicer and I also made both Prims and Kruskal algorithm. I think Kruskal is working but maybe a bit too slow.

Two most significant files for this project are Graph.java and LinesComponent.java. There are many auxiliary methods in Graph.java, mainly for testing purposes and most of them are adapted from the labs. The new methods, other than the modified methods from the previous labs, are described as the following:

public void readGraphVertices(IntersectionHash intersectionHash, RoadHash roadHash) reads the graph data from hash maps and assign attributes to the objects created. The intersection (vertex) indices and the edgecosts are loaded separately in different array lists and adjacency matrix.

public boolean lookupUsedVertex(String id, ArrayList<Intersection> ObjectArrayList) looks up used vertex in assigned arraylist.

public Double findTheSmallestUnknownDistanceFromAllDistanceKnownVertices() finds the smallest unknown distance from all the known vertices

public float findTheMinimumWeightfromAllUnknowRoads(ArrayList<Road> roadArray) is the method for the kruskal algorithm

public Intersection retrieveIntersectionByIndex(int index) retrieves Intersection by index

public Intersection retrieveIntersectionByID(String id) retrieves Intersection by ID

public void kruskal(IntersectionHash intersectionHash, RoadHash roadHash) implements kruskal minimum weight spanning tree (still under testing and development)

public void dijkstra(Intersection s) implements dijkstra minimum weight spanning tree

public void prims(Intersection s, IntersectionHash intersectionHash, RoadHash roadHash) implements prims minimum weight spanning tree

public void printShortestPath(Intersection endNode) prints the shortest path from the initialNode, i.e. vertex s, which was passed in public void dijkstra(Vertex s), to the endNode

In LinesComponent.java, public class Line constructs a line object, as the basis, for all the line drawn on the map. protected void paintComponent(Graphics g) is the graphics component for this project. private void paintPrims(Graphics2D g2d) is the paint method for Prims algorithm and private void paintDijkstra(Graphics2D g2d) is the method for Dijkstra algorithm. public void paint(Graphics2D g2d) is the method to draw the raw map. public Intersection findIntersectionObject(Graph graph, String id)  is the method to retrieve the Intersection Object by its ID. public void drawlines() is the key method to perform all sorts of drawing. User prompting interface is built into this method too.

The runtime of my program can be broken down to several pieces. The loading data of hash maps takes linear time (O(N)). So is the complexity to read graph data (vertices, distance etc.) The auxiliary lookup methods all take around linear time (Most of them are simple 1 level of for loops). When it goes to Dijkstra’s algorithm, the run time goes up to O(N^2) (O(E + |V|^2) where E is the number of vertices and V is the number of vertices)and when the program runs Prim’s algorithm, the run time is also O(N^2). So overall speaking, if we add up the run time of all the parts and piece, my program’s complexity scales quadratically. 

Integrated Development Environment:
Eclipse

Summary:
For this project, the program automatically reads in a data file named “monroe-county.tab”, in which the road intersection are described by a the letter “i” followed by a unique Id, followed by an (x,y) coordinate. and the Roads r described by a the letter “r” followed by a unique Id, followed by a pair of intersection identifiers. A map representing the streets are then graphically displayed and a minimum spanning tree is draw in a second color. The program is also able to find the shortest path between two intersections in the map. A driver method with user prompts is built into the program too.
