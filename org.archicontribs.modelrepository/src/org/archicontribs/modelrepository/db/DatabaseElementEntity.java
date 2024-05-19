package org.archicontribs.modelrepository.db;

import java.util.UUID;

public class DatabaseElementEntity {

	public UUID Id;
	public UUID ModelId;
	public int ModelVersion;
	public UUID ParentId;
	public String Type;
	public String XmlContent;
	public boolean IsDeleted;
}
