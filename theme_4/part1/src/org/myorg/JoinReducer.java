// cc JoinReducer Reducer for join

package org.myorg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// vv JoinReducer
public class JoinReducer extends Reducer<TextPair, Text, Text, Text> {
	
  @Override
  protected void reduce(TextPair key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
	  	  
      /* here comes the reducer code */
	  Iterator ctxItr = context.getValues().iterator();
	  
	  List<String> mapper2Values = new ArrayList<String>();
	  
	  boolean gotMatch = false;
      while (ctxItr.hasNext()) {
    	  ctxItr.next();
    	  String mapperValue = context.getCurrentValue().toString();
    	  if (isFromMapper1(mapperValue)) {
    		  gotMatch = true;
    	  } else {
    		  mapper2Values.add(context.getCurrentKey().toString());
    	  }
      }
      
      if (gotMatch) {
    	  for(String matchingValue : mapper2Values) {
    		  context.write(new Text(matchingValue), new Text(" "));
    	  }
      }
      
  }
  
  private boolean isFromMapper1(String text) {
	  return text.equals(JoinMapper1.class.getName());
  }
}
// ^^ JoinReducer
