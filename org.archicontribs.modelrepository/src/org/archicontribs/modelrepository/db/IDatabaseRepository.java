package org.archicontribs.modelrepository.db;

import java.sql.Connection;
import java.util.UUID;

/**
 * Repository interface to access database and perform actions
 * 
 * @author Mohi Ghanbari
 */

public interface IDatabaseRepository {
	
	IDatabaseConfiguration connectionProperties = null;
	
	public Connection getConnection() throws Exception;
	
	public boolean validateConnection() throws Exception;
	
	public UUID InsertModel(UUID modelId, String modelName) throws Exception;
}
