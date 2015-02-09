import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Stack;

public class brackets {
    public static boolean isMatched(String s) {
		Stack<Character> stack = new Stack<Character>();
		
		char c;
		// Straightforward solution: Push the bracket type to a stack, then peek to compare.
		for (int i=0; i<s.length(); i+=1) {
			c = s.charAt(i);
			// Simple push.
			if(c == '(') {
				stack.push(c);
			} else if(c == '{') {
				stack.push(c);
			} else if(c == '[') {
				stack.push(c);
			// Closing brackets is where things get interesting	
			} else if(c == ')') {
				if(stack.empty()) {
					// If it's empty, we know something's wrong.
					return false;
				} else if(stack.peek() == '(') {
					// This is the only "correct" path.
					stack.pop();
				} else {
					// If the stack is anything else, we have a mismatched order.
					return false;
				}
			} else if(c == '}') { // Comments any further are extraneous, same as above.
				if(stack.empty()) {
					return false;
				} else if(stack.peek() == '{') {
					stack.pop();
				} else {
					return false;
				}
			} else if(c == ']') {
				if(stack.empty()) {
					return false;
				} else if(stack.peek() == '[') {
					stack.pop();
				} else {
					return false;
				}
			}
		}
        return stack.empty();
    }

    public static void main(String [] args) throws IOException {
        /* Test input for Part 1 */
        /*
        System.out.println(isMatched("()[]{}"));
        System.out.println(isMatched("([{}])"));
        System.out.println(isMatched("([{)]}"));
        */
        
        // BufferedReaders are better than StreamReaders. I read it on the internet!
        BufferedReader in = new BufferedReader(new FileReader("brackets.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("brackets-matched.txt"));
        String s = "";
        // As long as we don't have EOF -- if we checked in.readLine(), it would stop at newline
        // That bug held me back for SO LONG.
        while (in.ready()) {
			s = in.readLine();
			boolean check = isMatched(s);
			// System.out.println(check);
			if (check == true) {
				out.println(s);
				// System.out.println(s);
			}
        }
	}
}
