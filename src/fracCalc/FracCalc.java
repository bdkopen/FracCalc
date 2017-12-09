package fracCalc;
import java.util.Arrays;
import java.util.Scanner;

public class FracCalc {

    public static void main(String[] args)  {
        // TODO: Read the input from the user and call produceAnswer with an equation
    	//Scanner setup
    		Scanner sc = new Scanner(System.in);
    		String input = sc.nextLine();
    		//Run till quit is typed
    		while(!input.equals("quit")) {
    			//run through produceAnswer
    			produceAnswer(input);
    			input = sc.next();
    		}
    }
    
    // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
    // This function takes a String 'input' and produces the result
    //
    // input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
    //      e.g. input ==> "1/2 + 3/4"
    //        
    // The function should return the result of the fraction after it has been calculated
    //      e.g. return ==> "1_1/4"
    public static String produceAnswer(String input) { 
        // TODO: Implement this function to produce the solution to the input
        return parseTerm(input.split(" "));
    }

    // TODO: Fill in the space below with any helper methods that you think you will need
    
    //Parse term, then cross multiply and add fractions together.
    public static String parseTerm(String[] term) {
    		//Define the whole number, numerator, and denominator.
    		int[] whole = new int[2];
    		int[] numerator = new int[2];
    		int[] denominator = new int[2];
    		
    		for(int i = 0; i <=3; i+=2) {
    			//Used to set specific array values.
    			int temp = i/2;
    			
    			/* WHOLE NUMBER */
    			//If there is a _
    			if(term[i].indexOf("_") != -1) {
    				//Then check if there is a fraction
    				if(term[i].indexOf("/") == -1) {
    					whole[temp] = Integer.parseInt(term[i]);
    				//If there is no fraction, set to string.
    				} else {
    					whole[temp] = Integer.parseInt(term[i].split("_")[0]);
    				}
    			//If no _, check to see if there is no whole number
    			} else if(term[i].indexOf("/") != -1) {
    				whole[temp] = 0;
				//Otherwise, it is just a whole number with no fraction.
				} else {
					whole[temp] = Integer.parseInt(term[i].split("_")[0]);
				}
    			
    			/*GET NUMERATOR & denominator*/
    			//Check to see if it has a slash
    			if(term[i].indexOf("/") != -1) {
    				//Check to see if it has a whole number
    				if(term[i].indexOf("_") == -1) {
    					numerator[temp] = Integer.parseInt(term[i].split("/")[0]);
        				denominator[temp] = Integer.parseInt(term[i].split("/")[1]);
        			//If no whole number, set numerator and denominator.
    				} else {
    					numerator[temp] = Integer.parseInt(term[i].split("_")[1].split("/")[0]);
        				denominator[temp] = Integer.parseInt(term[i].split("_")[1].split("/")[1]);
    				}
    			//If no fraction, set numerator and denominator
    			} else {
    				numerator[temp] = 0;
    				denominator[temp] = 1;
    			}
    			
    		}

    		String operator = term[1];
    		
    		//return a fraction that is simplified
    		return simplifyFraction(operator, whole, numerator, denominator);
    		//return "whole:"+whole[1]+" numerator:"+numerator[1]+" denominator:"+denominator[1];
    }
    
