package resolution;

import java.util.ArrayList;

public class ResolutionDisjunct
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private ArrayList<ResolutionVariable> variables = new ArrayList<ResolutionVariable>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- SETTERS ----------
	
	public void SetVariables (ResolutionVariable localVariables)
	{
		variables.add(localVariables);
	}
	
	// ---------- GETTERS ----------
	
	public ArrayList<ResolutionVariable> GetVariables ()
	{
		return variables;
	}
}
