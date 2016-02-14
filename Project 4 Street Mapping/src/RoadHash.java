// CSC 172
// Project 4 Street Mapping
// Student Name: Zunran Guo
// Student ID: 28279136
// All work is original.

public class RoadHash implements RoadHashing {

	// declare hashTableArray as a string array to store data
	Road[] hashTableArray;

	// declare capacity of hashTableArray
	// i.e. the numberOfUniqueItems in hashTableArray
	int capacity = 0;

	// declare loadFactor of hashTableArray
	double loadFactor;

	// declare totalItems read
	int totalItems = 0;
	
	// declare collisions occurred
	int collision = 0;

	// method to construct a hash with hashTableArray of size 20996011 for speed optimization
	// reference: https://en.wikipedia.org/wiki/Mersenne_prime
	public RoadHash() {
		hashTableArray = new Road[20996011];
	};

	// method to return the size of hashTableArraySize
	public int size() {
		return hashTableArray.length;
	}

	// method to generate a bash key for each string key
	// reference: https://en.wikipedia.org/wiki/Universal_hashing#Hashing_strings
	public static int hash(Road key, int tableSize) {
		int hashVal = 0;
		
		for (int i = 0; i < key.roadID.length(); i++){
			hashVal = (31 * hashVal + key.roadID.charAt(i)) % tableSize;
		}
		
		hashVal %= tableSize;

		if (hashVal < 0)
			hashVal += tableSize;

		return hashVal;
	}

	// method to search through hashTableArray for the item you look up
	public boolean lookup(Road x) {
		int nullOccurance = 0;
		int hashValue = hash(x, size());
		if (hashTableArray[hashValue] == null)
			return false;
		else if (hashTableArray[hashValue].roadID.equalsIgnoreCase(x.roadID) == true)
			return true;
		else{
			// searching forward
			for (int i = hashValue + 1; i < hashTableArray.length; i++) {
				if (hashTableArray[i] == null)
					nullOccurance++;
				if (hashTableArray[i] != null && hashTableArray[i].roadID.equalsIgnoreCase(x.roadID))
					return true;
				if (nullOccurance == 2)
					return false;
			}
			// searching backward
			for (int i = 0; i < hashValue; i++) {
				if (hashTableArray[i] != null && hashTableArray[i].roadID.equalsIgnoreCase(x.roadID))
					return true;
			}
		}
		// if x is not in hashTableArray, return false
		return false;
	}

	// method to perform Linear Probing
	public void linearProbing(Road itemToBeInserted, Road[] arrayPerformedOn) {
		// update collisions occurred
		collision++;
		
		int insertionSuccessfulIndicator = 0;
		
		// declare hash key
		int i = hash(itemToBeInserted, size());
		
		while (insertionSuccessfulIndicator == 0) {
			// start insertion trial from hashTableArray[hash(item, size())]
			for (; i < hashTableArray.length; i++) {

				// if there is empty space, insert item
				if (arrayPerformedOn[i] == null) {
					arrayPerformedOn[i] = itemToBeInserted;
					// System.out.println("inserted item, " + itemToBeInserted + ", by linear probing has index: " + i);
					insertionSuccessfulIndicator = 1;
					break;
				}
			}
			// if there is no space till the end,
			// search space from the beginning
			i = 0;
		}
	}

	// method to insert data into hashTableArray
	public void insert(Road item) {
		// update totalItems for each call of the "insert" method
		totalItems++;

		// add the given string to the table if it is new (duplicate does not exist)
		if (lookup(item) == false) {
			// update capacity for each unique item
			capacity++;
			
			// if the load factor exceeds 50%, expand the array
			if (loadFactor > 0.5) {

				// System.out.println("\nNow start rehashing: ");

				// declare enlargedHashTableArray
				Road[] enlargedHashTableArray = new Road[size() * 2];

				// rehash each inserted item in hashTableArray
				for (int i = 0; i < hashTableArray.length; i++) {
					if (hashTableArray[i] != null) {
						/*
						// if there is empty space in enlargedHashTableArray[hash(hashTableArray[i], size())]
						if (enlargedHashTableArray[hash(hashTableArray[i], size())] == null) {
							enlargedHashTableArray[hash(hashTableArray[i], size())] = hashTableArray[i];
							// System.out.println("old inserted item, " + hashTableArray[i] + ", in enlarged array has index: " + hash(hashTableArray[i], size()));
						}

						// else perform linear probing
						else
							linearProbing(hashTableArray[i], enlargedHashTableArray);
						*/
						// redundent rehashing procedure is dropped for speed optimization
						enlargedHashTableArray[i] = hashTableArray[i];
					}
				}

				// after putting the hash old items to enlargedHashTableArray
				// hash the new item about to be inserted
				// if there is empty space in enlargedHashTableArray
				int hashValue = hash(item, size());
				if (enlargedHashTableArray[hashValue] == null) {
					enlargedHashTableArray[hashValue] = item;
					// System.out.println("new inserted item, " + item + ", in enlarged array has index: " + hash(item, size()));
				}

				// else perform linear probing
				else
					linearProbing(item, enlargedHashTableArray);

				// replace hashTableArray to enlargedHashTableArray
				hashTableArray = enlargedHashTableArray;
			}

			// if the load factor does not exceed 50%
			else {
				// if there is empty space in hashTableArray
				int hashValue = hash(item, size());
				if (hashTableArray[hashValue] == null) {
					hashTableArray[hashValue] = item;
					// System.out.println("directly inserted item, " + item + ", has index: " + hash(item, size()));
				}

				// else perform linear probing
				else
					linearProbing(item, hashTableArray);
			}

			// update loadFactor for each insertion
			loadFactor = (double) capacity / (double) size();

			// testing
			// System.out.println("hashTableArray size now is: " + size());
			// System.out.println("loadFactor now is: " + loadFactor);
		}
	}

	// method to print data in hashTableArray
	public void print() {
		for (int i = 0; i < hashTableArray.length; i++) {
			// print the data in hashTableArray if the information stored is not null
			if (hashTableArray[i] != null) {
				System.out.println(i + ") " + hashTableArray[i].roadID);
			}
		}
	}
}
