package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XMLCounter implements ContentHandler {

	/* here comes var declaration */
	
	private final String rootPath = "";
	private List<String> uniquePaths = new ArrayList<String>();
	private List<String> path = new ArrayList<String>();
	
	private List<Integer> levelNumbers = new ArrayList<Integer>();
	private int level = 0;


	public void startDocument( ) throws SAXException {

     /* here comes the init code */
		//levelNumbers.add(0);
		
        
    }

	public void startElement(String uri, String localName, String qName, 
	            Attributes attributes) throws SAXException {
		level++;
		path.add(localName);
		
		
		if(levelNumbers.size() < level) {
			levelNumbers.add(0);
		}
		
		int num = levelNumbers.get(level - 1);
		levelNumbers.set(level - 1, num + 1);

		String path = currentPath();
		String tagPath = path + localName + ".tag";
		String attributePath = path + localName + ".attr";
		if (isCurrentPathUnique()) {
			uniquePaths.add(currentPath());
			// Create files for tag and attr
			createDirectory(path);
			createEmptyFile(tagPath);
			createEmptyFile(attributePath);
		} 
		appendToFile(tagPath, getDewey() + "\t" + localName + "\n");
		String attributesContent = "";
		for (int i=0; i<attributes.getLength(); i++) {
			String line = getDewey() + "\t" + attributes.getLocalName(i) + " " + attributes.getValue(i) + "\n";
			attributesContent += line;
		}
		appendToFile(attributePath, attributesContent);	
		
        /* here comes code after the startElement is met */
        /* for attributes too */
        
	}
	
	private void createDirectory(String path) {
		new File(path).mkdirs();
	}
	
	private void createEmptyFile(String path) {
		try {
			new PrintWriter(path).close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	private void appendToFile(String path, String content) {
	
		try {
		    FileWriter fileWriter = new FileWriter(path, true);
	        BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);
	        bufferedWriter.append(content);
	        bufferedWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	private String getDewey() {
		String res = "";
		for (int i = 0; i < level; i++) {
			res += levelNumbers.get(i) + ".";
		}
		res = res.substring(0, res.length() - 1);
		
		return res;
	}
	
	private String currentPath(){
		String fullPath = "";
		for( String localName : path ) 
			fullPath += localName + "/";
		return fullPath;
	}
	
	private boolean isCurrentPathUnique() {
		return !uniquePaths.contains(currentPath());
	}

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        
    	level--;
        
        path.remove(path.size() - 1);
        
        /* here comes code after the endElement is met */
    }
    
    public void characters(char ch[], int start, int length) throws SAXException {
    	/* here comes code after the startElement is met */
		
    }
    
    
    public void endDocument( ) throws SAXException {
        
    }
    
    // Do-nothing methods we have to implement only to fulfill
    // the interface requirements:
    
    public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {}
    
    public void processingInstruction(String target, String data) 	   
    throws SAXException {}
    public void setDocumentLocator(Locator locator) {}
    public void startPrefixMapping(String prefix, String uri)
    throws SAXException {}
    public void endPrefixMapping(String prefix) throws SAXException {}
    public void skippedEntity(String name) throws SAXException {}
    
}
