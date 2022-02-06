package resolution;

import java.util.ArrayList;

public class ResolutionTerm
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	enum termType
	{
		variable, constant, function
	};
	
	private static int id = 0;
	private static String greekAlphabet = "ΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΡρΣσςΤτΥυΦφΧχΨψΩω";
	private static String littleGreekAlphabet = "αβγδεζηθικλμνξοπρστυφχψω";
	
	private termType term; // term type
	private String name;
	private ArrayList<ResolutionTerm> terms = new ArrayList<ResolutionTerm>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTORS ----------
	
	public ResolutionTerm () // default constructor
	{}
	
	public ResolutionTerm (termType localTerm, String localName) // special constructor
	{
		term = localTerm;
		name = localName;
	}
	
	// ---------- SETTERS ----------
	
	public void SetTerm (termType localTerm)
	{
		term = localTerm;
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
	
	public static int GetID ()
	{
		return id;
	}
	public static String GetLittleGreekAlphabet ()
	{
		return littleGreekAlphabet;
	}
	public static String GetGreekAlphabet ()
	{
		return greekAlphabet;
	}
	public termType GetTerm ()
	{
		return term;
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
