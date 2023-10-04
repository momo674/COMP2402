package comp2402a1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class Part10 {

	/**
	 * Your code goes here - see Part0 for an example
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		LinkedList<String> biggest_lines = new LinkedList<>();
		ArrayList<String> input = new ArrayList<>();
		String biggest = "";
		
		//int index = 0;
		//int index_out = 0;


		for (String line = r.readLine(); line != null; line = r.readLine()) {
			biggest_lines.add(line);
			System.out.println(biggest_lines);
			if (biggest_lines.isEmpty()) { //if our biggest line list is empty
				biggest = line;
				biggest_lines.add(biggest);
				
			}
			else if (line.compareTo(biggest) > 0) { //if our current line is the biggest line seen
				biggest_lines.clear();
				biggest = line;
				biggest_lines.add(biggest);
			
			}

			else { //compare this line to every single line, if it is bigger bigger than any line, clear the list from that line till the end.
				for (String x: biggest_lines) {
					if (line.compareTo(x) > 0) {
						LinkedList<String> temp = new LinkedList<>();
						
						for (String y: biggest_lines) {
							if (y.equals(x)) {
								break;
							}
							temp.add(y);
						}
						temp.add(line);
						biggest_lines = temp;
						break;
						
						
					}
					
					
				}
				
				//
				
			}
		}
		for (String out: biggest_lines) {
			w.println(out);
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
