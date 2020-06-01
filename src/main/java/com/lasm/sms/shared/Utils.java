package com.lasm.sms.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	private final Random random = new SecureRandom();
	private final String STR_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890$-@";

	public String generateRandomId(int lenght) {
		
		StringBuilder returnerdValue = new StringBuilder();
		
		for (int i = 0; i < lenght; i++) {
			returnerdValue.append(STR_CHARACTERS.charAt(random.nextInt(STR_CHARACTERS.length())));
		}

		return new String(returnerdValue);
	}

}
