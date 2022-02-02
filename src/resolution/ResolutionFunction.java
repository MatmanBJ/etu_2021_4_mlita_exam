package resolution;

import java.util.ArrayList;

public class ResolutionFunction
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private ArrayList<ResolutionDisjunct> disjuncts = new ArrayList<ResolutionDisjunct>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- SETTERS ----------
	
	public void SetDisjuncts (ResolutionDisjunct localDisjuncts)
	{
		disjuncts.add(localDisjuncts);
	}
	
	// ---------- GETTERS ----------
	
	public ArrayList<ResolutionDisjunct> GetDisjuncts ()
	{
		return disjuncts;
	}
	
	// ---------- OTHERS ----------
	
	public void GetFunction ()
	{
		int i = 1;
		for (ResolutionDisjunct localDisjunct : disjuncts)
		{
			ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
			System.out.print(i + ": ");
			for (ResolutionVariable localVariable : variables)
			{
				ResolutionVar var = localVariable.GetVar();
				char inverse;
				if (localVariable.GetDenial() == true)
				{
					inverse = '\u0000';
				}
				else
				{
					inverse = '!';
				}
				System.out.print(inverse + "\u0000" + var.GetName() + " v ");
			}
			i = i + 1;
			System.out.print("\n");
		}
	}
	
	public ResolutionFunction Resolution ()
	{
		int i;
		int j;
		int k;
		int l;
		boolean repeat;
		ResolutionFunction localF = new ResolutionFunction();
		for (i = 0; i < disjuncts.size() - 1; i++)
		{
			ResolutionDisjunct localDisjunct = disjuncts.get(i);
			ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
			for (j = i + 1; j < disjuncts.size(); j++)
			{
				ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
				ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
				for (k = 0; k < variables.size(); k++)
				{
					ResolutionVariable localVariable = variables.get(k);
					ResolutionVar var = localVariable.GetVar();
					for (l = 0; l < variablesTwo.size(); l++)
					{
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						ResolutionVar varTwo = localVariableTwo.GetVar();
						if ((var == varTwo) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
						{
							ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
							//ArrayList<ResolutionVariable> z;
							
							localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
							ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
							localNewDisjunct.GetVariables().remove(x);
							
							localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
							ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
							localNewDisjunct.GetVariables().remove(y);
							
							for (int m = 0; m < localNewDisjunct.GetVariables().size() - 1; m++)
							{
								for (int n = m + 1; n < localNewDisjunct.GetVariables().size(); n++)
								{
									if ((localNewDisjunct.GetVariables().get(m).GetVar() == 
											localNewDisjunct.GetVariables().get(n).GetVar()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() == 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().remove(n);
									}
									/*else if ((localNewDisjunct.GetVariables().get(m).GetVar() == 
											localNewDisjunct.GetVariables().get(n).GetVar()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() != 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
									}*/
								}
							}
							localF.SetDisjuncts(localNewDisjunct);
						}
					}
				}
			}
			/*System.out.print(i + ": ");
			for (ResolutionVariable localVariable : variables)
			{
				ResolutionVar var = localVariable.GetVar();
				char inverse;
				if (localVariable.GetDenial() == true)
				{
					inverse = '\u0000';
				}
				else
				{
					inverse = '!';
				}
				System.out.print(inverse + "\u0000" + var.GetName() + " + ");
			}
			System.out.print("\n");*/
		}
		return localF;
	}
}
