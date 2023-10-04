package comp2402a1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.Arrays;
import java.util.LinkedList;

public class Part7 {
	
	/**
	 * Your code goes here - see Part0 for an example
	 * @param r the reader to read from
	 * @param w the writer to write to
	 * @throws IOException
	 */
	public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
		int index = 0;
		int block_size = 1;
		String reset = "***reset***";


		String[] buffer = new String[block_size];
		Stack<String[]> output = new Stack<>();
		
		

	
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			
			if (!line.equals(reset)) {
				
				try{
					buffer[index] = line;
					index++;
				}

				catch (ArrayIndexOutOfBoundsException e) {
					block_size++;
					index = 1;
					output.push(buffer);
					buffer = new String[block_size];
					buffer[0] = line;

				}





			}


			else {

				try{
					buffer[index] = line;
					output.push(buffer);
					index = 0;
					block_size = 1;
					buffer = new String[block_size];
				}

				catch (ArrayIndexOutOfBoundsException e) {
					block_size++;
					index = 0;
					output.push(buffer);
					buffer = new String[block_size];
					buffer[index] = reset;
					output.push(buffer);
					block_size = 1;
					buffer = new String[block_size];



				}


			}
			
			

			
			



			
		}
		output.push(buffer);
		
		while(!output.isEmpty()) {
			String[] temp = output.pop();

			for (int i = 0; i < temp.length; i++) {
				
				if (temp[i] != null) {
					w.println(temp[i]);
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