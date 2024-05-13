package org.archicontribs.modelrepository.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;

import org.archicontribs.modelrepository.db.DatabaseRepository;

/**
 * Repository class to access SQL Server database and perform actions
 * 
 * @author Mohi Ghanbari
 */

public class SQLServerRepository extends DatabaseRepository implements ISQLServerRepository {

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

		var sqlServerProperties = (SQLServerConfiguration) super.connectionProperties;

		// Open a connection
		System.out.println("Connecting to database...");

		Connection conn = DriverManager.getConnection(sqlServerProperties.getConnectionString(),
				sqlServerProperties.UserName, sqlServerProperties.Password);

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
}
