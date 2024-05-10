/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.modelrepository.preferences;

import java.io.File;

import org.archicontribs.modelrepository.ModelRepositoryPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.archimatetool.editor.ArchiPlugin;



/**
 * Class used to initialize default preference values
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class PreferenceInitializer extends AbstractPreferenceInitializer
implements IPreferenceConstants {

    @Override
    public void initializeDefaultPreferences() {
		IPreferenceStore store = ModelRepositoryPlugin.INSTANCE.getPreferenceStore();
        
		store.setDefault(PREFS_DATABASE_ENGINE, Messages.ModelRepositoryPreferencePage_28);
		store.setDefault(PREFS_DATABASE_SERVER_ADDRESS, "localhost");
		store.setDefault(PREFS_DATABASE_SERVER_PORT, "1433");
		
		store.setDefault(PREFS_DATABASE_USER_NAME, "sa");

		store.setDefault(PREFS_REPOSITORY_FOLDER, new File(ArchiPlugin.INSTANCE.getUserDataFolder(), "model-repository").getAbsolutePath());
		store.setDefault(PREFS_FETCH_IN_BACKGROUND, false);
		store.setDefault(PREFS_FETCH_IN_BACKGROUND_INTERVAL, 60);
		
		store.setDefault(PREFS_EXPORT_MAX_THREADS, 10);
		
		store.setDefault(PREFS_PASSWORD_MIN_LENGTH, 0);
		store.setDefault(PREFS_PASSWORD_MIN_LOWERCASE_CHARS, 0);
		store.setDefault(PREFS_PASSWORD_MIN_UPPERCASE_CHARS, 0);
		store.setDefault(PREFS_PASSWORD_MIN_DIGITS, 0);
		store.setDefault(PREFS_PASSWORD_MIN_SPECIAL_CHARS, 0);
    }
}
