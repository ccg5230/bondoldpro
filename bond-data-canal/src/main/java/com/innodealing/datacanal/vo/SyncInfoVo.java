package com.innodealing.datacanal.vo;

public class SyncInfoVo {
	private String srcTableName;
	private String srcKeyColumn;
	private String relationTable;
	private String relationDataCenterColum;
	private String destKeyColumn;
	private String destTableName;

	public String getSrcTableName() {
		return srcTableName;
	}

	public void setSrcTableName(String srcTableName) {
		this.srcTableName = srcTableName;
	}

	public String getSrcKeyColumn() {
		return srcKeyColumn;
	}

	public void setSrcKeyColumn(String srcKeyColumn) {
		this.srcKeyColumn = srcKeyColumn;
	}

	public String getRelationTable() {
		return relationTable;
	}

	public void setRelationTable(String relationTable) {
		this.relationTable = relationTable;
	}

	public String getRelationDataCenterColum() {
		return relationDataCenterColum;
	}

	public void setRelationDataCenterColum(String relationDataCenterColum) {
		this.relationDataCenterColum = relationDataCenterColum;
	}

	public String getDestKeyColumn() {
		return destKeyColumn;
	}

	public void setDestKeyColumn(String destKeyColumn) {
		this.destKeyColumn = destKeyColumn;
	}

	public String getDestTableName() {
		return destTableName;
	}

	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}

	@Override
	public String toString() {
		return "SyncInfoVo [srcTableName=" + srcTableName + ", srcKeyColumn=" + srcKeyColumn + ", relationTable="
				+ relationTable + ", relationDataCenterColum=" + relationDataCenterColum + ", destKeyColumn="
				+ destKeyColumn + ", destTableName=" + destTableName + "]";
	}

	public SyncInfoVo(String srcTableName, String srcKeyColumn, String relationTable, String relationDataCenterColum,
			String destKeyColumn, String destTableName) {
		super();
		this.srcTableName = srcTableName;
		this.srcKeyColumn = srcKeyColumn;
		this.relationTable = relationTable;
		this.relationDataCenterColum = relationDataCenterColum;
		this.destKeyColumn = destKeyColumn;
		this.destTableName = destTableName;
	}

	public SyncInfoVo() {
		super();
	}

}
