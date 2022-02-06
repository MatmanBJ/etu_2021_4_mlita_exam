// Alternative exam -- 4 term
// Matthew Sobolewski and Victor Stepovik
// Resolution method version alpha 0.07
// Updates:
// -- File output added

package resolution;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class ResolutionMethod
{
	public static void ExampleOne() // old and unupdated example
	{
		/*ResolutionVar a = new ResolutionVar('a');
		ResolutionVar b = new ResolutionVar('b');
		
		ResolutionVariable yesa = new ResolutionVariable();
		ResolutionVariable yesb = new ResolutionVariable();
		ResolutionVariable noa = new ResolutionVariable();
		ResolutionVariable nob = new ResolutionVariable();
		
		ResolutionDisjunct one = new ResolutionDisjunct();
		ResolutionDisjunct two = new ResolutionDisjunct();
		
		ResolutionFunction f = new ResolutionFunction();
		ResolutionFunction F;
		
		yesa.SetDenial(true);
		yesb.SetDenial(true);
		noa.SetDenial(false);
		nob.SetDenial(false);
		yesa.SetVar(a);
		yesb.SetVar(b);
		noa.SetVar(a);
		nob.SetVar(b);
		
		one.SetVariables(yesa);
		one.SetVariables(yesb);
		two.SetVariables(noa);
		two.SetVariables(nob);
		
		f.SetDisjuncts(one);
		f.SetDisjuncts(two);
		
		//f.GetFunction();
		//F = f.Resolution();
		//F.GetFunction();
		f.GetFunction();*/
		
		// new example
		
		/*ResolutionVar x = new ResolutionVar ('x');
		ResolutionVar y = new ResolutionVar ('y');
		ResolutionVar z = new ResolutionVar ('z');
		ResolutionVar t = new ResolutionVar ('t');
		ResolutionVar s = new ResolutionVar ('s');*/
	}
	public static void ExampleTwo()
	{
		ArrayList<ResolutionVariable> variables = new ArrayList<ResolutionVariable>();
		
		variables.add(new ResolutionVariable(false, 'x'));
		variables.add(new ResolutionVariable(false, 'y'));
		variables.add(new ResolutionVariable(false, 'z'));
		ResolutionDisjunct d_one = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(false, 'x'));
		variables.add(new ResolutionVariable(false, 't'));
		variables.add(new ResolutionVariable(true, 's'));
		ResolutionDisjunct d_two = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(false, 'x'));
		variables.add(new ResolutionVariable(false, 's'));
		ResolutionDisjunct d_three = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(false, 'x'));
		variables.add(new ResolutionVariable(true, 'y'));
		variables.add(new ResolutionVariable(true, 't'));
		ResolutionDisjunct d_four = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(true, 'x'));
		ResolutionDisjunct d_five = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(true, 'z'));
		ResolutionDisjunct d_six = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		ResolutionFunction d_func = new ResolutionFunction();
		d_func.SetDisjuncts(d_one);
		d_func.SetDisjuncts(d_two);
		d_func.SetDisjuncts(d_three);
		d_func.SetDisjuncts(d_four);
		d_func.SetDisjuncts(d_five);
		d_func.SetDisjuncts(d_six);
		
		d_func.ResolutionHash();
		//d_func.ResolutionFind();
		//d_func.Resolution();
		d_func.GetFunction();
		//d_func_return.GetFunction();
	}
	public static void InputConsole()
	{
		ResolutionFunction cons = new ResolutionFunction("INPUT");
		cons.ResolutionHash();
		cons.GetFunction();
		cons.ResolutionFileExample();
		cons.ResolutionFileReadeble();
	}
	public static void main(String[] args)
	{
		ExampleTwo();
		
		Scanner inputScanner = new Scanner(System.in);
		int key = 1;
		
		while (key != 0)
		{
			InputConsole();
			System.out.println("Do you want to input something else? Press 0 to NO, press else to YES:");
			key = inputScanner.nextInt();
		}
		
		inputScanner.close(); // be careful: it closes I/O system, so if you do it once, you can not use I/O again!
		System.out.println("---------- Bye!!! ----------");
	}
}
