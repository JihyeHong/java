package enron;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class MailReaderMultipleMapper extends Mapper<Text, BytesWritable, EdgeWritable, NullWritable> {
	
	private final EdgeWritable edgeOut = new EdgeWritable();
	private final NullWritable noval = NullWritable.get();
	private final Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Etc/UTC"));

	public static final Log logger = LogFactory.getLog(MailReaderMapper.class);
	
	public Map<String, Integer> empList = new HashMap<String, Integer>();
	
	// Extract values after each header
	private String stripCommand(String line, String com) {
		int len = com.length();
		if (line.length() > len)
			return line.substring(len);
		return null;
	}

	private String procFrom(String line) {
		if (line == null)
			return null;
		String[] froms;
		String from = null;
		do {
			froms = line.split("\\s+|,+", 5); // split delimiters '\s' white space | ','
			// This will only include Email accounts originating from the Enron domain
			if (froms.length == 1 && froms[0].matches(".+@enron\\.com"))
				from = froms[0];
			for (int i = 0; i < froms.length - 1; i++) {
				if (froms[i].matches(".+@enron\\.com")) {
					from = froms[i];
					break;
				}
			}
			line = froms[froms.length - 1];
		} while (froms.length > 1 && from == null);
		return from;
	}

	// Parce recipients
	private void procRecipients(String line, List<String> recipients) {
		// Compile a list of recipients
		// Make sure only select Email addresses ending with
		// @enron.com
		
		StringTokenizer st = new StringTokenizer(line, ",");
		
		while(st.hasMoreTokens()){		
			String rc = st.nextToken();
			if(rc.endsWith("@enron.com")){
				String recipient = rc;
				recipient = recipient.trim();
				recipients.add(recipient);
			}
		}
		
	}
	
	// This method will return the date timestamp
	// of an email as number of milliseconds since 
	// the beginning of epoch in UTC.
	private long procDate(String stripCommand) {
		// System.out.println("stripCommand=" + stripCommand);
		try {

			cal.setTime(MailReader.sdf.parse(stripCommand.trim()));
							
		} catch (ParseException e) {
			return -1;
		}
		return (cal.get(Calendar.YEAR) >= 1998 && cal.get(Calendar.YEAR) <= 2002) ?
				cal.getTimeInMillis() : -1;
	}
	
	@Override
	public void setup(Context context) throws IOException,  InterruptedException {
		Scanner s = new Scanner(new File("/Users/jihye/Desktop/RHUL/Term2/Project/full-positions.csv"));
		s.nextLine(); //skip header
		
		while(s.hasNextLine()) {
			String line = s.nextLine();
			// Read the data from the file
			// to efficiently lookup by Email address
			
			String [] lines = line.split(",");
			int id = Integer.parseInt(lines[0]);
			
			for(int i=3; i<7; i++){ //index from 3 to 7, email addresses
				String email = lines[i];
				if(!"".equals(email)){
					empList.put(email, id);
				}
			}
			
		}
		s.close();
	}
	

	@Override
	public void map(Text key, BytesWritable value, Context context)
			throws IOException, InterruptedException {

		byte[] bytes = value.getBytes();
		Scanner scanner = new Scanner(new ByteArrayInputStream(bytes), "UTF-8");
		String from = null; // Sender's Email address
		ArrayList<String> recipients = new ArrayList<String>(); // List of recipients' Email addresses  
		long millis = -1; // Date
		
		for (; scanner.hasNext(); ) {
			String line = scanner.nextLine();
			if (line.startsWith("From:")) {
				from = procFrom(stripCommand(line, "From:"));
			}
			else if (line.startsWith("To:")) {
				procRecipients(stripCommand(line, "To:"), recipients);
			}
			else if (line.startsWith("Cc:")) {
				procRecipients(stripCommand(line, "Cc:"), recipients);
			}
			else if (line.startsWith("Bcc:")) {
				procRecipients(stripCommand(line, "Bcc:"), recipients);
			}
			else if (line.startsWith("Date:")) {
				millis = procDate(stripCommand(line, "Date:"));
			}
			else if (line.startsWith("\t")) { //if it has many recipient in several rows
				procRecipients(stripCommand(line, ""), recipients);					
			}
			if (line.equals("")) { // Empty line indicates the end of the header
				break;
			}
		}
		scanner.close();
		
		// Eliminate duplicated triples.
		HashSet<String> hs = new HashSet<String>(recipients);
		ArrayList<String> newRecipients = new ArrayList<String>(hs);

		if (from != null && newRecipients.size() > 0 && millis != -1) { 
			// This will fail with exception if the asserted condition
			// is false. This is a useful debugging practice.
			assert(from.endsWith("@enron.com")); 
			
			if(empList.containsKey(from)){
				int senderId = empList.get(from);
				edgeOut.set(0, senderId);
				
				for(String rp:newRecipients){
					if(empList.containsKey(rp) && empList.get(rp)!=senderId){ // Remove self-cycles
						edgeOut.set(1, empList.get(rp));
						edgeOut.setTS(millis);
						
						context.write(edgeOut, noval);
					}
				}
			}
		}				
	}

	public void cleanup(Context context) throws IOException,
	InterruptedException {

	}
}