    public static String simplifyFraction(String operator, int[] whole, int[] numerator, int[] denominator) {
    	//Setup two arrays for changed numerators and denominators.
    	int[] numeratorChanged = new int[2];
		int[] denominatorChanged = new int[2];
		String solvedFraction = "";
		
		//Put whole numbers in numerators
		for(int i = 0; i < 2; i++) {
			if(whole[i] >= 0) {
				numeratorChanged[i] = numerator[i]+whole[i]*denominator[i];
			} else {
				numeratorChanged[i] = -numerator[i]+whole[i]*denominator[i];
			}
		}
		//Cross multiply
		denominatorChanged[0] = denominator[0] * denominator[1];
		numeratorChanged[0] = numeratorChanged[0] * denominator[1];
		denominatorChanged[1] = denominator[0] * denominator[1];
		numeratorChanged[1] = numeratorChanged[1] * denominator[0];
		
		//Find the operators
		if(operator.equals("+")) {
			solvedFraction = (numeratorChanged[0] + numeratorChanged[1]) + "/" + denominatorChanged[0];
			//System.out.println(solvedFraction);
		} else if(operator.equals("-")) {
			solvedFraction = (numeratorChanged[0] - numeratorChanged[1]) + "/" + denominatorChanged[0];
			//System.out.println(solvedFraction);
		} else if(operator.equals("/")) {
			solvedFraction = (numeratorChanged[0] * denominatorChanged[1]) + "/" + (denominatorChanged[0] * numeratorChanged[1]);
		} else if(operator.equals("*")) {
			solvedFraction = (numeratorChanged[0] * numeratorChanged[1]) + "/" + (denominatorChanged[0] * denominatorChanged[1]);
		}
		//Print for now
		//System.out.println(Arrays.toString(numerator) +Arrays.toString(denominator));
		//Simplify
		return simplyFrac(solvedFraction);
	}
    
    public static String simplyFrac(String fraction) {
    	
	    	int numerator;
	    	//If there is a whole number, get numerators
	    	if(fraction.contains("_")) {
	    		numerator = Integer.parseInt(fraction.split("/")[0].split("_")[1]);
	    	//If there isn't a whole number, get numerators
	    	} else {
	    		numerator = Integer.parseInt(fraction.split("/")[0]);
	    	}
	    	
	    	//Get denominator
	    	int denominator = Integer.parseInt(fraction.split("/")[1]);
	    	
	    	//Get whole number
	    	int whole = numerator / denominator;
	    	
	    	//Get numerator remaining after whole numbers are removed.
	    	numerator = numerator % denominator;
	    	
	    	//If denominator is negative, make numerator negative and denominator positive.
	    	if(denominator < 0) {
	    		numerator *= -1;
	    		denominator *= -1;
	    	}
	    	
	    	boolean simplify = true;
	    	//If you can simplify fraction
	    	while(simplify) {
	    		//Highest prime possible number to find divisors.
	    		for(int i = 119; i >= 1; i--) {
	    			//If it reaches 1, end while loop.
	    			if(i == 1) {
	    				simplify = false;
	    			}
	    			//Check to see if divisible by the iterator
	    			if(numerator % i == 0 && denominator % i == 0 && i > 1) {
	    				numerator = numerator / i;
	    				denominator = denominator / i;
	    				//Reset for loop to check if it can be simplified down again.
	    				i = 119;
	    			}
	    		}
	    	}
	    	
	    	//Remove unneeded negative signs
	    	denominator = Math.abs(denominator);
	    	if(whole != 0) {
	    		numerator = Math.abs(numerator);
	    	}
	    	
	    	//return statement
	    	//If whole, numerator, and denominator exists
	    	if(whole != 0 && numerator != 0 && denominator > 0) {
	    		System.out.println(whole + "_" + numerator + "/" + denominator);
			return whole + "_" + numerator + "/" + denominator;
		//If whole doesn't exist but others do
	    	} else if(whole == 0 && numerator != 0 && denominator > 0) {
	    		System.out.println(numerator + "/" + denominator);
	    		return numerator + "/" + denominator;
	    	//If fraction doesn't exist, just print whole.
	    	} else if(numerator == 0) {
	    		System.out.println(whole);
	    		return whole + "";
	    	} else {
	    		System.out.println(whole + "_" + numerator + "/" + denominator);
	    		return "";
	    	}
	    	
	    /*	
		// TODO:  Negatives all messed up
		if(whole != 0 && numerator == 0) {
			System.out.println(whole);
			return whole + "";
		} else if(whole != 0) {
			System.out.println(whole + "_" + numerator + "/" + denominator);
			return whole + "_" + Math.abs(numerator) + "/" + Math.abs(denominator);
		} else {
			System.out.println(numerator + "/" + denominator);
			return numerator + "/" + denominator;
		}*/
    }
}
