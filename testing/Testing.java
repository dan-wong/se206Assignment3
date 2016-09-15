package testing;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import spellingAid.FileManager;
import spellingAid.NewQuiz;
import spellingAid.Quiz;
import spellingAid.ReviewQuiz;
import spellingAid.Speech;
import spellingAid.Statistics;
import spellingAid.Word;
import spellingAid.WordList;

//gitCheck!
public class Testing {
	/**
	 * Testing the fileManager reads in the wordList properly.
	 */
	@Test
	public void testwordList(){
		FileManager fm = new FileManager();
		List<List<String>> allList = fm.readWordList();
		assertEquals(allList.size(), 12);


		List<String> level0 = allList.get(0);
		assertEquals(level0.size(),0);

		List<String> level1 = allList.get(1);
		assertEquals(level1.get(0), "a");
		assertEquals(level1.get(level1.size()-1), "we");

		List<String> level11 = allList.get(11);
		assertEquals(level11.get(0),"acceptance");
		assertEquals(level11.get(level11.size()-1), "wealth");

	}

	/**
	 * Testing the generate random words work correctly.
	 */
	@Test
	public void testGenerateRandomWords(){
		WordList list = new WordList(NewQuiz.getInstance(null,1));
		try {
			list.generateRandomWords(12, 10);
		}
		catch (RuntimeException rex){
			assertEquals(rex.getMessage(),"Invalid level");
		}

		try {
			list.generateRandomWords(1, 12);
		}
		catch (RuntimeException rex){
			assertEquals(rex.getMessage(),"Not enough words in list");
		}

		int level = 3;
		List<Word> wordlist= list.generateRandomWords(level, 3);
		for (Word w: wordlist){
			System.out.println("Please check manually: random words from level " + level);
			System.out.println(w.toString());
		}
	}

	/**
	 * Testing the speech functions.
	 */
	@Test
	public void testSpeech(){
		Speech speech = new Speech();
		Word word = new Word("University");

		System.out.println("Should say: "+ word.toString());
		speech.say(word.toString());

		String toSay = "Welcome to Spelling Aid";
		System.out.println("Should say: " + toSay);
		speech.say(toSay);
	}
	
	/**
	 * Testing the wordList class's review methods.
	 */
	@Test
	public void testReviewList(){
		Statistics stats = Statistics.getInstance();
		//add some failed words:
		for (int i=1; i<=11; i++){
			List<Word> failedList = new ArrayList<Word>();
			for (int fail=0; fail<3; fail++){
				Word word = new Word ("fail");
				word.setFailed();
				failedList.add(word);
			}
			stats.recordQuizResults(failedList, i);
		}
		
		Quiz review = ReviewQuiz.getInstance(null, 1);
		//Debug point above. To observe relative fields. Need to change stats line 89.
		String s = review.NL;
	}
}
