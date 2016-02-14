import java.util.ArrayList;

public class TestProgram {
	//test
	public static int positions = 6;
	
	public static String[] possibleColors = new String[] { "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PINK" };
	static int n = possibleColors.length + 1;
	static ArrayList<String> arrayList = new ArrayList<String>();
	static String[] stringArray = new String[arrayList.size()];

	//test
	//static ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
	
	static MasterMind testInstance = new MasterMind();

	public static void load() {
			for (int i = 0; i < possibleColors.length; i++) {
				
				n = n - 1;
				if (n > 0){ 
					arrayList.add(possibleColors[possibleColors.length - n]);
					load();
					
					// insert into node
					stringArray = arrayList.toArray(stringArray);
					testInstance.insert(stringArray);
				}
				
			}
	}
	
	public static void main(String[] args){
		load();
		testInstance.printList();
	}
	
	/*
	public static void load() {
		while(positions > 0)
			for (int i = 0; i < n; i++) {
				ArrayList<String> array = new ArrayList<String>();
				array.add(possibleColors[i]);
				arrayList2.add(array);
			}
		positions--;
		stringArray = arrayList.toArray(stringArray);
		testInstance.insert(stringArray);
		System.out.println("Into\n");

	}
	*/
	/*
	public static void load() {
		for(int i = 0; i < positions; i++){
			arrayList2.add(0);
		}
		int j = 0;
		while(arrayList2.get(positions-1) < n){
			if(arrayList2.get(j) < n){
				int temp = arrayList2.get(j);
				temp++;
				arrayList2.set(j, temp);
			}
			else
				j++;
		}
	}
	*/
}
