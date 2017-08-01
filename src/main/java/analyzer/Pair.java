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

package analyzer;

public class Pair<K, V> {
    private  K element0;
    private  V element1;
	 //override hashcode and equals
    
    public Pair() {
		// TODO Auto-generated constructor stub
	}
    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    
    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

   

	public K getElement0() {
        return element0;
    }

    public V getElement1() {
        return element1;
    }
    
    public boolean equals(Pair o) {
        if (o == null) return false;
        Pair pairo = (Pair) o;
        return this.element0.equals(pairo.getElement0()) &&
               this.element1.equals(pairo.getElement1());
      }
	
}


