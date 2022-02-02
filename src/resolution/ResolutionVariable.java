package resolution;

public class ResolutionVariable
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private boolean denial; // if TRUE -- no denial, if FALSE -- inverse
	private ResolutionVar var;
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- SETTERS ----------
	
	public void SetDenial (boolean localDenial)
	{
		denial = localDenial;
	}
	public void SetVar (ResolutionVar localVar)
	{
		var = localVar;
	}
	
	// ---------- GETTERS ----------
	
	public boolean GetDenial ()
	{
		return denial;
	}
	public ResolutionVar GetVar ()
	{
		return var;
	}
}
