// cc JoinMapper1 Mapper for a reduce-side join

package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//vv JoinMapper1
public class JoinMapper1
    extends Mapper<LongWritable, Text, TextPair, Text> {

/* here define the variables */
	final static String typeName = "species";
	final static String attrContains = "P_KK";
       
  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
  
	  	System.out.println("Mapper1 key = "+key);
	  	System.out.println("Mapper1 value = "+value);
	  	System.out.println("Mapper1 context = "+context.toString());
	  	
/* here the code for retrieving the triples from file01 and send the prefix of the dewey_pid as key */
	  	String line = value.toString();
	  	String[] values = line.split(" ");
	  	String dewey = values[0];
	  	String type = values[1];
	  	String val = values[2];
	  	
	  	if (type.equals(typeName) && val.contains(attrContains)) {
	  		String deweyPid = dewey.substring(0, dewey.length() - 4);
	  		TextPair pair = new TextPair(deweyPid, val);
	  		context.write(pair, new Text(this.getClass().getName()));
	  	}
  }
}
//^^ JoinMapper1
