package resolution;

import java.util.ArrayList;

/**
 * Resolution predicate class.
 * @author MatmanBJ
 * @version alpha 0.19
 */
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
	
	public ResolutionPredicate (ResolutionPredicate localPredicate)
	{
		denial = localPredicate.GetDenial();
		name = new String(localPredicate.GetName());
		//terms = new ArrayList<ResolutionTerm>(localPredicate.GetTerms());
		//terms.addAll(localPredicate.GetTerms());
		for (ResolutionTerm p : localPredicate.GetTerms())
		{
			terms.add(new ResolutionTerm(p));
		}
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
	
	// ---------- METHODS ----------
	
	public void change (ResolutionTerm [] localTerm)
	{
		for (int i = 0; i < this.GetTerms().size(); i++)
		{
			this.GetTerms().get(i).change(localTerm);
		}
	}
	
	public static boolean unification (ResolutionPredicate localPredicateThis, ResolutionDisjunct localDisjunctThis, ResolutionPredicate localPredicate, ResolutionDisjunct localDisjunct)
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
					localT = localPredicateThis.GetTerms().get(x).unification(localPredicate.GetTerms().get(x));
					if (localT != null)
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
		////System.out.println(localPredicateThis.toStringPredicate());
		////System.out.println(localPredicate.toStringPredicate());
		return localEquality;
	}
	
	public boolean preContrary (Object localObject)
	{
		ResolutionPredicate localPredicate = (ResolutionPredicate) localObject;
		return ((this.GetName().equals(localPredicate.GetName())) && (this.GetTerms().size() == localPredicate.GetTerms().size()) && (this.GetDenial() != localPredicate.GetDenial()));
	}
	
	public boolean pseudoEquals (Object localObject)
	{
		ResolutionPredicate localPredicate = (ResolutionPredicate) localObject;
		boolean localEquality = true;
		if ((this.GetName().equals(localPredicate.GetName())) && (this.GetTerms().size() == localPredicate.GetTerms().size()))
		{
			for (int z = 0; z < localPredicate.GetTerms().size(); z++)
			{
				boolean localEkwality = this.GetTerms().get(z).pseudoEquals(localPredicate.GetTerms().get(z));
				if (!localEkwality)
				{
					localEquality = localEkwality;
					z = localPredicate.GetTerms().size();
				}
			}
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
		ResolutionPredicate localPredicate = (ResolutionPredicate) localObject;
		boolean localEquality = true;
		if ((this.GetName().equals(localPredicate.GetName())) && (this.GetTerms().size() == localPredicate.GetTerms().size()))
		{
			for (int z = 0; z < localPredicate.GetTerms().size(); z++)
			{
				boolean localEkwality = this.GetTerms().get(z).equals(localPredicate.GetTerms().get(z));
				if (!localEkwality)
				{
					localEquality = localEkwality;
					z = localPredicate.GetTerms().size();
				}
			}
		}
		else
		{
			localEquality = false;
		}
		return localEquality;
	}
}
