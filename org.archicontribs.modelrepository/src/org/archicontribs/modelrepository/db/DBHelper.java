package org.archicontribs.modelrepository.db;

import java.util.UUID;

public class DBHelper {
	
	public static UUID convertModelIdToUuid(String modelId) {
		
		var uuidString = modelId.substring(3);
    	
    	// Insert hyphens at appropriate positions
        StringBuilder uuidBuilder = new StringBuilder(uuidString);
        uuidBuilder.insert(20, '-'); // after 8 characters
        uuidBuilder.insert(16, '-'); // after 12 characters
        uuidBuilder.insert(12, '-'); // after 16 characters
        uuidBuilder.insert(8, '-');  // after 20 characters

        // Parse the UUID string
        return UUID.fromString(uuidBuilder.toString());
	}
	
	public static String convertUuidToModelId(UUID id) {
		
		// Convert UUID to string without hyphens
        String uuidWithoutHyphens = id.toString().replace("-", "");

        // Concatenate "id-" with UUID without hyphens
        return "id-" + uuidWithoutHyphens;
	}
}
