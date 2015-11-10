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
	  	System.out.println("Mapper2 key = "+key);
	  	System.out.println("Mapper2 value = "+value);
	  	System.out.println("Mapper2 context = "+context.toString());
	  	
	  	String line = value.toString();
	  	String[] values = line.split(" ");
	  	String dewey = values[0];
	  	String type = values[1];
	  	String val = values[2];
	  	
	  	if (type.equals(typeName)) {
	  		String deweyPid = dewey.substring(0, dewey.length() - 4);
	  		TextPair pair = new TextPair(deweyPid, val);
	  		context.write(pair, new Text(this.getClass().getName()));
	  	}
  }
}
// ^^ JoinMapper2
