package org.archicontribs.modelrepository.db;

import java.util.UUID;

import org.archicontribs.modelrepository.grafico.GraficoUtils;
import org.eclipse.emf.ecore.EObject;

public class DBHelper {
	
	public static UUID getActiveUserId() {
		return UUID.fromString("d9b2d63d-a233-4123-847e-3a3f0f5d9152");
	}
	
	/*
	 * public static UUID convertModelIdToUuid(String modelId) {
	 * 
	 * var uuidString = modelId.substring(3);
	 * 
	 * // Insert hyphens at appropriate positions StringBuilder uuidBuilder = new
	 * StringBuilder(uuidString); uuidBuilder.insert(20, '-'); // after 8 characters
	 * uuidBuilder.insert(16, '-'); // after 12 characters uuidBuilder.insert(12,
	 * '-'); // after 16 characters uuidBuilder.insert(8, '-'); // after 20
	 * characters
	 * 
	 * // Parse the UUID string return UUID.fromString(uuidBuilder.toString()); }
	 * 
	 * public static String convertUuidToModelId(UUID id) {
	 * 
	 * // Convert UUID to string without hyphens String uuidWithoutHyphens =
	 * id.toString().replace("-", "");
	 * 
	 * // Concatenate "id-" with UUID without hyphens return "id-" +
	 * uuidWithoutHyphens; }
	 */
	
	public static DatabaseElementEntity GetElementClassFromEObject(EObject eObject) throws Exception {
		
		DatabaseElementEntity entity = null;
			
		entity = new DatabaseElementEntity();
		entity.Id = GraficoUtils.GetUuidFromElementlId(GraficoUtils.GetFieldValueOfEObject(eObject, "id").toString());
		entity.Type = eObject.eClass().getInstanceTypeName();

		return entity;
	}
}
