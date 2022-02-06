package resolution;

import java.util.ArrayList;

public class ResolutionPredicate
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private boolean denial; // if TRUE -- no denial, if FALSE -- inverse
	private String name;
	private ArrayList<ResolutionTerm> terms = new ArrayList<ResolutionTerm>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTORS ----------
	
	public ResolutionPredicate () // default constructor
	{}
	
	public ResolutionPredicate (boolean localDenial, String localName) // special constructor
	{
		denial = localDenial;
		name = localName;
	}
	
	// ---------- SETTERS ----------
	
	public void SetDenial (boolean localDenial)
	{
		denial = localDenial;
	}
	public void SetName (String localName)
	{
		name = localName;
	}
	public void SetTerms (ResolutionTerm localResolutionTerm)
	{
		terms.add(localResolutionTerm);
	}
	
	// ---------- GETTERS ----------
	
	public boolean GetDenial ()
	{
		return denial;
	}
	public String GetName ()
	{
		return name;
	}
	public ArrayList<ResolutionTerm> GetTerms ()
	{
		return terms;
	}
}
