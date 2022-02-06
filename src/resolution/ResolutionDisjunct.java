package resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

public class ResolutionDisjunct implements Comparable<ResolutionDisjunct>
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private boolean one; // if TRUE -- disjunct = 1
	private boolean empty; // if TRUE -- disjunct = empty (0)
	private static int maxID = 0;
	private int id;
	private int [] parents = new int [2];
	private String contrary; // the contrary variable of parents
	private ArrayList<ResolutionVariable> variables = new ArrayList<ResolutionVariable>();
	private ArrayList<ResolutionPredicate> predicates = new ArrayList<ResolutionPredicate>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTOR ----------
	
	public ResolutionDisjunct () // default constructor
	{
		one = false;
		empty = false;
		id = maxID + 1;
		maxID = maxID + 1;
		parents[0] = 0;
		parents[1] = 0;
		contrary = "0";
	}
	
	public ResolutionDisjunct (ArrayList<ResolutionVariable> localResolutionVariable) // special constructor
	{
		one = false;
		empty = false;
		variables.addAll(localResolutionVariable);
		id = maxID + 1;
		maxID = maxID + 1;
		parents[0] = 0;
		parents[1] = 0;
		contrary = "0";
	}
	
	// ---------- SETTERS ----------
	
	public void SetOne (boolean localOne)
	{
		one = localOne;
	}
	public void SetEmpty (boolean localEmpty)
	{
		empty = localEmpty;
	}
	public static void SetMaxID ()
	{
		maxID = 0;
	}
	public void SetParents (int [] localParents)
	{
		parents[0] = localParents[0];
		parents[1] = localParents[1];
	}
	public void SetContrary (String localContrary)
	{
		contrary = localContrary;
	}
	public void SetVariables (ResolutionVariable localVariables)
	{
		variables.add(localVariables);
	}
	public void SetPredicates (ResolutionPredicate localPredicates)
	{
		predicates.add(localPredicates);
	}
	
	// ---------- GETTERS ----------
	
	public boolean GetOne ()
	{
		return one;
	}
	public boolean GetEmpty ()
	{
		return empty;
	}
	public int GetMaxID ()
	{
		return maxID;
	}
	public int GetID ()
	{
		return id;
	}
	public int [] GetParents ()
	{
		return parents;
	}
	public String GetContrary ()
	{
		return contrary;
	}
	public ArrayList<ResolutionVariable> GetVariables ()
	{
		return variables;
	}
	public ArrayList<ResolutionPredicate> GetPredicates ()
	{
		return predicates;
	}
	
	// ---------- OTHERS ----------
	
	public void Sort () // sorting elements in alphabet order
	{
		Collections.sort(variables, new Comparator<ResolutionVariable>()
		{
		    public int compare(ResolutionVariable v1, ResolutionVariable v2)
		    {
		        return v1.GetName().compareTo(v2.GetName());
		    }
		});
	}
	
	public void Refresh () // delete duplicate elements
	{
		for (int i = 0; i < variables.size() - 1; i++)
		{
			for (int j = i + 1; j < variables.size(); j++)
			{
				if (variables.get(i).equals(variables.get(j)))
				{
					variables.remove(j);
					j = j - 1;
				}
				else if ((variables.get(i).GetName().equals(variables.get(j).GetName()))
						&& (variables.get(i).GetDenial() != variables.get(j).GetDenial()))
				{
					variables.removeAll(variables);
					this.SetOne(true);
				}
				/*if ((variables.get(i).GetName().equals(variables.get(j).GetName()))
						&& variables.get(i).GetDenial() == variables.get(j).GetDenial())
				{
					variables.remove(j);
					j = j - 1;
				}
				else if ((variables.get(i).GetName().equals(variables.get(j).GetName()))
						&& (variables.get(i).GetDenial() != variables.get(j).GetDenial()))
				{
					variables.removeAll(variables);
					this.SetOne(true);
				}*/
			}
		}
		if (this.GetOne() == false && variables.size() == 0)
		{
			this.SetEmpty(true);
		}
	}
	
	@Override
	public int compareTo (ResolutionDisjunct localDisjunct)
	{
		return 0;
	}
	
	public String toOutputString ()
	{
		String localOutputStringDisjunct;
		localOutputStringDisjunct = String.valueOf(id) + ": (" + String.valueOf(parents[0]) + ", " + String.valueOf(parents[1]) + ", " + String.valueOf(contrary) + ") " + this.toString() + "\n";
		return localOutputStringDisjunct;
	}
	
	/*public String toOutputString (String lll)
	{
		return "dsds";
	}*/
	
	public String toReadableString ()
	{
		String localStringDisjunct = "";
		if (one == false && empty == false)
		{
			int j = 1;
			for (ResolutionVariable localVariable : variables)
			{
				char inverse;
				if (localVariable.GetDenial() == true)
				{
					inverse = '\u0000';
				}
				else
				{
					inverse = '!';
				}
				localStringDisjunct = localStringDisjunct + inverse + "\u0000" + localVariable.GetName();
				if (j < variables.size())
				{
					localStringDisjunct = localStringDisjunct + " + ";
				}
				j = j + 1;
			}
		}
		else if (one == true)
		{
			localStringDisjunct = localStringDisjunct + "1";
		}
		else if (empty == true)
		{
			localStringDisjunct = localStringDisjunct + "□";
		}

		return localStringDisjunct;
	}
	
	@Override
	public String toString ()
	{
		String localStringDisjunct = "";
		if (one == false && empty == false)
		{
			int j = 1;
			for (ResolutionVariable localVariable : variables)
			{
				char inverse;
				if (localVariable.GetDenial() == true)
				{
					inverse = '\u0000';
				}
				else
				{
					inverse = '!';
				}
				localStringDisjunct = localStringDisjunct + inverse + "\u0000" + localVariable.GetName();
				if (j < variables.size())
				{
					localStringDisjunct = localStringDisjunct + " + ";
				}
				j = j + 1;
			}
		}
		else if (one == true)
		{
			localStringDisjunct = localStringDisjunct + "1 (~ true)";
		}
		else if (empty == true)
		{
			localStringDisjunct = localStringDisjunct + "□ (~ false)";
		}

		return localStringDisjunct;
	}
	
}
