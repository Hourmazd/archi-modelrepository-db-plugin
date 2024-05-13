package org.archicontribs.modelrepository.db;

/**
 * Repository interface to access database and perform actions
 * 
 * @author Mohi Ghanbari
 */

public interface IDatabaseRepository {
	
	IDatabaseConfiguration connectionProperties = null;
	
	public boolean validateConnection() throws Exception ;
}
