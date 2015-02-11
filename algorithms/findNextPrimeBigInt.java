import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigInteger;
import java.lang.Math;

public class findNextPrimeBigInt {
	
	// it's not in standard java... so let's write it ourselves LOL
	public static BigInteger sqrt(BigInteger x) {
		BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
		BigInteger div2 = div;
		// Loop until we hit the same value twice in a row, or wind up alternating.
		for(;;) {
			BigInteger y = div.add(x.divide(div)).shiftRight(1);
			if (y.equals(div) || y.equals(div2))
				return y;
			div2 = div;
			div = y;
		}
	}
	
	public static BigInteger findNext(BigInteger n) {
		// finds next prime based on current number
		if (n.compareTo(BigInteger.valueOf(2)) == -1) return BigInteger.valueOf(2); // base case
		if (n.compareTo(BigInteger.valueOf(2)) == 0) return BigInteger.valueOf(3);
		if (n.compareTo(BigInteger.valueOf(5)) == -1) return BigInteger.valueOf(5);
		if (n.compareTo(BigInteger.valueOf(7)) == -1) return BigInteger.valueOf(7);
		boolean isPrime = false;
		BigInteger i = n;
		while (!isPrime) {
			// System.out.println(i.mod(BigInteger.valueOf(2)));
			i=i.add(BigInteger.valueOf(1));
			if (i.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 1) { // no even numbers!
				// check every number below, as long as it’s pretty low
				for (BigInteger a=sqrt(i.add(BigInteger.valueOf(1))); a.compareTo(BigInteger.valueOf(2)) == 1; a=a.subtract(BigInteger.valueOf(1))) {
					// Debug 
					//System.out.println("a="+a);
					if (i.mod(a).compareTo(BigInteger.valueOf(0)) == 0) {
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
		System.out.print("Enter a BigInteger to find the next prime: ");
		String text = in.readLine();
		BigInteger x = new BigInteger(text);
		System.out.println("Next Prime: "+findNext(x));
	}

}
