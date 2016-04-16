/*
* Given operators [*, /, -, +] and numbers, find if target can be achieved by applying operations on numbers. 
* Also print the expression with brackets denoting precedence for obtaining target.
@author: Nitesh Thali
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FindExpression {
	private static final String [] OPERATORS = new String[] {"+", "-", "*", "/"};
	private static int count = 0;
	private static float target = (float) 0.0;
	public String applyOperator(String b, String a, String operator)
	{
		Float result = (float) 0;
		try{
			
		if(operator == "+")
			result =  (float) Float.parseFloat(a) + Float.parseFloat(b);
			
		if(operator == "*")
			result =  (Float.parseFloat(a) * Float.parseFloat(b));

		if(operator == "-")
			result =  (Float.parseFloat(a) - Float.parseFloat(b));
		
		if(operator == "/")
			result =  (Float.parseFloat(a) / Float.parseFloat(b));
		
		}catch(ArithmeticException e){
			System.out.println("Arithmetic Expression Error" + e.getMessage());	
		}
		//Integer abc = result.intValue();
		return result.toString();
	}
	public boolean evaluateExpr(List<String> s)
	{
		boolean match = false;
		Stack <String> st = new Stack<String>();
		for(int i=0; i<s.size(); i++){
			match = false;
			for(int j=0; j<OPERATORS.length; j++){
				if(s.get(i) == OPERATORS[j]){
					st.push(applyOperator(st.pop(), st.pop(), OPERATORS[j]));
					match = true;
					break;
				}
			}
			if(!match)
				st.push(s.get(i));
		}
		if(Float.parseFloat(st.pop()) == target)
			return true;
		return false;
	}
	public void printExpression(List<String> s)
	{
		boolean match = false;
		Stack <String> st = new Stack<String>();
		for(int i=0; i<s.size(); i++){
			match = false;
			for(int j=0; j<OPERATORS.length; j++){
				if(s.get(i) == OPERATORS[j]){
					String s_new = "";
					String b = st.pop();
					try{
						Integer bn =(int) Float.parseFloat(b);
						s_new += bn.toString();
					}catch(Exception e){
						s_new += b;
					}
					String a = st.pop();
					try{
						Integer an =(int) Float.parseFloat(a);
						s_new ="(" +  an.toString() + OPERATORS[j] + s_new + ")";
					}catch(Exception e){
						
						s_new = "(" + a + OPERATORS[j] + s_new + ")";
					}
					
					st.push(s_new);
					match = true;
					break;
				}
			}
			if(!match)
				st.push(s.get(i));
		}
		System.out.println("Expression: " + st.pop());
	}
    public boolean postFix(List<String> x, List<String> s, int expr_length, int index, int opr, int c, boolean track[])
    {
    	
    	if(s.size() == expr_length){
    		//System.out.println(s);
    		count++;
    		if(evaluateExpr(s)){
    			printExpression(s);
    			return true;
    		};
    		return false;
    	}
    	else{
    		if(opr < expr_length - c){
    			for(int j = 0; j<x.size(); j++){
    				if(!track[j]){
    					s.add(x.get(j));
    					track[j] = true;
    					if (postFix(x, s, expr_length, index + 1, opr+1, c, track)) return true;
    					s.remove(index);
    					track[j] = false;
    				}
    			}
    		}
    		if (2*opr - s.size() > 1){
    			for (int i=0; i<OPERATORS.length; i++){
    				s.add(OPERATORS[i]);
    				if(postFix(x, s, expr_length, index + 1, opr, c, track)) return true;
    				s.remove(index);
    			}	
    		}		    			
    	}
		return false;
    }
	public static void main(String[] args) {
		target = (float) Integer.parseInt(args[0]); 
		List<String> x = new ArrayList<String>();
	    for (int i = 1; i< args.length; i++)
	    	x.add(args[i] + ".0");
	    boolean track[] = new boolean[x.size()];
	    for (int i=0; i< track.length; i++)
	        track[i] = false;
	    List <String>  s = new ArrayList<String>();
	    int index = 0;
	    FindExpression pf = new FindExpression();
	    boolean result = pf.postFix(x, s, 2 * x.size() -1, index, 0, x.size()-1, track);
	    if (!result)
	    	System.out.println("none");
	    //System.out.println("Total: " + count + " " + result);
	}

}
