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
		System.out.println("---------- CONSOLE INPUT ----------");
		System.out.println("Repeated and same disjuncts will be deleted!");
		System.out.println("How many disjuncts do you want?");
		String [] localInputString = new String[localInputScanner.nextInt()];
		localInputScanner.nextLine();
		System.out.println("Input them:");
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
						localInputVariables.add(new ResolutionVariable(false, localInputString[i].charAt(j + 1)));
						j = j + 1;
					}
					else
					{
						localInputVariables.add(new ResolutionVariable(true, localInputString[i].charAt(j)));
					}
				}
			}
			ResolutionDisjunct localInputDisjunct = new ResolutionDisjunct(localInputVariables);
			disjuncts.add(localInputDisjunct);
			this.GetOneDisjunct(i + 1);
			
			//System.out.println("New disjunct? 0 -- no, 1 -- yes:");

		}
		this.RefreshD();
		this.SortD();
		this.Refresh();
		//localInputScanner.close();
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
	
	public void SortD ()
	{
		for (int i = 0; i < disjuncts.size(); i++)
		{
			disjuncts.get(i).Sort();
		}
	}
	
	public void RefreshD ()
	{
		for (int i = 0; i < disjuncts.size(); i++)
		{
			disjuncts.get(i).Refresh();
		}
	}
	
	public void Refresh()
	{
		for (int i = 0; i < disjuncts.size() - 1; i++)
		{
			for (int j = i + 1; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetVariables().size() == disjuncts.get(j).GetVariables().size())
						&& (disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					for (int k = 0; k < disjuncts.get(i).GetVariables().size(); k++)
					{
						if ((disjuncts.get(i).GetVariables().get(k).GetName() !=
								disjuncts.get(j).GetVariables().get(k).GetName()) ||
								(disjuncts.get(i).GetVariables().get(k).GetDenial() !=
								disjuncts.get(j).GetVariables().get(k).GetDenial()))
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	public void GetOneDisjunct (int localI) // for console-constructor
	{
		ResolutionDisjunct localDisjunct = disjuncts.get(disjuncts.size() - 1);
		ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
		System.out.print("Input " + localI + ": ");
		if (localDisjunct.GetOne() == false && localDisjunct.GetEmpty() == false)
		{
			int j = 1;
			for (ResolutionVariable localVariable : variables)
			{
				//ResolutionVar var = localVariable.GetVar();
				char inverse;
				if (localVariable.GetDenial() == true)
				{
					inverse = '\u0000';
				}
				else
				{
					inverse = '!';
				}
				System.out.print(inverse + "\u0000" + localVariable.GetName());
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
			//System.out.print(localDisjunct.GetVariables().hashCode() + " ");
			System.out.print(i + ": (" + localDisjunct.GetParents()[0] + ", " + localDisjunct.GetParents()[1] + ") ");
			if (localDisjunct.GetOne() == false && localDisjunct.GetEmpty() == false)
			{
				int j = 1;
				for (ResolutionVariable localVariable : variables)
				{
					//ResolutionVar var = localVariable.GetVar();
					char inverse;
					if (localVariable.GetDenial() == true)
					{
						inverse = '\u0000';
					}
					else
					{
						inverse = '!';
					}
					System.out.print(inverse + "\u0000" + localVariable.GetName());
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
					//ResolutionVar var = localVariable.GetVar();
					for (l = 0; l < variablesTwo.size(); l++)
					{
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						//ResolutionVar varTwo = localVariableTwo.GetVar();
						if ((localVariable.GetName() == localVariableTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
							
							localNewDisjunct.Refresh();
							localNewDisjunct.Sort();
							
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
						//ResolutionVar var = localVariable.GetVar();
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							//ResolutionVar varTwo = localVariableTwo.GetVar();
							if ((localVariable.GetName() == localVariableTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
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
					//ResolutionVar var = localVariable.GetVar();
					for (l = 0; l < variablesTwo.size(); l++)
					{
						if (repeat == false)
						{
							break;
						}
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						//ResolutionVar varTwo = localVariableTwo.GetVar();
						if ((localVariable.GetName() == localVariableTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
							
							localNewDisjunct.Refresh();
							localNewDisjunct.Sort();
							
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
						//ResolutionVar var = localVariable.GetVar();
						for (l = 0; l < variablesTwo.size(); l++)
						{
							if (repeat == false)
							{
								break;
							}
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							//ResolutionVar varTwo = localVariableTwo.GetVar();
							if ((localVariable.GetName() == localVariableTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
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
		this.RefreshD();
		this.SortD();
		this.Refresh();
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
					//ResolutionVar var = localVariable.GetVar();
					for (l = 0; l < variablesTwo.size(); l++)
					{
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						//ResolutionVar varTwo = localVariableTwo.GetVar();
						if ((localVariable.GetName() == localVariableTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
							
							localNewDisjunct.Refresh();
							localNewDisjunct.Sort();
							
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
			localF.SortD(); // the order is important
			localF.Refresh();
			disjuncts.addAll(localF.GetDisjuncts());
			this.SortD(); // the order is important
			this.Refresh(); // it's only here, because at the end it doesn't work
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
						//ResolutionVar var = localVariable.GetVar();
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							//ResolutionVar varTwo = localVariableTwo.GetVar();
							if ((localVariable.GetName() == localVariableTwo.GetName()) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
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
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
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
	}
}
