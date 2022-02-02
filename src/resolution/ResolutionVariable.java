package resolution;

public class ResolutionVariable
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private boolean denial; // if TRUE -- no denial, if FALSE -- inverse
	private char name;
	//private ResolutionVar var;
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTORS ----------
	
	public ResolutionVariable () // default constructor
	{}
	
	public ResolutionVariable (boolean localDenial, char localName) // special constructor
	{
		denial = localDenial;
		name = localName;
		//var = localVar;
	}
	
	// ---------- SETTERS ----------
	
	public void SetDenial (boolean localDenial)
	{
		denial = localDenial;
	}
	public void SetName (char localName)
	{
		name = localName;
	}
	/*public void SetVar (ResolutionVar localVar)
	{
		var = localVar;
	}*/
	
	// ---------- GETTERS ----------
	
	public boolean GetDenial ()
	{
		return denial;
	}
	public char GetName ()
	{
		return name;
	}
	/*public ResolutionVar GetVar ()
	{
		return var;
	}*/
}
