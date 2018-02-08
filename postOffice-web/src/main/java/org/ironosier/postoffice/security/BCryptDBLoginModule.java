package org.ironosier.postoffice.security;


import org.jboss.security.auth.spi.DatabaseServerLoginModule;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptDBLoginModule extends DatabaseServerLoginModule {

	@Override
	protected boolean validatePassword(String inputPass, String encryptedPass) {
		if (inputPass == null || encryptedPass == null) {
			return false;
		}
		return BCrypt.checkpw(inputPass, encryptedPass);
	}

}
