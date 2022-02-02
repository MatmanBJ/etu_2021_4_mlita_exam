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
			System.out.print(i + ": (" + localDisjunct.GetParents()[0] + ", " + localDisjunct.GetParents()[1] + ") ");
			if (localDisjunct.GetOne() == false && localDisjunct.GetEmpty() == false)
			{
				int j = 1;
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
					System.out.print(inverse + "\u0000" + var.GetName());
					if (j < localDisjunct.GetVariables().size())
					{
						System.out.print(" v ");
					}
					j = j + 1;
				}
			}
			else if (localDisjunct.GetOne() == true)
			{
				System.out.print("1 (~ true)");
			}
			else if (localDisjunct.GetEmpty() == true)
			{
				System.out.print("□ (~ false)");
			}
			i = i + 1;
			System.out.print("\n");
		}
	}
	
	public ResolutionFunction Resolution (String regime)
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		boolean repeat = true;
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
							int [] localParents = {i + 1, j + 1};
							localNewDisjunct.SetParents(localParents);
							
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
									else if ((localNewDisjunct.GetVariables().get(m).GetVar() == 
											localNewDisjunct.GetVariables().get(n).GetVar()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() != 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
										localNewDisjunct.SetOne(true);
									}
								}
							}
							if (localNewDisjunct.GetOne() == false)
							{
								if (localNewDisjunct.GetVariables().size() == 0)
								{
									localNewDisjunct.SetEmpty(true);
									/*if (regime.equals("find"))
									{
										repeat = false;
										i = disjuncts.size() - 1;
										j = disjuncts.size();
										k = variables.size();
										l = variablesTwo.size();
										localF.SetDisjuncts(localNewDisjunct);
										disjuncts.addAll(localF.GetDisjuncts());
									}*/
								}
							}
							localF.SetDisjuncts(localNewDisjunct);
						}
					}
				}
			}
		}
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			disjuncts.addAll(localF.GetDisjuncts());
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
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
								int [] localParents = {i + 1, j + 1};
								localNewDisjunct.SetParents(localParents);
								
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
										else if ((localNewDisjunct.GetVariables().get(m).GetVar() == 
												localNewDisjunct.GetVariables().get(n).GetVar()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() != 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
											localNewDisjunct.SetOne(true);
										}
									}
								}
								if (localNewDisjunct.GetOne() == false)
								{
									if (localNewDisjunct.GetVariables().size() == 0)
									{
										localNewDisjunct.SetEmpty(true);
									}
								}
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
		}
		return localF;
	}
}
