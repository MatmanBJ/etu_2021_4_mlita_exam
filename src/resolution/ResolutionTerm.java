package resolution;

import java.util.ArrayList;

/**
 * This class represents terms (constants, variables and functions) for the predicates.
 * Instead of "classic" statement-logic variables they can't have "denial",
 * but they can have term type (constants, variables and functions) and terms list (only for functions).
 * Terms [instead of predicates and "classic" statement-logic variables] in the predicate (and in the functions) can't change their place.
 * @author MatmanBJ
 * @version alpha 0.21
 */
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
	
	public ResolutionTerm (ResolutionTerm localTerm)
	{
		term = localTerm.GetTerm();
		name = new String(localTerm.GetName());
		//terms = new ArrayList<ResolutionTerm>(terms);
		//terms.addAll(localTerm.GetTerms());
		for (ResolutionTerm p : localTerm.GetTerms())
		{
			terms.add(new ResolutionTerm(p));
		}
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
	
	// 1 -- DORABOTAT ETU FUNCCIJU (var-var, var-const, const-var // func-func(easily with cycle) // func-var (with no name check))
	// 2 -- REALIOVAT POLNYJ VVOD I VYVOD PREDICATOV
	// 3 -- REALIZOVAL FUNKCIJU I UNIFIKACIJU
	// 4 -- REALIZOVAT LINEJNUJU REZOLUCIJU
	
	public boolean localFunctionCheck (ResolutionTerm localTerm) // for vars only
	{
		boolean localEquality = true;
		for (int x = 0; x < localTerm.GetTerms().size(); x++)
		{
			if (localTerm.GetTerms().get(x).GetTerm().equals(ResolutionTerm.GetFunction()))
			{
				localEquality = this.localFunctionCheck(localTerm.GetTerms().get(x));
				if (localEquality == false)
				{
					x = localTerm.GetTerms().size();
				}
			}
			else if (localTerm.GetTerms().get(x).GetTerm().equals(ResolutionTerm.GetVariable()))
			{
				if (localTerm.GetTerms().get(x).equals(this))
				{
					localEquality = false;
					x = localTerm.GetTerms().size();
				}
			}
		}
		return localEquality;
	}
	
	public void change (ResolutionTerm [] localTerm)
	{
		if (this.GetTerms().size() == 0 && this.GetTerm().equals(ResolutionTerm.GetVariable()) && this.GetName().equals(localTerm[0].GetName()))
		{
			this.SetName(new String(localTerm[1].GetName()));
			this.SetTerm(localTerm[1].GetTerm());
			this.GetTerms().addAll(localTerm[1].GetTerms());
			//System.out.println(this.GetName() + " " + this.GetTerms().size());
			//System.out.println(this.GetName() + " " + localTerm[0].GetName() + " " + localTerm[1].GetName());
		}
		else if (this.GetTerms().size() > 0)
		{
			for (int i = 0; i < this.GetTerms().size(); i++)
			{
				this.GetTerms().get(i).change(localTerm);
			}
		}
	}
	
	public ResolutionTerm [] unification (Object localObject, ResolutionDisjunct localDisjunctOne, ResolutionDisjunct localDisjunctTwo)
	{
		ResolutionTerm localTerm = (ResolutionTerm) localObject;
		ResolutionTerm [] localT = new ResolutionTerm[2];
		if (this.GetTerm().equals(ResolutionTerm.GetVariable()) && localTerm.GetTerm().equals(ResolutionTerm.GetVariable()))
		{
			localT[0] = localTerm;
			localT[1] = this;
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetVariable()) && localTerm.GetTerm().equals(ResolutionTerm.GetConstant()))
		{
			localT[0] = this;
			localT[1] = localTerm;
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetConstant()) && localTerm.GetTerm().equals(ResolutionTerm.GetVariable()))
		{
			localT[0] = localTerm;
			localT[1] = this;
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetConstant()) && localTerm.GetTerm().equals(ResolutionTerm.GetConstant()) && this.GetName().equals(localTerm.GetName()))
		{
			localT[0] = localTerm;
			localT[1] = this;
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetFunction()) && localTerm.GetTerm().equals(ResolutionTerm.GetFunction())
				&& this.GetName().equals(localTerm.GetName()) && this.GetTerms().size() == localTerm.GetTerms().size())
		{
			boolean localSuccess = ResolutionTerm.unification(this, localDisjunctOne, localTerm, localDisjunctTwo);
			if (localSuccess)
			{
				localT = null;
			}
			else
			{
				localT = new ResolutionTerm[10];
			}
			System.out.println(localSuccess);
			//localT = null;
			//localT = this.unification(localTerm);
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetVariable()) && localTerm.GetTerm().equals(ResolutionTerm.GetFunction()))
		{
			localT[0] = this;
			localT[1] = localTerm;
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetFunction()) && localTerm.GetTerm().equals(ResolutionTerm.GetVariable()))
		{
			localT[0] = localTerm;
			localT[1] = this;
		}
		else
		{
			localT = null;
		}
		if (localT != null)
		{
			localT[0] = new ResolutionTerm(localT[0]);
			localT[1] = new ResolutionTerm(localT[1]);
		}
		return localT;
	}
	
	// CHANGE NAME!!!
	public static boolean unification (ResolutionTerm localPredicateThis, ResolutionDisjunct localDisjunctThis, ResolutionTerm localPredicate, ResolutionDisjunct localDisjunct)
	{
		boolean localEquality;
		//System.out.println(localPredicateThis.GetName() + " " + localPredicate.GetName() + " " + localPredicateThis.GetTerms().size() + " " + localPredicate.GetTerms().size());
		if (localPredicateThis.GetName().equals(localPredicate.GetName()) && localPredicateThis.GetTerms().size() == localPredicate.GetTerms().size())
		{
			
			localEquality = true;
			ResolutionTerm [] localT = null;
			
			//localDisjunctThis = new ResolutionDisjunct (localDisjunctThis);
			//localDisjunct = new ResolutionDisjunct (localDisjunct);
			//localPredicateThis = new ResolutionPredicate (localPredicateThis);
			//localPredicate = new ResolutionPredicate (localPredicate);
			
			for (int x = 0; x < localPredicateThis.GetTerms().size(); x++)
			{
				localEquality = localPredicateThis.GetTerms().get(x).pseudoEquals(localPredicate.GetTerms().get(x));
				
				if (localEquality == true)
				{
					localT = localPredicateThis.GetTerms().get(x).unification(localPredicate.GetTerms().get(x), localDisjunctThis, localDisjunct);
					
					if (localT != null)
					{
						if (localT.length == 10)
						{
							localEquality = false;
							x = localPredicateThis.GetTerms().size();
						}
						else
						{
							if (localT[0].equals(localT[1]) == false)
							{
								for (int y = 0; y < localDisjunctThis.GetPredicates().size(); y++)
								{
									//localPredicateThis.change(localT);
									localDisjunctThis.GetPredicates().get(y).change(localT);
								}
								for (int z = 0; z < localDisjunct.GetPredicates().size(); z++)
								{
									//localPredicate.change(localT);
									localDisjunct.GetPredicates().get(z).change(localT);
								}
							}
						}
					}
				}
				else
				{
					x = localPredicateThis.GetTerms().size();
				}
			}
		}
		else
		{
			localEquality = false;
		}
		return localEquality;
	}
	
	public boolean pseudoEquals (Object localObject)
	{
		ResolutionTerm localTerm = (ResolutionTerm) localObject;
		boolean localEquality;
		if ((this.GetTerm().equals(ResolutionTerm.GetVariable()) && localTerm.GetTerm().equals(ResolutionTerm.GetVariable()))
				|| (this.GetTerm().equals(ResolutionTerm.GetVariable()) && localTerm.GetTerm().equals(ResolutionTerm.GetConstant()))
				|| (this.GetTerm().equals(ResolutionTerm.GetConstant()) && localTerm.GetTerm().equals(ResolutionTerm.GetVariable()))
				|| (this.GetTerm().equals(ResolutionTerm.GetConstant()) && localTerm.GetTerm().equals(ResolutionTerm.GetConstant())
				&& this.GetName().equals(localTerm.GetName())))
		{
			localEquality = true;
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetFunction()) && localTerm.GetTerm().equals(ResolutionTerm.GetFunction())
				&& this.GetName().equals(localTerm.GetName()) && this.GetTerms().size() == localTerm.GetTerms().size())
		{
			localEquality = true;
			for (int x = 0; x < this.GetTerms().size(); x++)
			{
				if (this.GetTerms().get(x).pseudoEquals(localTerm.GetTerms().get(x)))
				{
					localEquality = true;
				}
				else
				{
					localEquality = false;
					x = this.GetTerms().size();
				}
			}
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetVariable()) && localTerm.GetTerm().equals(ResolutionTerm.GetFunction()))
		{
			localEquality = this.localFunctionCheck(localTerm);
		}
		else if (this.GetTerm().equals(ResolutionTerm.GetFunction()) && localTerm.GetTerm().equals(ResolutionTerm.GetVariable()))
		{
			localEquality = localTerm.localFunctionCheck(this);
		}
		else
		{
			localEquality = false;
		}
		return localEquality;
	}
	
	@Override
	public boolean equals (Object localObject)
	{
		ResolutionTerm localTerm = (ResolutionTerm) localObject;
		boolean localEquality = true;
		if ((this.GetName().equals(localTerm.GetName())) && (this.GetTerms().size() == localTerm.GetTerms().size()) && (this.GetTerm().equals(localTerm.GetTerm())))
		{
			for (int z = 0; z < localTerm.GetTerms().size(); z++)
			{
				boolean localEkwality = this.GetTerms().get(z).equals(localTerm.GetTerms().get(z));
				if (!localEkwality)
				{
					localEquality = localEkwality;
					z = localTerm.GetTerms().size();
				}
			}
		}
		else
		{
			localEquality = false;
		}
		return localEquality;
	}
	
	public static boolean isConstant (String localString)
	{
		boolean localConstant = false;
		for (int m = 0; m < localString.length(); m++)
		{
			System.out.println(String.valueOf(localString.charAt(m)));
			if (greekAlphabet.contains(String.valueOf(localString.charAt(m))))
			{
				System.out.println("ZZZZZ");
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
					//System.out.println(String.valueOf(localPredicateString.charAt(localJ)));
					//System.out.println(localName);
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
					//System.out.println(String.valueOf(localPredicateString.charAt(localJ)));
					//System.out.println(localName);
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
