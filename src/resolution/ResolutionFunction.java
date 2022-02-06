package resolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.lang.Object.*;

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
		System.out.println("Repeated and same disjuncts will be deleted!");
		System.out.println("How many disjuncts do you want?");
		String [] localInputString = new String[localInputScanner.nextInt()];
		localInputScanner.nextLine();
		System.out.println("Input them:");
		for (int i = 0; i < localInputString.length; i++)
		{
			ArrayList<ResolutionVariable> localInputVariables = new ArrayList<ResolutionVariable>();
			localInputString[i] = localInputScanner.nextLine();
			
			boolean localEmpty = false;
			boolean localOne = false;
			
			String localInputVars [] = localInputString[i].split("\\s");
			for (int j = 0; j < localInputVars.length; j++)
			{
				if (!localInputVars[j].equals("") && !localInputVars[j].contains("+") && !localInputVars[j].equals("□") && !localInputVars[j].equals("1"))
				{
					if (localInputVars[j].charAt(0) == '!')
					{
						localInputVariables.add(new ResolutionVariable(false, localInputVars[j].substring(1)));
					}
					else
					{
						localInputVariables.add(new ResolutionVariable(true, localInputVars[j]));
					}
				}
				else if (localInputVars[j].equals("□"))
				{
					localEmpty = true;
				}
				else if (localInputVars[j].equals("1"))
				{
					localOne = true;
				}
			}
			
			ResolutionDisjunct localInputDisjunct;
			if (localEmpty == true)
			{
				localInputDisjunct = new ResolutionDisjunct();
				localInputDisjunct.SetEmpty(localEmpty);
			}
			else if (localOne == true)
			{
				localInputDisjunct = new ResolutionDisjunct();
				localInputDisjunct.SetOne(localOne);
			}
			else
			{
				localInputDisjunct = new ResolutionDisjunct(localInputVariables);
			}
			
			disjuncts.add(localInputDisjunct);
			this.GetOneDisjunct(i + 1);
			
			//System.out.println("New disjunct? 0 -- no, 1 -- yes:");

		}
		this.RefreshD();
		this.SortD();
		this.Refresh();
		System.out.println("Current console input:");
		this.GetFunction();
		System.out.print("\n");
	}
	
	public ResolutionFunction (int localJust) // for predicates
	{
		int stop = 1;
		
		char leftBrace = '(';
		char rightBrace = ')';
		
		Scanner localInputScanner = new Scanner(System.in);
		System.out.println("Repeated and same disjuncts will be deleted!");
		System.out.println("How many disjuncts do you want?");
		String [] localInputString = new String[localInputScanner.nextInt()];
		localInputScanner.nextLine();
		System.out.println("Input them:");
		for (int i = 0; i < localInputString.length; i++)
		{
			ArrayList<ResolutionPredicate> localInputPreds = new ArrayList<ResolutionPredicate>();
			localInputString[i] = localInputScanner.nextLine();
			
			boolean localEmpty = false;
			boolean localOne = false;
			boolean localDenial = true;
			
			String localInputPredicates [] = localInputString[i].split("\\s");
			for (int j = 0; j < localInputPredicates.length; j++)
			{
				if (!localInputPredicates[j].equals("") && !localInputPredicates[j].contains("+") && !localInputPredicates[j].equals("□") && !localInputPredicates[j].equals("1"))
				{
					HashMap<Character, Integer> localMap = new HashMap<Character, Integer>();
					String s = "aasjjikkk";
					char c = '(';
					for (int localI = 0; localI < s.length(); localI++)
					{
					    Integer val = localMap.get(c);
					    if (val != null)
					    {
					    	localMap.put(c, val + 1);
					    }
					    else
					    {
					    	localMap.put(c, 1);
					    }
					}
					
					for (int localJ = 0; localJ < localInputPredicates[j].length(); localJ++)
					{
						if (localInputPredicates[j].charAt(localJ) == '(' || localInputPredicates[j].charAt(localJ) == ')')
						{
							
						}
					}
					
					if (localInputPredicates[j].charAt(0) == '!')
					{
						localInputPredicates[j] = localInputPredicates[j].substring(1);
						localDenial = false;
					}
					
					int localJ = 0;
					String localName = "";
					while (localInputPredicates[j].charAt(localJ) != '(')
					{
						localName = localName.concat(String.valueOf(localInputPredicates[j].charAt(localJ)));
						localJ = localJ + 1;
					}
					ResolutionPredicate localPredicate = new ResolutionPredicate(localDenial, localName);
					
					if (localInputPredicates[j].indexOf(leftBrace) + 1 != localInputPredicates[j].lastIndexOf(rightBrace))
					{
						ResolutionTerm.newFunction(localInputPredicates[j].substring(localInputPredicates[j].indexOf(leftBrace) + 1, localInputPredicates[j].lastIndexOf(rightBrace)), localPredicate);
					}
					else
					{
						ResolutionTerm.newFunction("", localPredicate);
					}
					
					localInputPreds.add(localPredicate);
					
					//int ooo = localInputPredicates[j].
					
					//String localInputVars [] = localInputPredicates[j].split(";");
					//String localPredicate;
					//String localVars;
					//localInputPredicates[j].indexOf('(');
					//localInputPredicates[j].lastIndexOf(')');
					/*if (localInputPredicates[j].charAt(0) == '!')
					{
						localInputVariables.add(new ResolutionVariable(false, localInputPredicates[j].substring(1)));
					}
					else
					{
						localInputVariables.add(new ResolutionVariable(true, localInputPredicates[j]));
					}*/
				}
				else if (localInputPredicates[j].equals("□"))
				{
					localEmpty = true;
				}
				else if (localInputPredicates[j].equals("1"))
				{
					localOne = true;
				}
			}
			
			ResolutionDisjunct localInputDisjunct;
			if (localEmpty == true)
			{
				localInputDisjunct = new ResolutionDisjunct();
				localInputDisjunct.SetEmpty(localEmpty);
			}
			else if (localOne == true)
			{
				localInputDisjunct = new ResolutionDisjunct();
				localInputDisjunct.SetOne(localOne);
			}
			else
			{
				localInputDisjunct = new ResolutionDisjunct(localInputPreds, 1);
			}
			
			disjuncts.add(localInputDisjunct);
			
			ResolutionDisjunct localDisjunct = disjuncts.get(disjuncts.size() - 1);
			ArrayList<ResolutionPredicate> variables = localDisjunct.GetPredicates();
			System.out.print("Input " + i + ": ");
			System.out.println(localDisjunct.toStringPredicate());
			
			//this.GetOneDisjunct(i + 1);
			
			//System.out.println("New disjunct? 0 -- no, 1 -- yes:");
			
			// UPDATE INPT FUNCTION WITH BOOLEAN
			// UPDATE INPUT FUNCTION WITH EMPTY?ONE

		}
		this.RefreshD();
		this.SortD();
		this.Refresh();
		System.out.println("Current console input:");
		this.GetFunction();
		System.out.print("\n");
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
	
	public void RefreshSize () //(int localSize) // only by size, "classic" refresh isn't included
	{
		Comparator<ResolutionDisjunct> bySize = new Comparator<ResolutionDisjunct>()
	    {
	        @Override
	        public int compare(ResolutionDisjunct localRDOne, ResolutionDisjunct localRDTwo)
	        {
	            return Integer.compare(localRDOne.GetVariables().size() , localRDTwo.GetVariables().size());
	        }
	    };
		Collections.sort(disjuncts, bySize);
	}
	
	public void RefreshExtension (int localSize)
	{
		for (int i = 0; i < localSize; i++)
		{
			for (int j = localSize; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					int localMaxSize = 0;
					if (disjuncts.get(i).GetVariables().size() <= disjuncts.get(j).GetVariables().size())
					{
						localMaxSize = disjuncts.get(i).GetVariables().size();
					}
					else
					{
						ekwality = false;
					}
					// we have two situations: the last (j) disjunct should be less or equal to current (i)
					// disjunct -- so that we can delete him, else it would be unnesessary and wrong
					// because we should prevent expansion instead of giving a "chance" to the new disjunct!!!
					for (int k = 0; k < localMaxSize; k++)
					{
						boolean localEkwality = false;
						for (int l = 0; l < disjuncts.get(j).GetVariables().size(); l++)
						{
							if ((disjuncts.get(i).GetVariables().get(k).GetName().equals(disjuncts.get(j).GetVariables().get(l).GetName())) // don't forget "!"
									&&
									(disjuncts.get(i).GetVariables().get(k).GetDenial() ==
									disjuncts.get(j).GetVariables().get(l).GetDenial()))
							{
								//System.out.println((i + 1) + " " + (j + 1) + " " + (k + 1) + " " + (l + 1));
								localEkwality = true;
							}
						}
						if (localEkwality == false)
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
				else if (disjuncts.get(j).GetOne() == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
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
						if (!(disjuncts.get(i).GetVariables().get(k).GetName().equals(disjuncts.get(j).GetVariables().get(k).GetName())) // don't forget "!"
								||
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
		System.out.println(localDisjunct.toString());
	}
	
	public void GetFunction ()
	{
		int i = 1;
		for (ResolutionDisjunct localDisjunct : disjuncts)
		{
			System.out.print(localDisjunct.toOutputString());
		}
	}
	
	public void ResolutionFileInputExample (String localFileName)
	{
		try(FileReader reader = new FileReader(localFileName))
        {
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf)) > 0)
            {
                if(c < 256)
                {
                    buf = Arrays.copyOf(buf, c);
                }
                System.out.print(buf);
            } 
        }
        catch(IOException ex)
		{
            System.out.println(ex.getMessage());
        }
	}
	
	public void ResolutionFileInput (String localFileName)
	{
		try(FileReader reader = new FileReader(localFileName))
        {
            char[] localBufferChar = new char[256];
            //String localBufferString = "\u0000"; // if you do that, you will get a space as a symbol!!!
            String localBufferString = new String(); // if you do that, all will be OK!!!
            String localBufferStringArray [];
            int c;
            while((c = reader.read(localBufferChar)) > 0)
            {
                if(c < 256)
                {
                    localBufferChar = Arrays.copyOf(localBufferChar, c);
                }
                localBufferString = localBufferString + String.copyValueOf(localBufferChar);
            } 
            localBufferStringArray = localBufferString.split("\n");
            System.out.println("Current file input:");
            for (int i = 0; i < localBufferStringArray.length; i++)
    		{
    			ArrayList<ResolutionVariable> localInputVariables = new ArrayList<ResolutionVariable>();
    			boolean localEmpty = false;
    			boolean localOne = false;
    			
    			String localInputVars [] = localBufferStringArray[i].split("\\s");
    			for (int j = 0; j < localInputVars.length; j++)
    			{
    				if (!localInputVars[j].equals("") && !localInputVars[j].contains("+") && !localInputVars[j].equals("□") && !localInputVars[j].equals("1"))
    				{
    					if (localInputVars[j].charAt(0) == '!')
    					{
    						localInputVariables.add(new ResolutionVariable(false, localInputVars[j].substring(1)));
    					}
    					else
    					{
    						localInputVariables.add(new ResolutionVariable(true, localInputVars[j]));
    					}
    				}
    				else if (localInputVars[j].equals("□"))
    				{
    					localEmpty = true;
    				}
    				else if (localInputVars[j].equals("1"))
    				{
    					localOne = true;
    				}
    			}
    			
    			ResolutionDisjunct localInputDisjunct;
    			if (localEmpty == true)
    			{
    				localInputDisjunct = new ResolutionDisjunct();
    				localInputDisjunct.SetEmpty(localEmpty);
    			}
    			else if (localOne == true)
    			{
    				localInputDisjunct = new ResolutionDisjunct();
    				localInputDisjunct.SetOne(localOne);
    			}
    			else
    			{
    				localInputDisjunct = new ResolutionDisjunct(localInputVariables);
    			}
    			disjuncts.add(localInputDisjunct);
    			this.GetOneDisjunct(i + 1);
    			
    			//System.out.println("New disjunct? 0 -- no, 1 -- yes:");

    		}
    		this.RefreshD();
    		this.SortD();
    		this.Refresh();
    		System.out.print("\n");
        }
        catch(IOException ex)
		{
            System.out.println(ex.getMessage());
        }
	}
	
	public void ResolutionFileExample (String localFileName)
	{
		try (FileWriter writer = new FileWriter(localFileName, false))
        {
           // запись всей строки
            String text = "Hello Gold!";
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.append('E');
            writer.flush();
        }
        catch(IOException localException)
		{
            System.out.println(localException.getMessage());
        }
	}
	
	public void ResolutionFileReadable (String localFileName)
	{
		try (FileWriter writer = new FileWriter(localFileName, false))
        {
			int i = 1;
			String localOut [] = new String[this.GetDisjuncts().size()];
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				localOut[i - 1] = localDisjunct.toReadableString();
				i = i + 1;
			}
			i = 1;
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				String localString = localOut[i - 1];
				writer.write(localString);
				writer.append('\n');
				i = i + 1;
			}
            writer.flush();
        }
        catch(IOException localException)
		{
            System.out.println(localException.getMessage());
        }
	}
	
	public void ResolutionFileUnreadable (String localFileName)
	{
		try (FileWriter writer = new FileWriter(localFileName, false))
        {
			int i = 1;
			String localOut [] = new String[this.GetDisjuncts().size()];
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				localOut[i - 1] = localDisjunct.toOutputString();
				i = i + 1;
			}
			i = 1;
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				String localString = localOut[i - 1];
				writer.write(localString);
				i = i + 1;
			}
            writer.flush();
        }
        catch(IOException localException)
		{
            System.out.println(localException.getMessage());
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
					for (l = 0; l < variablesTwo.size(); l++)
					{
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
						{
							ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
							
							localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
							ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
							localNewDisjunct.GetVariables().remove(x);
							
							localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
							ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
							localNewDisjunct.GetVariables().remove(y);
							int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()};
							localNewDisjunct.SetParents(localParents);
							
							localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName()));
							
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()};
								localNewDisjunct.SetParents(localParents);
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName()));
								
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
					for (l = 0; l < variablesTwo.size(); l++)
					{
						if (repeat == false)
						{
							break;
						}
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
						{
							ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
							
							localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
							ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
							localNewDisjunct.GetVariables().remove(x);
							
							localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
							ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
							localNewDisjunct.GetVariables().remove(y);
							int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()};
							localNewDisjunct.SetParents(localParents);
							
							localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName()));
							
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							if (repeat == false)
							{
								break;
							}
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()};
								localNewDisjunct.SetParents(localParents);
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName()));
								
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
					for (l = 0; l < variablesTwo.size(); l++)
					{
						ResolutionVariable localVariableTwo = variablesTwo.get(l);
						if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
						{
							ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
							
							localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
							ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
							localNewDisjunct.GetVariables().remove(x);
							
							localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
							ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
							localNewDisjunct.GetVariables().remove(y);
							int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
							localNewDisjunct.SetParents(localParents); // set parent's numbers
							
							localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
							
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
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
	
	// ----------------------------------------
	// ---------- RESOLUTION METHODS ----------
	// ----------------------------------------
	
	// ---------- A GIANT OF THOUHT, A FATHER OF RUSSIAN REVOLUTION (*) ----------
	// (*) -- RESOLUTION
	public void ResolutionAllUnique () // all possible, but unique disjuncts (first gotten disjunct is new unique)
	{ // i guess it's a remake of the famous "ResolutionHash" (absolutly LEGENDARY function)
		int i;
		int j;
		int k;
		int l;
		int c;
		boolean repeat = true;
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		this.RefreshD();
		this.SortD();
		this.Refresh();
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
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
	
	// ---------- SATURATION STRATEGY ----------
	
	public void ResolutionSaturation () // saturation strategy demonstration
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		int iter = 1;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true; // repeating indicator
		boolean adding = false; // adding disjuncts in the end indicator
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.SortD(); // the order is important
			disjuncts.addAll(localF.GetDisjuncts());
			this.SortD(); // the order is important
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetEmpty() && repeat)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
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
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				disjuncts.addAll(localF.GetDisjuncts());
			}
		}
	}
	
	// ---------- PREFERENCE STRATEGY ----------
	
	public void ResolutionPreference () // saturation strategy demonstration
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		int iter = 1;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true; // repeating indicator
		boolean adding = false; // adding disjuncts in the end indicator
		ResolutionFunction localF = new ResolutionFunction();
		ResolutionFunction localClassicRF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.SortD(); // the order is important
			localF.RefreshSize();
			localClassicRF.GetDisjuncts().addAll(localF.GetDisjuncts());
			this.RefreshSize();
			disjuncts.addAll(localF.GetDisjuncts());
			this.SortD(); // the order is important
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetEmpty() && repeat)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
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
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				localF.RefreshSize();
				localClassicRF.GetDisjuncts().addAll(localF.GetDisjuncts());
				disjuncts.addAll(localF.GetDisjuncts());
				disjuncts.removeAll(disjuncts);
				disjuncts.addAll(localClassicRF.GetDisjuncts());
			}
		}
	}
	
	// ---------- STRIKEOUT STRATEGY ----------
	
	public void ResolutionStrikeout () // strikeout strategy demonstration
	{		
		int i;
		int j;
		int k;
		int l;
		int c;
		int iter = 1;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true;
		boolean adding = false; // adding disjuncts in the end indicator
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.SortD(); // the order is important
			localF.Refresh();
			disjuncts.addAll(localF.GetDisjuncts());
			this.SortD(); // the order is important
			this.RefreshExtension(d); // it's only here, because at the end it doesn't work
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
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetEmpty() && repeat)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
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
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				
				d = disjuncts.size();
				localF.SortD(); // the order is important
				localF.Refresh();
				disjuncts.addAll(localF.GetDisjuncts());
				this.SortD(); // the order is important
				this.RefreshExtension(d); // it's only here, because at the end it doesn't work
			}
		}
	}
}
