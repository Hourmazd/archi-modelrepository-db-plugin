package org.archicontribs.modelrepository.db.sql;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.archicontribs.modelrepository.db.DatabaseRepository;

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
	public boolean validateConnection() throws Exception {

		// Open a connection
		System.out.println("Connecting to database...");

		Connection conn = getConnection();

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
	public UUID InsertModel(UUID modelId, String modelName) throws Exception {

		String sql = "{call InsertNewModel(?, ?)}";

		try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

			// Set the input parameter for ModelId
			stmt.setObject(1, modelId);

			// Set the input parameter for ModelName
			stmt.setString(2, modelName.trim());

			// Execute the stored procedure
			stmt.execute();

			return modelId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Connection getConnection() throws Exception {
		
		var sqlServerProperties = (SQLServerConfiguration) super.connectionProperties;
		
		Connection conn = DriverManager.getConnection(sqlServerProperties.getConnectionString(),
				sqlServerProperties.UserName, sqlServerProperties.Password);
		
		return conn;
	}
}
