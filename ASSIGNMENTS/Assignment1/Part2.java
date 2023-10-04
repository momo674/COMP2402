package comp2402a1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.LinkedList;

public class Part2 {
	
	/**
	 * Your code goes here - see Part0 for an example
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		
		Queue<String> data = new LinkedList<String>();
		// int line_length;
		// int previous_length;
		// int longest_length;

		String biggest = "";
		String previous = "";
		//boolean first_nonempty_string_added = false;
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			
            //make sure line is not empty
			if (data.isEmpty() && !line.isEmpty()) {
				data.add(line);
				biggest = line;
				previous = line;
			}

			else {
				if (line.compareTo(biggest) > 0 && !line.isEmpty()) {
					data.add(line);
					// longest_length = line_length;
					biggest = line;
					previous = line;
				}
				else if (line.compareTo(previous) < 0 && !line.isEmpty()) {
					data.add(line);
					previous = line;
				}
			}

			
        }	

		for (String text : data) {
            w.println(text);
        }
	}

	/**
	 * The driver.  Open a BufferedReader and a PrintWriter, either from System.in
	 * and System.out or from filenames specified on the command line, then call doIt.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader r;
			PrintWriter w;
			if (args.length == 0) {
				r = new BufferedReader(new InputStreamReader(System.in));
				w = new PrintWriter(System.out);
			} else if (args.length == 1) {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(System.out);				
			} else {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(new FileWriter(args[1]));
			}
			long start = System.nanoTime();
			doIt(r, w);
			w.flush();
			long stop = System.nanoTime();
			System.out.println("Execution time: " + 1e-9 * (stop-start));
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}
	}
}
