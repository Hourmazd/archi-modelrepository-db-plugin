package org.archicontribs.modelrepository.db.sql;

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

	@Override
	public String getConnectionString() {

		String connectionString = "jdbc:sqlserver://" + ServerAddress + ":" + ServerPort + ";databaseName="
				+ DatabaseName + ";encrypt=" + encrypt + ";";

		return connectionString;
	}
}
