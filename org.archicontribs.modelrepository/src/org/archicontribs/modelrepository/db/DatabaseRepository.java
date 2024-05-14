package org.archicontribs.modelrepository.db;

/**
 * Repository class to access database and perform actions
 * 
 * @author Mohi Ghanbari
 */

public abstract class DatabaseRepository implements IDatabaseRepository {

	protected IDatabaseConfiguration connectionProperties = null;
	
	public DatabaseRepository(IDatabaseConfiguration connectionProperties)
	{
		this.connectionProperties = connectionProperties;
	}
}
