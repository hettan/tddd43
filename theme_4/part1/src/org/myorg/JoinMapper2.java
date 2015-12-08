// cc JoinMapper2 Mapper for a reduce-side join
package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

// vv JoinMapper2
public class JoinMapper2
    extends Mapper<LongWritable, Text, TextPair, Text> {

        /* here define the variables */
	final static String typeName = "species";
	
  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  	String line = value.toString();
	  	String[] values = line.split("\\s+");

		String dewey = values[0];
	  	String type = values[1];
	  	String val = values[2];
	  	
	  	if (type.equals(typeName)) {
		    String[] deweyNumbers = dewey.split("\\.");
		    int deweyRemoveLength;
		    if (deweyNumbers.length > 2) {
			deweyRemoveLength = deweyNumbers[deweyNumbers.length - 2].length() + deweyNumbers[deweyNumbers.length - 1].length() + 2;
		    } else {
			deweyRemoveLength = dewey.length();
		    }
		    String deweyPid = dewey.substring(0, dewey.length() - deweyRemoveLength);
	  		TextPair pair = new TextPair(deweyPid, val);
	  		context.write(pair, new Text(this.getClass().getName()));
	  	}
  }
}
// ^^ JoinMapper2
