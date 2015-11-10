// cc JoinReducer Reducer for join

package org.myorg;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// vv JoinReducer
public class JoinReducer extends Reducer<TextPair, Text, Text, Text> {
	
  @Override
  protected void reduce(TextPair key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
	  	  
      /* here comes the reducer code */
	  Iterator<Text> itr = values.iterator();
      while (itr.hasNext()) {
    	  Text value = itr.next();
    	  if (!isFromMapper1(value)) {
    		  context.write(value, new Text(this.getClass().getName()));
    	  } 
      }
      
  }
  
  private boolean isFromMapper1(Text text) {
	  return text.toString().equals(JoinMapper1.class.getName());
  }
}
// ^^ JoinReducer
