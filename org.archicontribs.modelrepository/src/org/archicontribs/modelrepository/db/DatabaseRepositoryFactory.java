package org.archicontribs.modelrepository.db;

import org.archicontribs.modelrepository.ModelRepositoryPlugin;
import org.archicontribs.modelrepository.db.sql.SQLServerRepository;
import org.archicontribs.modelrepository.preferences.IPreferenceConstants;
import org.archicontribs.modelrepository.preferences.Messages;

public class DatabaseRepositoryFactory {

	public static IDatabaseRepository getDbRepository() throws Exception {
		IDatabaseRepository result = null;
		var dbEngine = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getString(IPreferenceConstants.PREFS_DATABASE_ENGINE);

		if (dbEngine.equals(Messages.ModelRepositoryPreferencePage_28)) {
			result = new SQLServerRepository();
		} else {
			throw new Exception("No database repository class found to support " + dbEngine + ".");
		}

		return result;
	}
}
