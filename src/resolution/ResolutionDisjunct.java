package resolution;

import java.util.ArrayList;

public class ResolutionDisjunct
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private boolean one; // if TRUE -- disjunct = 1
	private boolean empty; // if TRUE -- disjunct = empty (0)
	private int [] parents = new int [2];
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
	}
	
	public ResolutionDisjunct (ArrayList<ResolutionVariable> localResolutionVariable) // special constructor
	{
		one = false;
		empty = false;
		variables.addAll(localResolutionVariable);
		parents[0] = 0;
		parents[1] = 0;
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
	public int [] GetParents()
	{
		return parents;
	}
	public ArrayList<ResolutionVariable> GetVariables ()
	{
		return variables;
	}
}
