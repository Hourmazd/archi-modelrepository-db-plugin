package org.archicontribs.modelrepository.db.sql;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.archicontribs.modelrepository.ModelRepositoryPlugin;
import org.archicontribs.modelrepository.authentication.internal.EncryptedCredentialsStorage;
import org.archicontribs.modelrepository.grafico.IGraficoConstants;
import org.archicontribs.modelrepository.preferences.IPreferenceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * SQL Server configuration class
 * 
 * @author Mohi Ghanbari
 */

public class SQLServerConfiguration implements ISQLServerConfiguration {

	public String ServerAddress;
	public int ServerPort;
	public String DatabaseName;
	public String UserName;
	public String Password;
	boolean encrypt;

	public SQLServerConfiguration() throws IOException, GeneralSecurityException {
		ServerAddress = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getString(IPreferenceConstants.PREFS_DATABASE_SERVER_ADDRESS);
		ServerPort = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getInt(IPreferenceConstants.PREFS_DATABASE_SERVER_PORT);
		DatabaseName = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getString(IPreferenceConstants.PREFS_DATABASE_NAME);
		UserName = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getString(IPreferenceConstants.PREFS_DATABASE_USER_NAME);

		EncryptedCredentialsStorage dbCredentials = getServerCredentials();
		Password = String.valueOf(dbCredentials.getPassword());
	}

	@Override
	public String getConnectionString() {

		String connectionString = "jdbc:sqlserver://" + ServerAddress + ":" + ServerPort + ";databaseName="
				+ DatabaseName + ";encrypt=" + encrypt + ";";

		return connectionString;
	}

	private EncryptedCredentialsStorage getServerCredentials() {
		return new EncryptedCredentialsStorage(new File(ModelRepositoryPlugin.INSTANCE.getUserModelRepositoryFolder(),
				IGraficoConstants.DB_CREDENTIALS_FILE));
	}
}
