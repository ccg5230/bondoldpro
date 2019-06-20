package com.innodealing.model.mongo.dm;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_cred_rating") 
public class BondCredRatingDoc extends BasicCredRatingDoc{
	
	@ApiModelProperty(value = "债券id")
	@Indexed
    private Long bondUniCode;

	@ApiModelProperty(value = "债券名称")
	private String bondShortName;

	/**
	 * @return the bondUniCode
	 */
	public Long getBondUniCode() {
		return bondUniCode;
	}

	/**
	 * @param bondUniCode the bondUniCode to set
	 */
	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	/**
	 * @return the bondShortName
	 */
	public String getBondShortName() {
		return bondShortName;
	}

	/**
	 * @param bondShortName the bondShortName to set
	 */
	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondCredRatingDoc [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (bondShortName != null ? "bondShortName=" + bondShortName : "") + "]";
    }

	public BondCredRatingDoc() {
	}
    
}
