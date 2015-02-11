import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class findNextPrime {
	public static int findNext(int n) {
		// finds next prime based on current number
		if (n < 2) return 2; // base case
		if (n == 2) return 3;
		if (n < 5) return 5;
		if (n < 7) return 7;
		boolean isPrime = false;
		int i = n;
		while (!isPrime) {
			i++;
			if (i%2 != 0) { // no even numbers!
				// check every number below, as long as it’s pretty low
				for (int a=(i/2)-1; a>2; a--) {
					if (i % a == 0) {
						isPrime = false;
						break;
					}
				isPrime = true;
				}
			}
		}
		return i;
	}
	
	public static void main(String [] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		String text = in.readLine();
		int x = Integer.parseInt(text);
		System.out.println(findNext(x));
	}

}
