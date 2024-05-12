/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.modelrepository.preferences;




/**
 * Constant definitions for plug-in preferences
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public interface IPreferenceConstants {
    
	String PREFS_DATABASE_ENGINE = "databaseEngine";
	String PREFS_DATABASE_SERVER_ADDRESS = "serverAddress";
	String PREFS_DATABASE_SERVER_PORT = "serverPort";
	String PREFS_DATABASE_NAME = "Archi";
	
	String PREFS_DATABASE_USER_NAME = "sa";
    String PREFS_DATABASE_USER_PASS = "databaseUserPass";
    
    String PREFS_REPOSITORY_FOLDER = "repoFolder";
    String PREFS_FETCH_IN_BACKGROUND = "fetchInBackground";
    String PREFS_FETCH_IN_BACKGROUND_INTERVAL = "fetchInBackgroundInterval";
    
    String PREFS_EXPORT_MAX_THREADS = "exportMaxThreads";    
    
    /*
       Password constraints
    
       Can be set in plugin_customization.ini as:
          org.archicontribs.modelrepository/passwordMinLength=10
          org.archicontribs.modelrepository/passwordMinLowerCase=2
          org.archicontribs.modelrepository/passwordMinUpperCase=2
          org.archicontribs.modelrepository/passwordMinDigits=2
          org.archicontribs.modelrepository/passwordMinSpecialChars=2
    */
    
    String PREFS_PASSWORD_MIN_LENGTH = "passwordMinLength";
    String PREFS_PASSWORD_MIN_LOWERCASE_CHARS = "passwordMinLowerCase";
    String PREFS_PASSWORD_MIN_UPPERCASE_CHARS = "passwordMinUpperCase";
    String PREFS_PASSWORD_MIN_DIGITS = "passwordMinDigits";
    String PREFS_PASSWORD_MIN_SPECIAL_CHARS = "passwordMinSpecialChars";
    
    /*
      Password timeouts
      
      Can be set in plugin_customization.ini as minutes:
         org.archicontribs.modelrepository/passwordPrimaryTimeout=10
         org.archicontribs.modelrepository/passwordInactivityTimeout=10
     */
    
    String PREFS_PRIMARY_PASSWORD_TIMEOUT = "passwordPrimaryTimeout";
    String PREFS_PASSWORD_INACTIVITY_TIMEOUT = "passwordInactivityTimeout";
    
    String PREFS_STORE_REPO_CREDENTIALS = "storeCredentials";
    String PREFS_SSH_IDENTITY_FILE = "sshIdentityFile";
    String PREFS_SSH_IDENTITY_REQUIRES_PASSWORD = "sshIdentityRequiresPassword";
    String PREFS_SCAN_REPOSITORY_FOLDER = "scanRepoFolder";
 }
