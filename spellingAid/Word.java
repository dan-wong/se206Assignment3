package spellingAid;

import speech.Speech;

/**
 * This is the class Representing a Word Object.
 * (code reused, modified from assignment 2)
 * 
 * Responsibilities: 
 * 1. To pronounce a word (using Speech Class).
 * 2. Compare a word with a string to see if they are equal.
 * 3. Stores the status of the correctness of the word.
 * 
 * 
 * @author victor
 *
 */
public class Word {
	/**
	 * This enum class represents the four successStatus that a word can have 
	 * after being spelt.
	 * @author victor
	 *
	 */
	public enum SuccessStatus {
		MASTERED, FAULTED, FAILED;
	}
	private String _word;
	private Word.SuccessStatus _successStatus;
	private Speech _speech;
	
	public Word (String word){
		_word = word;
		_speech = new Speech();
	}
	
	/**
	 * This method would use festival to pronounce the Word Object.
	 */
	public void sayWord(){
		_speech.say(this._word);
	}
	
	/**
	 * This method returns the string that should be passed to festival
	 * to spell the word out.
	 */
	public String StringToSpellWord(){
		String spelling = "";
		for (int charIndex=0; charIndex<_word.length(); charIndex++){
			String singleChar = _word.substring(charIndex, charIndex+1);
			//a is pronounced wrong. adjust.
			if (singleChar.equals("a")){
				singleChar = "ay";
			}
			spelling = spelling + singleChar + " ... ";
		}
		return spelling;
	}
	
	/**
	 * This method checks if the spelling of a word is correct.
	 * Note that the input of this method should have lower case letters only.
	 * @param spelling
	 * @return
	 */
	public boolean isSpeltCorrect(String spelling){
		if (spelling.equals(_word.toLowerCase())){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * These set of setters are intended to be called by the quiz
	 * class to set a word's successStatus after it's being spelt.
	 */
	public void setMastered(){
		_successStatus = Word.SuccessStatus.MASTERED;
	}
	public void setFaulted(){
		_successStatus = Word.SuccessStatus.FAULTED;
	}
	public void setFailed(){
		_successStatus = Word.SuccessStatus.FAILED;
	}
	
	/**
	 * Provide a getter for getting the sucessStatus of the word/
	 */
	public Word.SuccessStatus getSuccessStatus (){
		return _successStatus;
	}
	
	/**
	 * This method overrides the object#toString().
	 */
	public String toString(){
		return _word;
	}
}
