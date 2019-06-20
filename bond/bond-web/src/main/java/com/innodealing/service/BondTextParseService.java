package com.innodealing.service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.innodealing.bond.vo.favorite.BondParseVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.bond.vo.msg.BasicBondVo;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;

@Service
public class BondTextParseService {

	private static final Logger LOG = LoggerFactory.getLogger(BondTextParseService.class);
	
    private @Autowired BondBasicInfoRepository bondBasicInfoRepository;
    private final String[] defaultPostfixs = { "", ".SZ", ".IB", ".SH" };

    private static boolean hasMarketPostfix(String bondCode)
    {
    	return bondCode.matches("(?i).*?(\\.sz|\\.ib|\\.sh)$");
    }
    
    public BondParseVO parseBondText(Long userId, String content) {
    	// 前端传入参数为一个json，非字符串列表，所以需要先提取真实数据
        final JSONObject obj = new JSONObject(content);
        final String realContent = obj.getString("content");
        
        Map<Long, BasicBondVo> map = new HashMap<>();
        List<String> briefBondCodeList = this.parseBondCodeListText(realContent);
        // 保存最终解析成功的bondCode，用于从source中分类
        List<String> validBondCodeList = new ArrayList<>();

        briefBondCodeList.stream().forEach(bondCode -> {
            final String validBondCode = bondCode.endsWith(",") ? bondCode.substring(0, bondCode.length() - 1) : bondCode;
            if (hasMarketPostfix(validBondCode.toLowerCase())) {
                if (this.attemptToAddBondByCode(validBondCode.toUpperCase(), map)
                        && !validBondCodeList.contains(bondCode)) {
                    validBondCodeList.add(bondCode);
                }
            } else {
                Arrays.stream(defaultPostfixs).forEach(bondCodeExt -> {
                    if (this.attemptToAddBondByCode(validBondCode.concat(bondCodeExt), map)
                            && !validBondCodeList.contains(bondCode)) {
                        validBondCodeList.add(bondCode);
                    }
                });
            }
        });
        Set<String> allContentSet = new LinkedHashSet<>(Arrays.asList(
                realContent.replaceAll(",", ", ").replaceAll("\\s+"," ").split(" ")));
        validBondCodeList.stream().forEach(code -> allContentSet.remove(code));
        allContentSet.remove(",");
        BondParseVO bondParseVO = new BondParseVO();
        bondParseVO.setSucceedList(new ArrayList<>(map.values()));
        bondParseVO.setFailedList(new ArrayList<>(allContentSet));
        return bondParseVO;
    }

    private static List<String> parseBondCodeListText(String bondCodeListText) {
        LOG.info("bondCodeListText:" + bondCodeListText);
        List<String> results = new ArrayList<>();

        //http://stackoverflow.com/questions/2824302/how-to-make-regular-expression-into-non-greedy
        //use possessive match
        //Pattern p = Pattern.compile("(\\d+)(.\\p{Alpha}*+)?+"); //OK1
        Pattern p = Pattern.compile("(?i)([0-9]+)(.(sz|ib|sh)*+)?+"); //OK2
        //Pattern p = Pattern.compile("(?i)((([0-9]+)(.(sz|ib|sh)*+)?+)(\\s|\\n)*+)*"); //failed todo
        Matcher m = p.matcher(bondCodeListText);
        while (m.find()) {
            if (!m.group().isEmpty()) {
                String bondCode = m.group().trim();
                if(!bondCode.endsWith(".")) {
                    results.add(bondCode);
                    LOG.info("group:"+ bondCode);
                }
                //for(int i = 0; i < m.groupCount(); ++i)
                //System.out.println("group[" + i + "]:" + m.group(i));
            }
        }
        return results;
    }

    private Boolean attemptToAddBondByCode(String bondCode, Map<Long, BasicBondVo> list) {
        BondBasicInfoDoc bondBasicInfo = bondBasicInfoRepository.findByCode(bondCode);
        if (null != bondBasicInfo) {
            BasicBondVo bondVo = new BasicBondVo();
            bondVo.setBondId(bondBasicInfo.getBondUniCode());
            bondVo.setBondCode(bondBasicInfo.getCode());
            bondVo.setBondName(bondBasicInfo.getShortName());
            list.put(bondBasicInfo.getBondUniCode(), bondVo);
            return true;
        } 
        return false;
    }

    public static void main(String[] args) {
    	
    	//System.out.println("123.SZ is mkt code postfix:" + isMarketPostfix("123.SZ"));
    	//System.out.println("123.Sz is mkt code postfix:" + isMarketPostfix("123.Sz"));
        //parseBondCodeListText("1234.SZ 8131.sZ 331.SZ 44.66:3333jjj ggggg 13g.3  3333.h 77.IB 1113.sh \\n 1113.sh 99.Sz ");
    	parseBondCodeListText("111 111.sz 22.A 33.Sz 44.sZ 55.SZ 66.ss \\n 77.sz abd s1234 ");
    }

}
