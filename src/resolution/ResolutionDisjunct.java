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
	private int [] parents = new int [2];
	private String contrary; // the contrary variable of parents
	private ArrayList<ResolutionVariable> variables = new ArrayList<ResolutionVariable>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTOR ----------
	
	public ResolutionDisjunct () // default constructor
	{
		one = false;
		empty = false;
		parents[0] = 0;
		parents[1] = 0;
		contrary = "0";
	}
	
	public ResolutionDisjunct (ArrayList<ResolutionVariable> localResolutionVariable) // special constructor
	{
		one = false;
		empty = false;
		variables.addAll(localResolutionVariable);
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
	
	// ---------- GETTERS ----------
	
	public boolean GetOne ()
	{
		return one;
	}
	public boolean GetEmpty ()
	{
		return empty;
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
		/*for (int i = 0; i < variables.size() - 1; i++)
		{
			for (int j = i + 1; j < variables.size(); j++)
			{
				if ((int)variables.get(i).GetName() > (int)variables.get(j).GetName())
				{
					Collections.swap(variables, i, j);
				}
			}
		}*/
	}
	
	public void Refresh () // delete duplicate elements
	{
		for (int i = 0; i < variables.size() - 1; i++)
		{
			for (int j = i + 1; j < variables.size(); j++)
			{
				if ((variables.get(i).GetName().equals(variables.get(j).GetName()))
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
				}
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
	
	@Override
	public String toString ()
	{
		return "!!!";
	}
	
}
