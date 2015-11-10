package parser;

import org.xml.sax.*;
import org.xml.sax.helpers.*; 


public class ReadXMLFile {
 
   public static void main(String argv[]) {
	   
	    try{
	        String executionPath = System.getProperty("user.dir");
	        System.out.print("Executing at =>"+executionPath.replace("\\", "/"));
	      }catch (Exception e){
	        System.out.println("Exception caught ="+e.getMessage());
	      }
 
    try {
 
        XMLReader parser;
        try {
         parser = XMLReaderFactory.createXMLReader( );
        }
        catch (SAXException e) {
          // fall back on Xerces parser by name
          try {
            parser = XMLReaderFactory.createXMLReader(
             "org.apache.xerces.parsers.SAXParser");
          }
          catch (SAXException ee) {
            System.err.println("Couldn't locate a SAX parser");
            return;
          }
        }

	   parser.setContentHandler(new XMLCounter( ));

       parser.parse("/home/jacob/courses/tddd43/theme_4/BIOMD0000000009.xml");
 
     } catch (Exception e) {
       e.printStackTrace();
     }
 
   }
 
}