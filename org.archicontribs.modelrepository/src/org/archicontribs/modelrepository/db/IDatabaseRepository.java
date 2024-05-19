package org.archicontribs.modelrepository.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface to access database and perform actions
 * 
 * @author Mohi Ghanbari
 */

public interface IDatabaseRepository {
	
	IDatabaseConfiguration connectionProperties = null;
	
	public Connection GetConnection() throws Exception;
	
	public boolean ValidateConnection() throws Exception;
	
	public boolean IsModelExist(UUID modelId) throws SQLException;
	
	public UUID InsertModel(UUID modelId, String modelName, UUID userId) throws SQLException;
	
	public int ReviseModel(UUID modelId, int modelVersion, UUID userId, String description) throws SQLException;
	
	public void SaveElement(DatabaseElementEntity entity, UUID userId) throws SQLException;
	
	public void SaveElements(List<DatabaseElementEntity> entities, UUID userId) throws SQLException;
}
