package resolution;

import java.util.ArrayList;

public class ResolutionTerm
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private enum termType
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
	
	public static termType GetVariable ()
	{
		return termType.variable;
	}
	public static termType GetConstant ()
	{
		return termType.constant;
	}
	public static termType GetFunction ()
	{
		return termType.function;
	}
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
	
	// ---------- METHODS ----------
	
	public static boolean isConstant (String localString)
	{
		boolean localConstant = false;
		for (int m = 0; m < localString.length(); m++)
		{
			if (greekAlphabet.contentEquals(String.valueOf(localString.charAt(m))))
			{
				localConstant = true;
			}
		}
		return localConstant;
	}
	
	public static void newFunction (String localPredicateString, ResolutionTerm localElement)
	{
		int localJ = 0;
		String localName;
		while (localJ < localPredicateString.length())
		{
			localName = "";
			while (localJ < localPredicateString.length())
			{
				if ((localPredicateString.charAt(localJ) != '(') && (localPredicateString.charAt(localJ) != ')') && (localPredicateString.charAt(localJ) != ',') && (localPredicateString.charAt(localJ) != ';'))
				{
					//System.out.println(localPredicateString.charAt(localJ));
					//System.out.println(localPredicateString + String.valueOf(localJ));
					localName = localName.concat(String.valueOf(localPredicateString.charAt(localJ)));
					System.out.println(String.valueOf(localPredicateString.charAt(localJ)));
					System.out.println(localName);
					localJ = localJ + 1;
				}
				else
				{
					break;
				}
			}
			if (localJ < localPredicateString.length() && localPredicateString.charAt(localJ) == '(')
			{
				ResolutionTerm localTerm = new ResolutionTerm(ResolutionTerm.GetFunction(), localName);
				localElement.SetTerms(localTerm);
				int localBalance = 0;
				int n = 0;
				do {
					if (localPredicateString.charAt(localJ + n) == '(')
					{
						localBalance = localBalance - 1;
					}
					else if (localPredicateString.charAt(localJ + n) == ')')
					{
						localBalance = localBalance + 1;
					}
					n = n + 1;
				} while (localBalance != 0);
				int localIndex = localJ + n - 1;
				if (localJ + 1 != localIndex)
				{
					ResolutionTerm.newFunction(localPredicateString.substring(localJ + 1, localIndex), localTerm);
				}
				else
				{
					ResolutionTerm.newFunction("", localTerm);
				}
				localJ = localIndex + 1;
			}
			else if (ResolutionTerm.isConstant(localName))
			{
				localElement.SetTerms(new ResolutionTerm(ResolutionTerm.GetConstant(), localName));
			}
			else
			{
				localElement.SetTerms(new ResolutionTerm(ResolutionTerm.GetVariable(), localName));
			}
			localJ = localJ + 1;
		}
	}
	
	public static void newFunction (String localPredicateString, ResolutionPredicate localElement)
	{
		int localJ = 0;
		String localName;
		while (localJ < localPredicateString.length())
		{
			localName = "";
			while (localJ < localPredicateString.length())
			{
				if ((localPredicateString.charAt(localJ) != '(') && (localPredicateString.charAt(localJ) != ')') && (localPredicateString.charAt(localJ) != ',') && (localPredicateString.charAt(localJ) != ';'))
				{
					//System.out.println(localPredicateString.charAt(localJ));
					//System.out.println(localPredicateString + String.valueOf(localJ));
					localName = localName.concat(String.valueOf(localPredicateString.charAt(localJ)));
					System.out.println(String.valueOf(localPredicateString.charAt(localJ)));
					System.out.println(localName);
					localJ = localJ + 1;
				}
				else
				{
					break;
				}
			}
			if (localJ < localPredicateString.length() && localPredicateString.charAt(localJ) == '(')
			{
				//System.out.println("Function???");
				ResolutionTerm localTerm = new ResolutionTerm(ResolutionTerm.GetFunction(), localName);
				localElement.SetTerms(localTerm);
				int localBalance = 0;
				int n = 0;
				do {
					if (localPredicateString.charAt(localJ + n) == '(')
					{
						localBalance = localBalance - 1;
					}
					else if (localPredicateString.charAt(localJ + n) == ')')
					{
						localBalance = localBalance + 1;
					}
					n = n + 1;
				} while (localBalance != 0);
				int localIndex = localJ + n - 1;
				if (localJ + 1 != localIndex)
				{
					ResolutionTerm.newFunction(localPredicateString.substring(localJ + 1, localIndex), localTerm);
				}
				else
				{
					ResolutionTerm.newFunction("", localTerm);
				}
				localJ = localIndex + 1;
			}
			else if (ResolutionTerm.isConstant(localName))
			{
				localElement.SetTerms(new ResolutionTerm(ResolutionTerm.GetConstant(), localName));
			}
			else
			{
				localElement.SetTerms(new ResolutionTerm(ResolutionTerm.GetVariable(), localName));
			}
			localJ = localJ + 1;
		}
	}
}
