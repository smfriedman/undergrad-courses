package kwic;

/** Represents the original and matching forms of a word.  
 * You must implement 
 * {@link Object#hashCode()} correctly as well as
 * {@link Object#equals(Object)} 
 * for this to work.
 * 
 * @author steven.friedman
 */

public class Word {
	String w;

	/** Represent a word of a {@link Phrase}
	 * @param w The original word
	 */
	public Word(String w){
		this.w = w;
	}

	/**
	 * The word used for matching is the original word run throgh
	 * the WordCanonical filter.
	 * @return the form of the word used for matching.
	 * 
	 */
	public String getMatchWord() {
		//String matchWord = "" + w;
		WordFilter filter = WordFilter.instance();
		return filter.makeCanonical(w);
	}

	/**
	 * 
	 * @return the original word
	 */
	public String getOriginalWord() {
		return w;
	}

	/** 
	 * You must implement this right, see lab writeup notes.
	 * 
	 * This is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getMatchWord() == null) ? 0 : this.getMatchWord().hashCode());
		return result;
	}


	/**
	 * You must implement this so that two words equal each
	 * other if their matched forms equal each other.
	 * You can let eclipse generate this method automatically,
	 * but you have to modify the resulting code to get the
	 * desired effect.
	 * 
	 * This method is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (this.getMatchWord() == null) {
			if (other.getMatchWord() != null)
				return false;
		} else if (!this.getMatchWord().equals(other.getMatchWord()))
			return false;
		return true;
	}

	/**
	 * @return the word and its matching form, if different
	 */
	public String toString(){
//		if (getOriginalWord().equals(getMatchWord()))
//			return getOriginalWord();
//		else														
//			return getOriginalWord() + " --> " + getMatchWord();
		return getMatchWord();	//now just returns final version, looks better in GUI
	}

}
