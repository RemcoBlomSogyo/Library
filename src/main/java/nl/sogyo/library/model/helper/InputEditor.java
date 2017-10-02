package nl.sogyo.library.model.helper;

public class InputEditor {
	
	public static String addEscapeCharacters(String input) {
		return input.replace("\'", "\'\'");
	}
}
