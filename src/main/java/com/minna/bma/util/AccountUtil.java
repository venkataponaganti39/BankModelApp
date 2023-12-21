/**
 * 
 */
package com.minna.bma.util;

import java.util.Random;

public class AccountUtil {
	public static String generateCreditCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);

            if ((i + 1) % 4 == 0 && i < 15) {
                sb.append("-");
            }
        }

        return sb.toString();
    }

}
