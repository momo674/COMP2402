package comp2402a1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Part9 {

	/**
	 * Your code goes here - see Part0 for an example
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		//Use a hashmap to store lines pointing at their count value
//use set to determine if we have seen the line before




		//HashSet<String>() s = new HashSet<>(); //check to see if a word has already been outputted
		HashMap<String, Integer> data = new HashMap<>(); //stores all lines with count
		
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			
			if (!data.containsKey(line)) {
				data.put(line, 1);
			}
			else{
				Integer count = data.get(line) + 1;
				data.remove(line);
				data.put(line,count);
				
				
				if (data.get(line) > 3) {
					//s.add(line);
					w.println(line);
				}
			} 
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
