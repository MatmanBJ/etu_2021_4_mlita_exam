package resolution;

import java.util.ArrayList;

/**
 * Resolution predicate class.
 * @author MatmanBJ
 * @version alpha 0.18
 * @since alpha 0.18
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
	
	public boolean unification (ResolutionDisjunct localDisjunctThis, ResolutionPredicate localPredicate, ResolutionDisjunct localDisjunct)
	{
		return true;
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
