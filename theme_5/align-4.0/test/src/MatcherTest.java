/*
 * $Id: MatcherTest.java 1364 2010-03-26 15:59:34Z jdavid $
 *
 * Copyright (C) INRIA, 2008-2010
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

//package test.com.acme.dona.dep;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;
//import org.testng.annotations.*;

import org.semanticweb.owl.align.AlignmentVisitor;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.Evaluator;

import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import fr.inrialpes.exmo.ontosim.string.StringDistances;
import fr.inrialpes.exmo.align.impl.eval.PRecEvaluator;
import fr.inrialpes.exmo.align.impl.URIAlignment;

import fr.inrialpes.exmo.ontowrap.OntologyCache;

import fr.inrialpes.exmo.align.parser.AlignmentParser;
import fr.inrialpes.exmo.align.util.NullStream;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;
import java.util.Properties;

/**
 * These tests corresponds to the README file in the main directory
 */

public class MatcherTest {

    private AlignmentProcess alignment = null;

    // Add one test with instances in the ontology (the same ontology)

    @Test(groups = { "full", "impl", "raw" })
    public void routineTest8() throws Exception {
	/*
$ java -jar lib/Procalign.jar file://$CWD/examples/rdf/edu.umbc.ebiquity.publication.owl file://$CWD/examples/rdf/edu.mit.visus.bibtex.owl -i fr.inrialpes.exmo.align.impl.method.StringDistAlignment -DstringFunction=levenshteinDistance -o examples/rdf/bibref.rdf
	*/
	Properties params = new Properties();
	params.setProperty( "stringFunction", "levenshteinDistance");
	params.setProperty( "noinst", "1");
	alignment = new StringDistAlignment();
	assertNotNull( alignment, "ObjectAlignment should not be null" );
	assertEquals( alignment.nbCells(), 0 );
	alignment.init( new URI("file:examples/rdf/edu.umbc.ebiquity.publication.owl"), new URI("file:examples/rdf/edu.mit.visus.bibtex.owl"));
	alignment.align( (Alignment)null, params );
	assertEquals( alignment.nbCells(), 42/*44*/ );
	FileOutputStream stream = new FileOutputStream("test/output/bibref.rdf");
	PrintWriter writer = new PrintWriter (
			  new BufferedWriter(
			       new OutputStreamWriter( stream, "UTF-8" )), true);
	AlignmentVisitor renderer = new RDFRendererVisitor( writer );
	alignment.render( renderer );
	writer.flush();
	writer.close();
	//assertEquals( stream.toString().length(), 1740, "Rendered differently" );
	/*
$ java -jar lib/Procalign.jar file://$CWD/examples/rdf/edu.umbc.ebiquity.publication.owl file://$CWD/examples/rdf/edu.mit.visus.bibtex.owl -i fr.inrialpes.exmo.align.impl.method.StringDistAlignment -DstringFunction=subStringDistance -t .4 -o examples/rdf/bibref2.rdf
	*/
	alignment.cut( "hard", 0.55 );
	assertEquals( alignment.nbCells(), 32 ); /* With  .4, I have either 36 or 35! */
	stream = new FileOutputStream("test/output/bibref2.rdf");
	writer = new PrintWriter (
			  new BufferedWriter(
			       new OutputStreamWriter( stream, "UTF-8" )), true);
	alignment.render( new RDFRendererVisitor( writer ) );
	writer.flush();
	writer.close();
	//assertEquals( stream.toString().length(), 1740, "Rendered differently" );
    }

    /* This tests an error when the distance name is incorrect */
    @Test(groups = { "full", "impl", "raw" }, expectedExceptions = AlignmentException.class)
    public void routineTest9() throws Exception {
	Properties params = new Properties();
	params.setProperty( "stringFunction", "teinDistance");
	params.setProperty( "noinst", "1");
	alignment = new StringDistAlignment();
	assertNotNull( alignment, "ObjectAlignment should not be null" );
	assertEquals( alignment.nbCells(), 0 );
	alignment.init( new URI("file:examples/rdf/edu.umbc.ebiquity.publication.owl"), new URI("file:examples/rdf/edu.mit.visus.bibtex.owl"));
	alignment.align( (Alignment)null, params );
    }
}
