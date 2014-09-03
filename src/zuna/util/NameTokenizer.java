package zuna.util;

import uk.ac.open.crc.intt.IdentifierNameTokeniser;
import uk.ac.open.crc.intt.IdentifierNameTokeniserFactory;

public class NameTokenizer {
	public static String[] tokenizer(String className) {
		IdentifierNameTokeniserFactory factory = new IdentifierNameTokeniserFactory();
		IdentifierNameTokeniser tokeniser = factory.create();
		String[] tokens;
		tokens = tokeniser.tokenise(className);
		return tokens;
	}

}
