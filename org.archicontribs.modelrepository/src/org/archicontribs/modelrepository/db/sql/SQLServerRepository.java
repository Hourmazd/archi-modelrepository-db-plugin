package org.archicontribs.modelrepository.db.sql;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.archicontribs.modelrepository.db.DatabaseElementEntity;
import org.archicontribs.modelrepository.db.DatabaseRepository;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;

/**
 * Repository class to access SQL Server database and perform actions
 * 
 * @author Mohi Ghanbari
 */

public class SQLServerRepository extends DatabaseRepository implements ISQLServerRepository {

	public SQLServerRepository() throws IOException, GeneralSecurityException {
		super(new SQLServerConfiguration());

		try {

			// Register JDBC driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public SQLServerRepository(ISQLServerConfiguration connectionProperties) {
		super(connectionProperties);
		
		try {

			// Register JDBC driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection GetConnection() throws SQLException {
		
		var sqlServerProperties = (SQLServerConfiguration) super.connectionProperties;
		
		Connection conn = DriverManager.getConnection(sqlServerProperties.getConnectionString(),
				sqlServerProperties.UserName, sqlServerProperties.Password);
		
		return conn;
	}

	@Override
	public boolean ValidateConnection() throws Exception {

		// Open a connection
		System.out.println("Connecting to database...");

		Connection conn = GetConnection();

		// Check if connection is successful
		if (conn != null) {
			System.out.println("Connected to the database!");
			conn.close();
			System.out.println("Disconnected from database.");
			return true;
		} else {
			System.out.println("Failed to make connection!");
			return false;
		}
	}

	@Override
	public UUID InsertModel(UUID modelId, String modelName, UUID userId) throws SQLException {
		
		if(IsModelExist(modelId)) {
			throw new SQLException("The model is already exist in the database.");
		}

		String sql = "{call InsertNewModel(?, ?, ?)}";

		try (Connection conn = GetConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

			// Set the input parameter for ModelId
			stmt.setObject(1, modelId);

			// Set the input parameter for ModelName
			stmt.setString(2, modelName.trim());
			
			// Set the input parameter for userId
			stmt.setObject(3, userId);

			// Execute the stored procedure
			stmt.execute();

			return modelId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean IsModelExist(UUID modelId) throws SQLException {

		String query = "SELECT COUNT(*) AS total FROM [dbo].[Models] WHERE [Id] = ?";

		try (Connection connection = GetConnection();
				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Set the value for the placeholder in the SQL query
			preparedStatement.setObject(1, modelId);

			// Execute the query, and get a java result set
			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;
			// Process the result set
			while (resultSet.next()) {
				count = resultSet.getInt("total");
			}

			return count > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int ReviseModel(UUID modelId, int modelVersion, UUID userId, String description) throws SQLException {

		String sql = "{call ReviseModel(?, ?, ?, ?)}";

		try (Connection conn = GetConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

			int newModelVersion = -1;
			
			// Set the input parameters
			stmt.setObject(1, modelId);
			stmt.setInt(2, modelVersion);
			stmt.setObject(3, userId);
			stmt.setString(4, description);

			// Execute the stored procedure
			stmt.execute();

			return newModelVersion;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void SaveElement(DatabaseElementEntity entity, UUID userId) throws SQLException {

		String sql = "{call SaveModelElement(?, ?, ?, ?, ?, ?, ?, ?)}";

		try (Connection conn = GetConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

			// Set the input parameters
			stmt.setObject(1, entity.ModelId);
			stmt.setInt(2, entity.ModelVersion);
			stmt.setObject(3, entity.Id);
			stmt.setObject(4, entity.ParentId);
			stmt.setString(5, entity.Type);
			stmt.setString(6, entity.XmlContent);
			stmt.setObject(7, userId);
			stmt.setBoolean(8, entity.IsDeleted);

			// Execute the stored procedure
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}	
	}

	@Override
	public void SaveElements(List<DatabaseElementEntity> entities, UUID userId) throws SQLException {
		
		String sql = "{call SaveModelElements(?, ?)}";

		try (Connection conn = GetConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

			SQLServerDataTable tvp = new SQLServerDataTable();

            // Add the columns to the TVP
			tvp.addColumnMetadata("Id", java.sql.Types.CHAR);
            tvp.addColumnMetadata("ModelId", java.sql.Types.CHAR);
            tvp.addColumnMetadata("ModelVersion", java.sql.Types.INTEGER);
            tvp.addColumnMetadata("ParentId", java.sql.Types.CHAR);
            tvp.addColumnMetadata("ElementType", java.sql.Types.NVARCHAR);
            tvp.addColumnMetadata("ElementContent", java.sql.Types.SQLXML);

         // Add the rows to the TVP
            for (var entity : entities) {
                tvp.addRow(entity.Id, entity.ModelId, entity.ModelVersion, entity.ParentId, entity.Type, entity.XmlContent);
            }
            
            // Set parameters
            stmt.setObject(1, tvp);
            stmt.setObject(2, userId);

			// Execute the stored procedure
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}	
		
	}
}
