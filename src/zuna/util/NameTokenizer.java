package zuna.util;

import uk.ac.open.crc.intt.IdentifierNameTokeniser;
import uk.ac.open.crc.intt.IdentifierNameTokeniserFactory;

public class NameTokenizer {
	public static String[] tokenizer(String className) {
		IdentifierNameTokeniserFactory factory = new IdentifierNameTokeniserFactory();
		IdentifierNameTokeniser tokeniser = factory.create();

		// String className = "AbstractApplication";
		String[] tokens;
		tokens = tokeniser.tokenise(className);

		// for (int i = 0; i < tokens.length; i++) {
		// System.out.print(tokens[i]);
		// if (i < tokens.length - 1) {
		// System.out.print(" ");
		// }
		// }

		return tokens;
	}

	//
//	public static void main(String[] args) {
//
//		IdentifierNameTokeniserFactory factory = new IdentifierNameTokeniserFactory();
//
//		IdentifierNameTokeniser tokeniser = factory.create();
//
//		String line = "AbstractApplicationTestTest";
//		String[] tokens;
//		tokens = tokeniser.tokenise(line);
//
//		for (int i = 0; i < tokens.length; i++) {
//			System.out.print(tokens[i]);
//			if (i < tokens.length - 1) {
//				System.out.print(" ");
//			}
//		}
//		System.out.println();
//	}

}
