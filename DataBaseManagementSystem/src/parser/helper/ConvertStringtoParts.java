package parser.helper;

import java.util.ArrayList;

public class ConvertStringtoParts {

	private ConvertStringtoParts () {
		
	}
	
	public static ArrayList<String> changeToParts(String takenSyntax) {
		StringBuilder build = new StringBuilder();
		ArrayList<String> parts = new ArrayList<String>();
		for (int index = 0; index < takenSyntax.length(); index++) {
			if (takenSyntax.charAt(index) == ' ' || takenSyntax.charAt(index) == '\n') {
				if (build.length() != 0) {
					parts.add(build.toString());
					build = new StringBuilder();
				}
			} else if ("()\\\",/<>'!*;=*".contains(String.valueOf(takenSyntax.charAt(index)))) {
				if (build.length() != 0) {
					parts.add(build.toString());
					build = new StringBuilder();
				}
				build.append(takenSyntax.charAt(index));
				parts.add(build.toString());
				build = new StringBuilder();
			} else
				build.append(takenSyntax.charAt(index));
		}
		if (build.length() != 0)
			parts.add(build.toString());
		return parts;
	}

}
