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

public class lightbulbs {
	public static void main(String [] args) throws IOException {
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		//PrintWriter out = new PrintWriter(new FileWriter("primes.txt")); 
		BufferedReader in = new BufferedReader(new FileReader("primesMain.txt"));
        String s = "";
        Boolean[] lights = new Boolean[10000000];
        Arrays.fill(lights, Boolean.TRUE);
        int i, mult;
        // As long as we don't have EOF -- if we checked in.readLine(), it would stop at newline
        // That bug held me back for SO LONG.
        while (in.ready()) {
			s = in.readLine(); // read in the prime from file
			// System.out.println(check);
			// flip all the switches from S --> 10M
			int x = Integer.parseInt(s);
			i = 1;
			mult = x*i;
			while (mult < 10000000) {
				i++;
				lights[mult] = !lights[mult];
				System.out.println("Flicked light switch " + mult + " to " + lights[mult]);
				mult = x*i;
			}
        }
        int count = 0;
        for (boolean j: lights) {
			if (j) { // light is on
				count++;
			}
		}
		System.out.println(count + " light switches are on.");
	}

}
