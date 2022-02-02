package resolution;

public class ResolutionMethod
{
	public static void main(String[] args)
	{
		ResolutionVar a = new ResolutionVar('a');
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
		
		f.GetFunction();
		F = f.Resolution();
		F.GetFunction();
	}
}
