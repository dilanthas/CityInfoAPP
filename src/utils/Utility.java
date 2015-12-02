package utils;

import java.util.List;

public class Utility {

	public static String joinList(List list) {
		return list.toString().replaceAll("[\\[.\\].\\s+]", "");
	}
}

