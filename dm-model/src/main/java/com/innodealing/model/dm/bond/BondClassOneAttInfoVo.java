/**
 * BondClassOneAttInfoVo.java
 * com.innodealing.model.dm.bond
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年11月15日 		chungaochen
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.model.dm.bond;
/**
 * ClassName:BondClassOneAttInfoVo:前端新债相关文件Vo
 * Function: 
 * Reason:	 
 *
 * @author   chungaochen
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年11月15日		下午2:24:21
 *
 * @see 	 
 */

import java.util.List;

public class BondClassOneAttInfoVo {
    
    private Long bondUniCode;
    
    List<BondClassOneAnnAttInfo> raiseList;
    
    List<BondClassOneAnnAttInfo> applyPurchaseList;
    
    List<BondClassOneAnnAttInfo> otherList;

    public List<BondClassOneAnnAttInfo> getRaiseList() {
        return raiseList;
    }

    public void setRaiseList(List<BondClassOneAnnAttInfo> raiseList) {
        this.raiseList = raiseList;
    }

    public List<BondClassOneAnnAttInfo> getApplyPurchaseList() {
        return applyPurchaseList;
    }

    public void setApplyPurchaseList(List<BondClassOneAnnAttInfo> applyPurchaseList) {
        this.applyPurchaseList = applyPurchaseList;
    }

    public List<BondClassOneAnnAttInfo> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<BondClassOneAnnAttInfo> otherList) {
        this.otherList = otherList;
    }

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }
    
    
    

}

