package com.innodealing.bond.service.rrs;

public interface IDocEngine {

	BondDocLayout createLayout();

	void init(Long issuerId, Long year, Long quarter);

	void init(Long issuerId, Long firstDate, Long secondDate, boolean isComparison);

	BondDocField crtRatField(String desc, String col,
		IFieldFormatter format);
	
	BondDocField crtRatField(String desc, String col,
		IFieldFormatter format, IValuePreprocessor preprocessor);

	BondDocField crtFinField(String desc, String col,
		IFieldFormatter format);
	
	BondDocField crtFinField(String desc, String col,
			IFieldFormatter format, IValuePreprocessor preprocessor);

	BondDocField crtTxtField(String desc, String col, IFieldFormatter format);

	BondDocField getField(String col);
	void loadData();
	
	String getIssuer() ;
	String getPreQuarter() ;
	String getLastQuarter() ;

	//note: issuerId在私报的时候填写taskId
	Integer findQuantileByIndu(Long issuerId, Long userId, String ratio, Long yearmonth) ;
	Integer countRatioPositionByIndu(Long issuerId, Long userId, Long yearmonth) ;
	Integer findRatioPositionByIndu(Long issuerId, Long userId, String ratio, Long yearmonth) ;
	String findIndu(Long userId, Long issuerId);
}
