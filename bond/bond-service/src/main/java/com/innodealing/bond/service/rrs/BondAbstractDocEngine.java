package com.innodealing.bond.service.rrs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.bond.service.BondComExtService;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.engine.jpa.dm.BondComExtRepository;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.util.SafeUtils;

@Service("BondAbstractDocEngine")
@Scope(value="prototype")
public abstract class BondAbstractDocEngine {

	private static final Logger LOG = LoggerFactory.getLogger(BondAbstractDocEngine.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected BondComExtService comExtService;

    @Autowired
    protected BondInduService induService;

    @Autowired
    protected BondComInfoRepository comInfoRepository;

    @Autowired
    protected BondComExtRepository comExtRepository;
    
	protected Map<String, List<BondDocField>> col2fieldsMap = new HashMap<>();
	
	protected Long issuerId;
	protected String issuer;
	protected Integer compClas;
	protected BondDocTableSchema rTable;
	protected BondDocTableSchema fTable;
	protected BondDocTableSchema aTable;
	protected String preQuarter; //从数据库中取得的连续两期财报中的上一期
	protected String lastQuarter; //从数据库中取得的连续两期财报中的最近一期
	protected Long selectedQuarter; //用户选择的财报期限 年+季度，例如 201703 201706 201709 201712
	protected boolean isComparison;
	protected Long firstYM;
	protected Long secondYM;
	
    protected abstract BondDocTableSchema makeRatioTableMeta(Long issuerId);
    protected abstract BondDocTableSchema makeFinTableMeta(Long issuerId);
    protected abstract BondDocTableSchema makeAnaTableMeta(Long issuerId);
    
	public String getIssuer() {
		return issuer;
	}

	public String getPreQuarter() {
		return preQuarter;
	}

	public String getLastQuarter() {
		return lastQuarter;
	}

	public BondDocLayout createLayout()
	{
		BondDocLayout layout = new BondDocLayout();
		layout.setTitle(String.format("将%s和%s财报的关键指标计算且对比后得出：", lastQuarter, preQuarter));
		return layout;
	}

	public void init(Long issuerId, Long year, Long quarter) {
		this.issuerId = issuerId;
		rTable = makeRatioTableMeta(issuerId);
		fTable = makeFinTableMeta(issuerId);
		aTable = makeAnaTableMeta(issuerId);
		if (year != null && quarter != null)
			selectedQuarter = year*100 + quarter*3; //输入是1~4, 对应四个季度
	}

	public void init(Long issuerId, Long firstYM, Long secondYM, boolean isComparison) {
		this.issuerId = issuerId;
		this.rTable = makeRatioTableMeta(issuerId);
		this.fTable = makeFinTableMeta(issuerId);
		this.aTable = makeAnaTableMeta(issuerId);
		this.firstYM = firstYM;
		this.secondYM = secondYM;
		this.isComparison = isComparison;
	}

	public BondDocField crtRatField(String ratioName, String ratioKey, IFieldFormatter format) {
		BondDocField field = new BondDocField();
		field.setColDesc(ratioName);
		field.setColName(ratioKey);
		field.setFormatter(format);
		rTable.getFields().put(ratioKey, ratioName);
		if (!col2fieldsMap.containsKey(ratioKey)) {
			col2fieldsMap.put(ratioKey, new ArrayList<>());
		}
		List<BondDocField> fset = col2fieldsMap.get(ratioKey);
		fset.add(field);
		return field;
	}
	
	public BondDocField crtRatField(String desc, String col, 
			IFieldFormatter format, IValuePreprocessor preprocessor)
	{
		BondDocField f = crtRatField(desc, col, format);
		f.setValPreProc(preprocessor);
		return f;
	}

	public BondDocField crtFinField(String desc, String col, 
			IFieldFormatter format)
	{
		BondDocField field = new BondDocField();
		field.setColDesc(desc);
		field.setColName(col);
		field.setFormatter(format);
		fTable.getFields().put(col, desc);
		if (!col2fieldsMap.containsKey(col)) {
			col2fieldsMap.put(col, new ArrayList<>());
		}
		List<BondDocField> fset = col2fieldsMap.get(col);
		fset.add(field);
		return field;
	}
	
	public BondDocField crtFinField(String desc, String col, 
			IFieldFormatter format, IValuePreprocessor preprocessor)
	{
		BondDocField f = crtFinField(desc, col, format);
		f.setValPreProc(preprocessor);
		return f;
	}

	public BondDocField crtTxtField(String desc, String col, IFieldFormatter format)
	{
		BondDocField field = new BondDocField();
		field.setColDesc(desc);
		field.setColName(col);
		field.setFormatter(format);
		if (!col2fieldsMap.containsKey(col)) {
			col2fieldsMap.put(col, new ArrayList<>());
		}
		List<BondDocField> fset = col2fieldsMap.get(col);
		fset.add(field);
		return field;
	}

	public BondDocField getField(String col)
	{
		List<BondDocField> fset = col2fieldsMap.get(col);
		if (fset.isEmpty()) return null;
		return fset.get(0);
	}

}
