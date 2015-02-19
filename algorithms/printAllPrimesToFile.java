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
import java.util.HashSet;

public class printAllPrimesToFile {
	static HashSet<Long> primes = new HashSet<Long>();
    static boolean isPrime(long n) {
		if (n < 2L) {
			return false;
		}
		for (long k : primes) {
			if (n % k == 0L) {
				return false;
			}
		}
		return true;
    }

	public static void main(String [] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		PrintWriter out = new PrintWriter(new FileWriter("primes.txt")); 
		System.out.print("Enter a number (less than 2^64) to print all primes up to: ");
		String text = in.readLine();
		long x = Integer.parseInt(text);
		if (x > 3L) {
			primes.add(2L);
			out.println(2L);
			System.out.println(2L);
			for (long k = 3; k < x; k++) {
				if (isPrime(k)) {
					primes.add(k);
					System.out.println(k);
					out.println(k);
				}
			}
		} else if (x > 0L) {
			System.out.println("There's nothing really there...");
			if (x >= 2L) primes.add(2L);
			if (x >= 3L) primes.add(3L);
		} else {
			System.out.println("No prime numbers with upper maximum of " + x);
		}
		if (out != null) {
			out.close();
		}
	}

}
