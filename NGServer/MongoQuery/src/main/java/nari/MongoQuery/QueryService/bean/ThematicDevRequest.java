package nari.MongoQuery.QueryService.bean;

public class ThematicDevRequest {

	//MongoQueryService/thematicDev?input="{"mapId":"","documentId":""}"
	
	//图类型
	private String mapId = "";
	
	//图实例
	private String documentId = "";
	

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
}