package resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ResolutionFunction
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private ArrayList<ResolutionDisjunct> disjuncts = new ArrayList<ResolutionDisjunct>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTORS ----------
	
	public ResolutionFunction ()
	{}
	
	public ResolutionFunction (String localJust)
	{
		int stop = 1;
		Scanner localInputScanner = new Scanner(System.in);
		System.out.println("How many disjuncts do you want?");
		String [] localInputString = new String[localInputScanner.nextInt()];
		localInputScanner.nextLine();
		for (int i = 0; i < localInputString.length; i++)
		{
			ArrayList<ResolutionVariable> localInputVariables = new ArrayList<ResolutionVariable>();
			localInputString[i] = localInputScanner.nextLine();
			//System.out.println(localInputString[i]);
			
			//String local = localInputScanner.nextLine();
			//localInputScanner.nextLine();
			//System.out.println(local.length());
			
			for (int j = 0; j < localInputString[i].length(); j++)
			{
				if (localInputString[i].charAt(j) != ' ' && localInputString[i].charAt(j) != '+' && localInputString[i].charAt(j) != 'v')
				{
					if (localInputString[i].charAt(j) == '!')
					{
						localInputVariables.add(new ResolutionVariable(false, new ResolutionVar (localInputString[i].charAt(j + 1))));
						j = j + 1;
					}
					else
					{
						localInputVariables.add(new ResolutionVariable(true, new ResolutionVar (localInputString[i].charAt(j))));
					}
				}
			}
			ResolutionDisjunct localInputDisjunct = new ResolutionDisjunct(localInputVariables);
			disjuncts.add(localInputDisjunct);
			this.GetOneDisjunct();
			
			//System.out.println("New disjunct? 0 -- no, 1 -- yes:");

		}
		localInputScanner.close();
	}
	
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
	
	public void GetOneDisjunct () // for console-constructor
	{
		ResolutionDisjunct localDisjunct = disjuncts.get(disjuncts.size() - 1);
		ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
		System.out.print("Input: ");
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
		System.out.print("\n");
	}
	
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
	
	public ResolutionFunction Resolution ()
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
						if ((var.GetName() == varTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
									if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
											localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() == 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().remove(n);
									}
									else if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
											localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() != 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
										localNewDisjunct.SetOne(true);
									}
								}
							}
							if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
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
							if ((var.GetName() == varTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
										if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
												localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() == 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().remove(n);
										}
										else if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
												localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() != 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
											localNewDisjunct.SetOne(true);
										}
									}
								}
								if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
								{
									localNewDisjunct.SetEmpty(true);
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
	
	public void ResolutionFind ()
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
			if (repeat == false)
			{
				break;
			}
			ResolutionDisjunct localDisjunct = disjuncts.get(i);
			ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
			for (j = i + 1; j < disjuncts.size(); j++)
			{
				if (repeat == false)
				{
					break;
				}
				ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
				ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
				for (k = 0; k < variables.size(); k++)
				{
					if (repeat == false)
					{
						break;
					}
					ResolutionVariable localVariable = variables.get(k);
					ResolutionVar var = localVariable.GetVar();
					for (l = 0; l < variablesTwo.size(); l++)
					{
						if (repeat == false)
						{
							break;
						}
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						ResolutionVar varTwo = localVariableTwo.GetVar();
						if ((var.GetName() == varTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
									if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
											localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() == 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().remove(n);
									}
									else if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
											localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() != 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
										localNewDisjunct.SetOne(true);
									}
								}
							}
							if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
							{
								localNewDisjunct.SetEmpty(true);
								repeat = false;
							}
							localF.SetDisjuncts(localNewDisjunct);
						}
					}
				}
			}
		}
		if (repeat == false)
		{
			disjuncts.addAll(localF.GetDisjuncts());
			System.out.println("The □ has been found (DONE)!!!");
		}
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			disjuncts.addAll(localF.GetDisjuncts());
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				if (repeat == false)
				{
					break;
				}
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
					if (repeat == false)
					{
						break;
					}
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						if (repeat == false)
						{
							break;
						}
						ResolutionVariable localVariable = variables.get(k);
						ResolutionVar var = localVariable.GetVar();
						for (l = 0; l < variablesTwo.size(); l++)
						{
							if (repeat == false)
							{
								break;
							}
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							ResolutionVar varTwo = localVariableTwo.GetVar();
							if ((var.GetName() == varTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
										if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
												localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() == 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().remove(n);
										}
										else if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
												localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() != 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
											localNewDisjunct.SetOne(true);
										}
									}
								}
								if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
								{
									localNewDisjunct.SetEmpty(true);
									repeat = false;
								}
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if (repeat == false)
			{
				disjuncts.addAll(localFR.GetDisjuncts());
				System.out.println("The □ has been found (DONE)!!!");
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
				System.out.println("The □ hasn't been found (NOT DONE)!!!");
			}
			localF = localFR;
		}
	}
	
	public void ResolutionHash ()
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
						if ((var.GetName() == varTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
									if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
											localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() == 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().remove(n);
									}
									else if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
											localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
											(localNewDisjunct.GetVariables().get(m).GetDenial() != 
											localNewDisjunct.GetVariables().get(n).GetDenial()))
									{
										localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
										localNewDisjunct.SetOne(true);
									}
								}
							}
							if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
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
							localF.SetDisjuncts(localNewDisjunct);
						}
					}
				}
			}
		}
		while (repeat == true)
		{
			/*for (i = 0; i < disjuncts.size() - 1; i++)
			{
				for (j = i + 1; j < disjuncts.size(); j++)
				{
					if (disjuncts.get(i).GetVariables().hashCode() == disjuncts.get(j).GetVariables().hashCode() ||
							(disjuncts.get(i).GetVariables().size() == 0 &&
							disjuncts.get(j).GetVariables().size() == 0))
					{
						disjuncts.remove(j);
					}
				}
			}
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				for (j = i + 1; j < disjuncts.size(); j++)
				{
					if (disjuncts.get(i).GetVariables().hashCode() == disjuncts.get(j).GetVariables().hashCode())
					{
						disjuncts.remove(j);
						j = j - 1;
					}
				}
			}*/
			for (i = 0; i < disjuncts.size(); i++)
			{
				for (j = 0; j < localF.GetDisjuncts().size(); j++)
				{
					if (disjuncts.get(i).GetVariables().hashCode() ==
							localF.GetDisjuncts().get(j).GetVariables().hashCode())
					{
						localF.GetDisjuncts().remove(j);
						j = j - 1;
					}
				}
			}
			for (i = 0; i < localF.GetDisjuncts().size() - 1; i++)
			{
				for (j = i + 1; j < localF.GetDisjuncts().size(); j++)
				{
					if (localF.GetDisjuncts().get(i).GetVariables().hashCode() ==
							localF.GetDisjuncts().get(j).GetVariables().hashCode())
					{
						localF.GetDisjuncts().remove(j);
						j = j - 1;
					}
				}
			}
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
							if ((var.GetName() == varTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
										if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
												localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() == 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().remove(n);
										}
										else if ((localNewDisjunct.GetVariables().get(m).GetVar().GetName() == 
												localNewDisjunct.GetVariables().get(n).GetVar().GetName()) && 
												(localNewDisjunct.GetVariables().get(m).GetDenial() != 
												localNewDisjunct.GetVariables().get(n).GetDenial()))
										{
											localNewDisjunct.GetVariables().removeAll(localNewDisjunct.GetVariables());
											localNewDisjunct.SetOne(true);
										}
									}
								}
								if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
								{
									localNewDisjunct.SetEmpty(true);
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
		/*for (i = 0; i < disjuncts.size() - 1; i++)
		{
			for (j = i + 1; j < disjuncts.size(); j++)
			{
				if (disjuncts.get(i).GetVariables().hashCode() == disjuncts.get(j).GetVariables().hashCode())
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}*/
	}
}
