/*
 * $Id: SubsDistNameAlignment.java 1256 2010-02-15 22:27:54Z euzenat $
 *
 * Copyright (C) INRIA, 2003-2005, 2007-2010
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 */

package fr.inrialpes.exmo.align.impl.method; 

import java.util.ArrayList;
import java.util.Properties;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;

import fr.inrialpes.exmo.align.impl.DistanceAlignment;
import fr.inrialpes.exmo.ontowrap.OntowrapException;

/**
 * This class implements alignment based on substring distance
 * of class and property labels
 * THIS CLASS IS ONLY HERE FOR COMPATIBILITY PURPOSES
 *
 * @author J�r�me Euzenat
 * @version $Id: SubsDistNameAlignment.java 1256 2010-02-15 22:27:54Z euzenat $ 
 */

public class SubsDistNameAlignment extends DistanceAlignment implements AlignmentProcess {

	SubsDistNameAlignment(){}
	
	public void align( Alignment alignment, Properties param ) throws AlignmentException {
		for (Object cl2: ontology2().getClasses()) {
			for ( Object cl1: ontology1().getClasses() ){
				// add mapping into alignment object 
				addAlignCell(cl1,cl2,"=",match(cl1,cl2));    
			}
		}

	}

	private double match(Object o1, Object o2) throws AlignmentException {
		try {
			String s1 = ontology1().getEntityName(o1);
			String s2 = ontology2().getEntityName(o2);
			
			// Unnamed entity = max distance
			if ( s1 == null || s2 == null ) return 1.;

			if (s1.equals(s2)) return 0.;

			ArrayList<String> s1Grams = createGrams(s1);
			ArrayList<String> s2Grams = createGrams(s2);
			int totalLength = calculateIntersection(s1Grams, s2Grams);
			double score = Math.abs( 1.0 - ( (double)totalLength / (double)(((s1.length() + s2.length()) * 2))));

			return score;
		} catch ( OntowrapException owex ) {
			throw new AlignmentException( "Error getting entity name", owex );
		}

	}

	private int calculateIntersection(ArrayList<String> s1Grams, ArrayList<String> s2Grams) {
		int counter = 0;
		for (String str : s1Grams) {
			if (s2Grams.contains(str)) {
				counter += str.length();
			}
		}
		return counter;
	}

	private ArrayList<String> createGrams(String s1) {
		ArrayList<String> coll = createNGrams(s1, 1);
		coll.addAll(createNGrams(s1, 2));
		coll.addAll(createNGrams(s1, 3));
		return coll;
	}

	private ArrayList<String> createNGrams(String s1, int n) {
		ArrayList<String> coll = new ArrayList<String>();

		String prev = "";
		for (char c : s1.toCharArray()) {
			if (prev.length() == n - 1) {
				coll.add(prev + c);
				prev = prev.substring(1);
			}
			prev += c;
		}
		return coll;
	}
}
