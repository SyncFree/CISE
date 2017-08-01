/*******************************************************************************
 * ===========================================================
 * This file is part of the CISE tool software.
 *
 * The CISE tool software contains proprietary and confidential information of Inria.
 * All rights reserved. Reproduction, adaptation or distribution, in whole or in part, is
 * forbidden except by express written permission of Inria.
 * Version V1.5.1., July 2017
 * Authors: Mahsa Najafzadeh, Michał Jabczyński, Marc Shapiro
 * Copyright (C) 2017, Inria
 * ===========================================================
 ******************************************************************************/

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



