package cristinae.dictionary;

public interface Translator {

	/*
	 * ∑≠“Îword
	 */
	public String translate(String word);
	
	public final int TRANSLATOR_TYPE = 200;
	
	public void setFrom(String from);
	public void setTo(String to);
}
