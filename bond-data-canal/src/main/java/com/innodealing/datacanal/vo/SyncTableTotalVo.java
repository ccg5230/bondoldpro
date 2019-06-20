package com.innodealing.datacanal.vo;

/**
 * 同步使用到的表数量信息VO
 * @author 在赵正来
 *
 */
public class SyncTableTotalVo{
	/**
	 * 计数column(目标表中的column)
	 */
	private Long keyColumn;
	
	/**
	 * 源表中的column
	 */
	private Long srcKeyColumn;
	
	/**
	 * 数量
	 */
	private Long total;

	public Long getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(Long keyColumn) {
		this.keyColumn = keyColumn;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getSrcKeyColumn() {
		return srcKeyColumn;
	}

	public void setSrcKeyColumn(Long srcKeyColumn) {
		this.srcKeyColumn = srcKeyColumn;
	}

	@Override
	public String toString() {
		return "SyncTableTotalVo [keyColumn=" + keyColumn + ", srcKeyColumn=" + srcKeyColumn + ", total=" + total + "]";
	}

	
	
}
