package de.throwstnt.developing.labymod.cvc.api.util;

import java.util.UUID;

public class UUIDUtil {

	public static String convert(UUID uuid) {
		return uuid.toString().replace("-", "");
	}
}
