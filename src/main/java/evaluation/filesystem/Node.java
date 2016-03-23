package evaluation.filesystem;

import java.util.LinkedList;

public class Node {

	private int value;
	private LinkedList<Tree> child; 
	private LinkedList<Node> next; 
	
	
	public class Tree {
		
		private int count ;
		private int isfile;
		private  LinkedList<Node> child_list; 

	}
	
	
}



