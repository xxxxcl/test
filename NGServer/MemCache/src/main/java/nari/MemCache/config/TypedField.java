package nari.MemCache.config;

import nari.Xml.bundle.annotation.XmlAttribute;


public class TypedField {

	@XmlAttribute(name="name")
	private String fieldName;
	
	@XmlAttribute(name="type")
	private String fieldType;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
}
