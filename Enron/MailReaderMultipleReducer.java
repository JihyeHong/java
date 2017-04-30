package enron;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

class MailReaderMultipleReducer extends Reducer<EdgeWritable, NullWritable, NullWritable, Text> {
	// You can put instance variables here to store state between iterations of
	// the reduce task.
	
	private MultipleOutputs<NullWritable, Text> multipleOutputs;
	
	private final Text out = new Text();
	private final NullWritable noval = NullWritable.get();
	private final Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Etc/UTC"));
	
	public static final Log logger = LogFactory.getLog(MailReaderMultipleReducer.class);
	
	// The setup method. Anything in here will be run exactly once before the
	// beginning of the reduce task.
	
	public void setup(Context context) throws IOException, InterruptedException {
		multipleOutputs = new MultipleOutputs<NullWritable, Text>(context);
	}
	
	// This method will return a string appending year and month of date timestamp.
	private String parseBucket(String date) {
		// System.out.println("stripCommand=" + stripCommand);

		boolean flag = true;
		String bucketName = null;
		
		try {
			cal.setTime(MailReaderMultipleOutputs.sdf.parse(date));
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			
			if(year < 1998 || year > 2002){
				flag = false;
			}else if(year == 1998 && month < 11){
				flag = false;
			}else if(year == 2002 && month > 6){
				flag = false;
			}
			
			if(flag!=false){
				bucketName = year+"_"+month;
			}
		} catch (ParseException e) {
			return "-1";
		}
		return flag?bucketName:"-1";
	}

	// The reducer will emit the edges (email, email, timestamp)
	// with timestamp being formatted as a normal date string
	// relative to the UTC time zone.
	public void reduce(EdgeWritable key, Iterable<NullWritable> values, Context context)
			throws IOException, InterruptedException {

			MailReaderMultipleOutputs.sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
			String date = MailReader.sdf.format(key.getTS());
			
			out.set(key.get(0) + " " + key.get(1));
			//context.write(noval, out);
			
			String bucket = parseBucket(date);
			
			if(!bucket.equals("-1")){
				multipleOutputs.write(noval, out, parseBucket(date));
			}
	}

	// The cleanup method. Anything in here will be run exactly once after the
	// end of the reduce task.
	public void cleanup(Context context) throws IOException, InterruptedException {
		multipleOutputs.close();
	}
}