/*
 * $Id: Skeleton.java 1311 2010-03-07 22:51:10Z euzenat $
 *
 * Copyright (C) INRIA, 2009
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */

// Alignment API classes
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;
import org.semanticweb.owl.align.Parameters;

// Alignment API implementation classes
import fr.inrialpes.exmo.align.impl.BasicParameters;
import fr.inrialpes.exmo.align.impl.ObjectAlignment;
import fr.inrialpes.exmo.align.impl.URIAlignment;
import fr.inrialpes.exmo.align.impl.BasicAlignment;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import fr.inrialpes.exmo.align.impl.renderer.OWLAxiomsRendererVisitor;
import fr.inrialpes.exmo.align.parser.AlignmentParser;

// Jena
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;

// Pellet

// IDDL
import fr.inrialpes.exmo.iddl.IDDLReasoner;
import fr.inrialpes.exmo.iddl.conf.Semantics;

// SAX standard classes
import org.xml.sax.SAXException;

// DOM Standard classes
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathConstants;

// Java standard classes
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * MyApp
 *
 * Reconcile two ontologies in various ways
 */

public class MyApp {

    String RESTServ = "http://aserv.inrialpes.fr/rest/";

    public static void main( String[] args ) {
	new MyApp().run( args );
    }

    public void run( String[] args ) {

	// Setting variables
	String myId = "JETest";
	Alignment al = null;
	URI uri1 = null;
	URI uri2 = null;
	//String u1 = "http://alignapi.gforge.inria.fr/tutorial2/ontology1.owl";
	//String u2 = "http://alignapi.gforge.inria.fr/tutorial2/ontology2.owl";
	String u1 = "file:ontology1.owl";
	String u2 = "file:ontology2.owl";
	String method = "fr.inrialpes.exmo.align.impl.method.StringDistAlignment";
	Parameters params = new BasicParameters();
	try {
	    uri1 = new URI( u1 );
	    uri2 = new URI( u2 );
	} catch (URISyntaxException use) { use.printStackTrace(); exit(); }

	try {

	    // ***** First exercise: matching *****
	    // (Sol1) Try to find an alignment between two ontologies from the server
	    // ask for it
	    // retrieve it
	    // parse it as an alignment

	    // (Sol2) Match the ontologies with a local algorithm
	    // match

	    // (Sol3) Match the ontologies on the server
	    // call for matching
	    // retrieve it
	    // parse it as an alignment

	    // Alternative: find an intermediate ontology between which there are alignments
	    // find (basically a graph traversal operation)
	    // retrieve them
	    // parse them
	    // compose them

	    // Supplementary:
	    // upload the result on the server
	    // store it

	    // ***** Second exercise: merging/transforming *****

	    // (Sol1) generate a merged ontology between the ontologies (OWLAxioms)

	    // (Sol2) import the data from one ontology into the other

	    // ***** Third exercise: querying and reasoning *****

	    // (Sol1) Use SPARQL to answer queries (at the data level)

	    // (Sol2) Use Pellet to answer queries (at the ontology level)

	} catch (Exception e) { e.printStackTrace(); exit();  }
    }

    public String getFromURLString( String u, boolean print ){
	URL url = null;
	String result = "<?xml version='1.0'?>";
	try {
	    url = new URL( u );
	    BufferedReader in = new BufferedReader(
    				new InputStreamReader(
    				  url.openStream()));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null) {
		if (print) System.out.println(inputLine);
		result += inputLine;
	    }
	    in.close();
	}
	catch ( MalformedURLException mue ) { mue.printStackTrace(); }
	catch ( IOException mue ) { mue.printStackTrace(); }
	return result;
    }

    public NodeList extractFromResult( String found, String path, boolean print ){
	Document document = null;
	NodeList nodes = null;
	try { // Parse the returned string as XML
	    DocumentBuilder parser =
		DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    document = parser.parse(new ByteArrayInputStream( found.getBytes() ));
	} catch ( ParserConfigurationException pce ) { pce.printStackTrace(); }
	catch ( SAXException se ) { se.printStackTrace(); }
	catch ( IOException ioe ) { ioe.printStackTrace(); }

	try { // Apply the Xpath expression
	    XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
	    XPathExpression expr = xpath.compile( path );
	    Object result = expr.evaluate( document, XPathConstants.NODESET );
	    nodes = (NodeList)result;
	    if ( print ) {
		for ( int i = 0; i < nodes.getLength(); i++) {
		    System.out.println(nodes.item(i).getNodeValue()); 
		}
	    }
	} catch (XPathExpressionException xpee) { xpee.printStackTrace(); }
	return nodes;
    }
}
