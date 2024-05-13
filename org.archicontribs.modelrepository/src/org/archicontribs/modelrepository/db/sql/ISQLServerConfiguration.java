package org.archicontribs.modelrepository.db.sql;

import org.archicontribs.modelrepository.db.IDatabaseConfiguration;

/**
 * SQL Server configuration interface
 * 
 * @author Mohi Ghanbari
 */

public interface ISQLServerConfiguration extends IDatabaseConfiguration {
	
	String getConnectionString();
}
