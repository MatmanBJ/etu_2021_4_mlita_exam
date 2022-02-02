package resolution;

public class ResolutionVar
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private static int currentNumber = 1;
	private int number;
	private char name;
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTOR ----------
	
	public ResolutionVar (char localName)
	{
		number = currentNumber;
		currentNumber = currentNumber + 1;
		name = localName;
	}
	
	// ---------- GETTERS ----------
	
	public int GetNumber ()
	{
		return number;
	}
	public char GetName ()
	{
		return name;
	}
}
