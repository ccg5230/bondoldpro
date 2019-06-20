package com.innodealing.model.mongo.dm;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="iss_cred_rating") 
public class IssCredRatingDoc extends BasicCredRatingDoc {

	@ApiModelProperty(value = "债券主体id")
	@Indexed
	private Long comUniCode;

	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IssCredRatingDoc [" + (comUniCode != null ? "comUniCode=" + comUniCode : "") + "]";
    }

	public IssCredRatingDoc() {
	}
    
}
