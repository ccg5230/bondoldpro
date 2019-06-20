package com.innodealing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.google.common.base.FinalizablePhantomReference;
import com.google.common.base.Objects;
import com.innodealing.consts.IndicatorConstants;
import com.innodealing.model.dm.bond.BondAreaData;
import com.innodealing.model.dm.bond.BondComExt;
import com.innodealing.model.mongo.dm.AreaEconomiesDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.EconomieIndicator;



@Component
public class AreaEconomiesService {
	
	private @Autowired JdbcTemplate jdbcTemplate;
	
	private @Autowired MongoTemplate mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(AreaEconomiesService.class);
	
	//构建数据
	public Boolean buildIndicators(){
		
		Long startTime = System.currentTimeMillis();
		Boolean result = true ;
		//区域经济指标
		List<AreaEconomiesDoc> docList = new ArrayList<>() ;
		String sql = "select d.*,c.AREA_CHI_NAME area_chi_name from dmdb.t_bond_area_data d "
				 		+"\n\tLEFT JOIN bond_ccxe.pub_area_code  c ON d.AREA_UNI_CODE = c.AREA_UNI_CODE "
				 		+"\n\twhere d.data_integrity not like '0%' ";
		//区域经济集合
		List<BondAreaData> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(BondAreaData.class));	
		
		
		if(list != null && list.size()!=0){
			List<CompletableFuture<AreaEconomiesDoc>> futures =
				      list.stream()
				          .map(data -> CompletableFuture.supplyAsync(() -> 				           
				           		convertToAreaEconomiesDoc(data)				           		
				        	)).collect(Collectors.toList());
				 
			docList = futures.stream()
				             .map(CompletableFuture::join)
				             .collect(Collectors.toList());        
		}
		if(docList == null || docList.size()==0){
			logger.info("构建数据失败 docList==null");
			return false;
		}
					
		// TODO Auto-generated method stub
		//往mongodb 中插入文档			
		try {
			//往mongodb中同步数据前，清空area_economies 中的数据
			logger.info("clear area_economies start ......");
			mongoTemplate.remove(new Query(),AreaEconomiesDoc.class);
			
			mongoTemplate.insertAll(docList);
			logger.info("数据同步完成 .......");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("数据同步失败");
			return false;
		}
		Long endTime = System.currentTimeMillis();	
		System.out.println(endTime-startTime);
		
		return result;
	}	
	
	public AreaEconomiesDoc convertToAreaEconomiesDoc(BondAreaData data){
		AreaEconomiesDoc doc = new AreaEconomiesDoc();
		List<EconomieIndicator> list2 = new ArrayList<>();
		
		doc.setAreaChiName(data.getAreaChiName());
		doc.setAreaUniCode(data.getAreaUniCode());
		doc.setBondMonth(data.getBondMonth());
		doc.setBondQuarter(data.getBondQuarter());
		doc.setBondYear(data.getBondYear());
		doc.setDataType(data.getDataType());
		doc.setStatisticsType(data.getStatisticsType());
		doc.setId(data.getId());
		
		EconomieIndicator indicator1 = new EconomieIndicator();				
		indicator1.setField(IndicatorConstants.permanent_resident_pop);
		indicator1.setFieldName("常住人口（万人）");
		
		BigDecimal val1 = data.getPermanentResidentPop();
		if(val1==null || Objects.equal(val1.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator1.setValue(null);
		}else{
			indicator1.setValue(val1);
		}
				
		indicator1.setUnit("万人");
		list2.add(indicator1);
	
		EconomieIndicator indicator2 = new EconomieIndicator();
		indicator2.setField(IndicatorConstants.domicile_pop);
		indicator2.setFieldName("户籍人口（万人）");
		
		BigDecimal val2 = data.getDomicilePop();
		if(val2==null ||  Objects.equal(val2.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator2.setValue(null);
		}else{
			indicator2.setValue(val2);
		}
		
		//indicator2.setValue(data.getDomicilePop());
		indicator2.setUnit("万人");
		list2.add(indicator2);
		
		EconomieIndicator indicator3 = new EconomieIndicator();
		indicator3.setField(IndicatorConstants.urban_pop);
		indicator3.setFieldName("城镇人口（万人）");
		
		BigDecimal val3 = data.getUrbanPop();
		if(val3==null || Objects.equal(val3.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator3.setValue(null);
		}else{
			indicator3.setValue(val3);
		}
		
		//indicator3.setValue(data.getUrbanPop());
		indicator3.setUnit("万人");
		list2.add(indicator3);
		
		EconomieIndicator indicator4 = new EconomieIndicator();
		indicator4.setField(IndicatorConstants.rural_pop);
		indicator4.setFieldName("乡村人口（万人）");
		
		BigDecimal val4 = data.getRuralPop();
		if(val4==null || Objects.equal(val4.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator4.setValue(null);
		}else{
			indicator4.setValue(val4);
		}
		
		//indicator4.setValue(data.getRuralPop());
		indicator4.setUnit("万人");
		list2.add(indicator4);
		
		EconomieIndicator indicator5 = new EconomieIndicator();
		indicator5.setField(IndicatorConstants.air_cargo_throughput);
		indicator5.setFieldName("机场货邮吞吐量（万吨）");
		
		BigDecimal val5 = data.getAirCargoThroughput();
		if(val5==null || Objects.equal(val5.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator5.setValue(null);
		}else{
			indicator5.setValue(val5);
		}
		
		//indicator5.setValue(data.getAirCargoThroughput());
		indicator5.setUnit("万吨");
		list2.add(indicator5);
		
		EconomieIndicator indicator6 = new EconomieIndicator();
		indicator6.setField(IndicatorConstants.air_freight_transport_volume);
		indicator6.setFieldName("机场货邮运输量（万吨）");
		
		BigDecimal val6 = data.getAirFreightTransportVolume();
		if(val6==null || Objects.equal(val6.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator6.setValue(null);
		}else{
			indicator6.setValue(val6);
		}
		
		
		//indicator6.setValue(data.getAirFreightTransportVolume());
		indicator6.setUnit("万吨");
		list2.add(indicator6);
		
		EconomieIndicator indicator7 = new EconomieIndicator();
		indicator7.setField(IndicatorConstants.air_passenger_throughput);
		indicator7.setFieldName("机场旅客吞吐量（万人次）");
		
		BigDecimal val7 = data.getAirPassengerThroughput();
		if(val7==null || Objects.equal(val7.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator7.setValue(null);
		}else{
			indicator7.setValue(val7);
		}
		
		
		//indicator7.setValue(data.getAirPassengerThroughput());
		indicator7.setUnit("万人次");
		list2.add(indicator7);
		
		EconomieIndicator indicator8 = new EconomieIndicator();
		indicator8.setField(IndicatorConstants.air_passenger_volume);
		indicator8.setFieldName("机场旅客运输量（万人次）");
		
		BigDecimal val8 = data.getAirPassengerVolume();
		if(val8==null || Objects.equal(val8.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator8.setValue(null);
		}else{
			indicator8.setValue(val8);
		}
		
		
		//indicator8.setValue(data.getAirPassengerVolume());
		indicator8.setUnit("万人次");
		list2.add(indicator8);
		
		EconomieIndicator indicator9 = new EconomieIndicator();
		indicator9.setField(IndicatorConstants.seaports_throughput);
		indicator9.setFieldName("港口吞吐量（亿吨）");
		
		BigDecimal val9 = data.getSeaportsThroughput();
		if(val9==null || Objects.equal(val9.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator9.setValue(null);
		}else{
			indicator9.setValue(val9);
		}
		
		//indicator9.setValue(data.getSeaportsThroughput());
		indicator9.setUnit("亿吨");
		list2.add(indicator9);
		
		EconomieIndicator indicator10 = new EconomieIndicator();
		indicator10.setField(IndicatorConstants.expressway_mileage);
		indicator10.setFieldName("高速公路里程（公里）");
		
		BigDecimal val10 = data.getExpresswayMileage();
		if(val10==null || Objects.equal(val10.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator10.setValue(null);
		}else{
			indicator10.setValue(val10);
		}
		
		//indicator10.setValue(data.getExpresswayMileage());
		indicator10.setUnit("公里");
		list2.add(indicator10);
		
		EconomieIndicator indicator11 = new EconomieIndicator();
		indicator11.setField(IndicatorConstants.railway_mileage);
		indicator11.setFieldName("铁路营业里程（公里）");
		
		BigDecimal val11 = data.getRailwayMileage();
		if(val11==null || Objects.equal(val11.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator11.setValue(null);
		}else{
			indicator11.setValue(val11);
		}
		
		//indicator11.setValue(data.getRailwayMileage());
		indicator11.setUnit("公里");
		list2.add(indicator11);
		
		EconomieIndicator indicator12 = new EconomieIndicator();
		indicator12.setField(IndicatorConstants.urbanization_rate);
		indicator12.setFieldName("城镇化率（%）");
		
		BigDecimal val12 = data.getUrbanizationRate();
		if(val12==null || Objects.equal(val12.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator12.setValue(null);
		}else{
			indicator12.setValue(val12);
		}
		
		//indicator12.setValue(data.getUrbanizationRate());
		indicator12.setUnit("%");
		list2.add(indicator12);
		
		EconomieIndicator indicator13 = new EconomieIndicator();
		indicator13.setField(IndicatorConstants.per_exp_urban_residents);
		indicator13.setFieldName("城镇居民人均消费支出（元）");
		
		BigDecimal val13 = data.getPerExpUrbanResidents();
		if(val13==null || Objects.equal(val13.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator13.setValue(null);
		}else{
			indicator13.setValue(val13);
		}
		
		
		//indicator13.setValue(data.getPerExpUrbanResidents());
		indicator13.setUnit("元");
		list2.add(indicator13);
		
		EconomieIndicator indicator14 = new EconomieIndicator();
		indicator14.setField(IndicatorConstants.gdp);
		indicator14.setFieldName("地区生产总值(亿元)");
		
		BigDecimal val14 = data.getGdp();
		if(val14==null || Objects.equal(val14.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator14.setValue(null);
		}else{
			indicator14.setValue(val14);
		}
		
		
		//indicator14.setValue(data.getGdp());
		indicator14.setUnit("亿元");
		list2.add(indicator14);
		
		EconomieIndicator indicator15 = new EconomieIndicator();
		indicator15.setField(IndicatorConstants.growth_gdp);
		indicator15.setFieldName("地区生产总值增速(%)");
		
		BigDecimal val15 = data.getGrowthGdp();
		if(val15==null || Objects.equal(val15.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator15.setValue(null);
		}else{
			indicator15.setValue(val15);
		}
						
		//indicator15.setValue(data.getGrowthGdp());
		indicator15.setUnit("%");
		list2.add(indicator15);
		
		EconomieIndicator indicator16 = new EconomieIndicator();
		indicator16.setField(IndicatorConstants.gdp_per_capita);
		indicator16.setFieldName("地区人均生产总值(元)");
		
		BigDecimal val16 = data.getGdpPerCapita();
		if(val16==null || Objects.equal(val16.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator16.setValue(null);
		}else{
			indicator16.setValue(val16);
		}
				
		//indicator16.setValue(data.getGdpPerCapita());
		indicator16.setUnit("元");
		list2.add(indicator16);
		
		EconomieIndicator indicator17 = new EconomieIndicator();
		indicator17.setField(IndicatorConstants.growth_gdp_per_capita);
		indicator17.setFieldName("地区人均生产总值增速(%)");
		
		BigDecimal val17 = data.getGrowthGdpPerCapita();
		if(val17==null || Objects.equal(val17.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator17.setValue(null);
		}else{
			indicator17.setValue(val17);
		}
				
		//indicator17.setValue(data.getGrowthGdpPerCapita());
		indicator17.setUnit("%");
		list2.add(indicator17);
		
		EconomieIndicator indicator18 = new EconomieIndicator();
		indicator18.setField(IndicatorConstants.gdp_add_val_primary_indu);
		indicator18.setFieldName("第一产业增加值(亿元)");
		
		BigDecimal val18 = data.getGdpAddValPrimaryIndu();
		if(val18==null || Objects.equal(val18.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator18.setValue(null);
		}else{
			indicator18.setValue(val18);
		}
		
		//indicator18.setValue(data.getGdpAddValPrimaryIndu());
		indicator18.setUnit("亿元");
		list2.add(indicator18);
		
		EconomieIndicator indicator19 = new EconomieIndicator();
		indicator19.setField(IndicatorConstants.growth_add_val_primary_indu);
		indicator19.setFieldName("第一产业增加值增速(%)");
		
		BigDecimal val19 = data.getGrowthAddValPrimaryIndu();
		if(val19==null || Objects.equal(val19.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator19.setValue(null);
		}else{
			indicator19.setValue(val19);
		}
		
		//indicator19.setValue(data.getGrowthAddValPrimaryIndu());
		indicator19.setUnit("%");
		list2.add(indicator19);
		
		EconomieIndicator indicator20 = new EconomieIndicator();
		indicator20.setField(IndicatorConstants.gdp_add_val_secondary_indu);
		indicator20.setFieldName("第二产业增加值(亿元)");
		
		BigDecimal val20 = data.getGdpAddValSecondaryIndu();
		if(val20==null || Objects.equal(val20.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator20.setValue(null);
		}else{
			indicator20.setValue(val20);
		}
		
		//indicator20.setValue(data.getGdpAddValSecondaryIndu());
		indicator20.setUnit("亿元");
		list2.add(indicator20);
		
		EconomieIndicator indicator21 = new EconomieIndicator();
		indicator21.setField(IndicatorConstants.growth_add_val_secondary_indu);
		indicator21.setFieldName("第二产业增加值增速(%)");
		
		BigDecimal val21 = data.getGrowthAddValSecondaryIndu();
		if(val21==null || Objects.equal(val21.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator21.setValue(null);
		}else{
			indicator21.setValue(val21);
		}
				
		//indicator21.setValue(data.getGrowthAddValSecondaryIndu());
		indicator21.setUnit("%");
		list2.add(indicator21);
		
		EconomieIndicator indicator22 = new EconomieIndicator();
		indicator22.setField(IndicatorConstants.gdp_add_val_tertiary_indu);
		indicator22.setFieldName("第三产业增加值(亿元)");
		
		BigDecimal val22 = data.getGdpAddValTertiaryIndu();
		if(val22==null || Objects.equal(val22.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator22.setValue(null);
		}else{
			indicator22.setValue(val22);
		}
						
		//indicator22.setValue(data.getGdpAddValTertiaryIndu());
		indicator22.setUnit("亿元");
		list2.add(indicator22);
		
		EconomieIndicator indicator23 = new EconomieIndicator();
		indicator23.setField(IndicatorConstants.growth_add_val_tertiary_indu);
		indicator23.setFieldName("第三产业增加值增速(%)");
		
		BigDecimal val23 = data.getGrowthAddValTertiaryIndu();
		if(val23==null || Objects.equal(val23.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator23.setValue(null);
		}else{
			indicator23.setValue(val23);
		}
		
		//indicator23.setValue(data.getGrowthAddValTertiaryIndu());
		indicator23.setUnit("%");
		list2.add(indicator23);
		
		EconomieIndicator indicator24= new EconomieIndicator();
		indicator24.setField(IndicatorConstants.indu_add_val);
		indicator24.setFieldName("工业增加值(亿元)");
		
		BigDecimal val24 = data.getInduAddVal();
		if(val24==null || Objects.equal(val24.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator24.setValue(null);
		}else{
			indicator24.setValue(val24);
		}
		
		//indicator24.setValue(data.getInduAddVal());
		indicator24.setUnit("亿元");
		list2.add(indicator24);
		
		EconomieIndicator indicator25 = new EconomieIndicator();
		indicator25.setField(IndicatorConstants.growth_indu_output_val);
		indicator25.setFieldName("工业总产值增速(%)");
		
		BigDecimal val25 = data.getGrowthInduOutputVal();
		if(val25==null || Objects.equal(val25.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator25.setValue(null);
		}else{
			indicator25.setValue(val25);
		}
			
		//indicator25.setValue(data.getGrowthInduOutputVal());
		indicator25.setUnit("%");
		list2.add(indicator25);
		
		EconomieIndicator indicator26 = new EconomieIndicator();
		indicator26.setField(IndicatorConstants.invest_Fix_assets_whole_society);
		indicator26.setFieldName("全社会固定资产投资(亿元)");
		
		BigDecimal val26 = data.getInvestFixAssetsWholeSociety();
		if(val26==null ||  Objects.equal(val26.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator26.setValue(null);
		}else{
			indicator26.setValue(val26);
		}				
		//indicator26.setValue(data.getInvestFixAssetsWholeSociety());
		indicator26.setUnit("亿元");
		list2.add(indicator26);
		
		EconomieIndicator indicator27 = new EconomieIndicator();
		indicator27.setField(IndicatorConstants.invest_Fix_assets);
		indicator27.setFieldName("固定资产投资(亿元)");
		
		BigDecimal val27 = data.getInvestFixAssets();
		if(val27==null || Objects.equal(val27.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator27.setValue(null);
		}else{
			indicator27.setValue(val27);
		}		
		//indicator27.setValue(data.getInvestFixAssets());
		indicator27.setUnit("亿元");
		list2.add(indicator27);
		
		EconomieIndicator indicator28 = new EconomieIndicator();
		indicator28.setField(IndicatorConstants.total_retail_sales_consumer_goods);
		indicator28.setFieldName("社会消费品零售总额(亿元)");
		
		BigDecimal val28 = data.getTotalRetailSalesConsumerGoods();
		if(val28==null || Objects.equal(val28.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator28.setValue(null);
		}else{
			indicator28.setValue(val28);
		}
		//indicator28.setValue(data.getTotalRetailSalesConsumerGoods());
		indicator28.setUnit("亿元");
		list2.add(indicator28);

		//计算人均社会消费品零售总额(万元)
		EconomieIndicator indicator29 = new EconomieIndicator();
		indicator29.setField(IndicatorConstants.total_retail_sales_per_consumer_goods);
		indicator29.setFieldName("人均社会消费品零售总额(万元)");	
		indicator29.setUnit("万元");
		BigDecimal num2 = data.getTotalRetailSalesConsumerGoods();
		BigDecimal num1 = data.getPermanentResidentPop();
		if(num1==null || num2==null || Objects.equal(num1.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){					
			indicator29.setValue(null);
		}else{							
			double b = num2.divide(num1, 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(10000)).doubleValue();										
			indicator29.setValue(new BigDecimal(String.valueOf(b)));
		}
		list2.add(indicator29);

		EconomieIndicator indicator30 = new EconomieIndicator();
		indicator30.setField(IndicatorConstants.total_import_export_dollar);
		indicator30.setFieldName("进出口总额(亿美元)");
		indicator30.setUnit("亿美元");
		
		BigDecimal val30 = data.getTotalImportExportDollar();
		if(val30==null || Objects.equal(val30.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator30.setValue(null);
		}else{
			indicator30.setValue(val30);
		}
		//indicator30.setValue(data.getTotalImportExportDollar());
		list2.add(indicator30);
		
		//计算人均进出口总额（亿美元）
		EconomieIndicator indicator31 = new EconomieIndicator();
		indicator31.setField(IndicatorConstants.total_import_export_per_dollar);
		indicator31.setFieldName("人均进出口总额(万美元)");	
		indicator31.setUnit("万美元");

		BigDecimal num3 = data.getTotalImportExportDollar();				
		if(num1 == null ||num3==null ||Objects.equal(num1.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
			indicator31.setValue(null);
		}else{						
			double b = num3.divide(num1, 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(10000)).doubleValue();
			
			indicator31.setValue(new BigDecimal(String.valueOf(b)));				
		}
		list2.add(indicator31);	
		
		EconomieIndicator indicator32 = new EconomieIndicator();
		indicator32.setField(IndicatorConstants.total_import_export);
		indicator32.setFieldName("进出口总额(亿元)");
		
		BigDecimal val32 = data.getTotalImportExport();
		if(val32==null || Objects.equal(val32.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator32.setValue(null);
		}else{
			indicator32.setValue(val32);
		}
		//indicator32.setValue(data.getTotalImportExport());
		indicator32.setUnit("亿元");
		list2.add(indicator32);
					
		//计算人均进出口总额（亿元）		
		EconomieIndicator indicator33 = new EconomieIndicator();
		indicator33.setField(IndicatorConstants.total_import_per_export);
		indicator33.setFieldName("人均进出口总额(万元)");
		indicator33.setUnit("万元");
		BigDecimal num4 = data.getTotalImportExport();
		if(num1 == null ||num4==null ||Objects.equal(num1.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
			indicator33.setValue(null);
		}else{		
			double b = num4.divide(num1, 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(10000)).doubleValue() ;
			
			indicator33.setValue(new BigDecimal(String.valueOf(b)));
		}
		list2.add(indicator33);
		
		EconomieIndicator indicator34 = new EconomieIndicator();
		indicator34.setField(IndicatorConstants.foreign_currency_deposits_pro);
		indicator34.setFieldName("年末全省金融机构本外币各项存款余额(亿元)");
		
		BigDecimal val34 = data.getForeignCurrencyDepositsPro();
		if(val34==null || Objects.equal(val34.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator34.setValue(null);
		}else{
			indicator34.setValue(val34);
		}
		
		//indicator34.setValue(data.getForeignCurrencyDepositsPro());
		indicator34.setUnit("亿元");
		list2.add(indicator34);

		
		EconomieIndicator indicator35 = new EconomieIndicator();
		indicator35.setField(IndicatorConstants.rmb_deposits_pro);
		indicator35.setFieldName("年末全省金融机构人民币各项存款余额(亿元)");
		
		BigDecimal val35 = data.getRmbDepositsPro();
		if(val35==null || Objects.equal(val35.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator35.setValue(null);
		}else{
			indicator35.setValue(val35);
		}
		//indicator35.setValue(data.getRmbDepositsPro());
		indicator35.setUnit("亿元");
		list2.add(indicator35);

		EconomieIndicator indicator36 = new EconomieIndicator();
		indicator36.setField(IndicatorConstants.foreign_currency_loan_bal_pro);
		indicator36.setFieldName("年末全省金融机构本外币各项贷款余额(亿元)");
		
		BigDecimal val36 = data.getForeignCurrencyLoanBalPro();
		if(val36==null || Objects.equal(val36.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator36.setValue(null);
		}else{
			indicator36.setValue(val36);
		}
		//indicator36.setValue(data.getForeignCurrencyLoanBalPro());
		indicator36.setUnit("亿元");
		list2.add(indicator36);
		
		EconomieIndicator indicator37 = new EconomieIndicator();
		indicator37.setField(IndicatorConstants.rmb_loan_bal_pro);
		indicator37.setFieldName("年末全省金融机构人民币各项贷款余额(亿元)");
		
		BigDecimal val37 = data.getRmbLoanBalPro();
		if(val37==null || Objects.equal(val37.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator37.setValue(null);
		}else{
			indicator37.setValue(val37);
		}
		//indicator37.setValue(data.getRmbLoanBalPro());
		indicator37.setUnit("亿元");
		list2.add(indicator37);
		
		EconomieIndicator indicator38 = new EconomieIndicator();
		indicator38.setField(IndicatorConstants.gov_receipts);
		indicator38.setFieldName("财政收入（亿元）");
		
		BigDecimal val38 = data.getGovReceipts();
		if(val38==null || Objects.equal(val38.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator38.setValue(null);
		}else{
			indicator38.setValue(val38);
		}
		//indicator38.setValue(data.getGovReceipts());
		indicator38.setUnit("亿元");
		list2.add(indicator38);
		
		EconomieIndicator indicator39 = new EconomieIndicator();
		indicator39.setField(IndicatorConstants.pub_gov_receipts);
		indicator39.setFieldName("公共财政收入（亿元）");
		
		BigDecimal val39 = data.getPubGovReceipts();
		if(val39==null || Objects.equal(val39.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator39.setValue(null);
		}else{
			indicator39.setValue(val39);
		}
		//indicator39.setValue(data.getPubGovReceipts());
		indicator39.setUnit("亿元");
		list2.add(indicator39);
		
		EconomieIndicator indicator40 = new EconomieIndicator();
		indicator40.setField(IndicatorConstants.tax_rev);
		indicator40.setFieldName("税收收入(亿元)");
		
		BigDecimal val40 = data.getTaxRev();
		if(val40==null || Objects.equal(val40.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator40.setValue(null);
		}else{
			indicator40.setValue(val40);
		}
		//indicator40.setValue(data.getTaxRev());
		indicator40.setUnit("亿元");
		list2.add(indicator40);
					
		EconomieIndicator indicator41 = new EconomieIndicator();
		indicator41.setField(IndicatorConstants.nontax_rev);
		indicator41.setFieldName("非税收收入(亿元)");
		
		BigDecimal val41 = data.getNontaxRev();
		if(val41==null || Objects.equal(val41.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator41.setValue(null);
		}else{
			indicator41.setValue(val41);
		}
		//indicator41.setValue(data.getNontaxRev());
		indicator41.setUnit("亿元");
		list2.add(indicator41);
		
		//计算税收收入占比
		BigDecimal num5 = data.getTaxRev();
		BigDecimal num6 = data.getPubGovReceipts();

		EconomieIndicator indicator42 = new EconomieIndicator();
		indicator42.setField(IndicatorConstants.tax_rev_proportion);
		indicator42.setFieldName("税收收入占比");
		indicator42.setUnit("%");
		if(num6 == null ||num5==null ||Objects.equal(num6.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){				
			indicator42.setValue(null);
		}else{					
			double b = num5.divide(num6, 5, BigDecimal.ROUND_HALF_UP).doubleValue()*100;					
			indicator42.setValue(new BigDecimal(String.valueOf(b)));
		}
		list2.add(indicator42);	

		EconomieIndicator indicator43 = new EconomieIndicator();
		indicator43.setField(IndicatorConstants.pro_pub_budget_rev);
		indicator43.setFieldName("省级一般公共预算收入(亿元)");
		
		BigDecimal val43 = data.getProPubBudgetRev();
		if(val43==null || Objects.equal(val43.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator43.setValue(null);
		}else{
			indicator43.setValue(val43);
		}
		//indicator43.setValue(data.getProPubBudgetRev());
		indicator43.setUnit("亿元");
		list2.add(indicator43);
		
		EconomieIndicator indicator44 = new EconomieIndicator();
		indicator44.setField(IndicatorConstants.pro_pub_budget_tax_rev);
		indicator44.setFieldName("省级一般公共预算收入税收收入(亿元)");
		
		BigDecimal val44 = data.getProPubBudgetTaxRev();
		if(val44==null || Objects.equal(val44.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator44.setValue(null);
		}else{
			indicator44.setValue(val44);
		}
		//indicator44.setValue(data.getProPubBudgetTaxRev());
		indicator44.setUnit("亿元");
		list2.add(indicator44);
		
		EconomieIndicator indicator45 = new EconomieIndicator();
		indicator45.setField(IndicatorConstants.pro_pub_budget_nontax_rev);
		indicator45.setFieldName("省级一般公共预算收入非税收收入(亿元)");
		
		BigDecimal val45 = data.getProPubBudgetNontaxRev();
		if(val45==null || Objects.equal(val45.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator45.setValue(null);
		}else{
			indicator45.setValue(val45);
		}
		//indicator45.setValue(data.getProPubBudgetNontaxRev());
		indicator45.setUnit("亿元");
		list2.add(indicator45);
		
		//计算省级税收收入占比
		BigDecimal num7 = data.getProPubBudgetTaxRev();
		BigDecimal num8 = data.getProPubBudgetRev();								
		
		EconomieIndicator indicator46 = new EconomieIndicator();
		indicator46.setField(IndicatorConstants.tax_rev_pro_proportion);
		indicator46.setFieldName("省级税收收入占比");
		indicator46.setUnit("%");
		if(num8==null || num7==null|| Objects.equal(num8.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
			indicator46.setValue(null);
		}else{
			double b = num7.divide(num8, 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();					
			indicator46.setValue(new BigDecimal(String.valueOf(b)));
		}
		list2.add(indicator46);
		
		EconomieIndicator indicator47 = new EconomieIndicator();
		indicator47.setField(IndicatorConstants.pub_budget_rev_pro_cor_lev);
		indicator47.setFieldName("省本级公共财政预算收入(亿元)");
		
		BigDecimal val47 = data.getPubBudgetRevProCorLev();
		if(val47==null || Objects.equal(val47.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator47.setValue(null);
		}else{
			indicator47.setValue(val47);
		}
		//indicator47.setValue(data.getPubBudgetRevProCorLev());
		indicator47.setUnit("亿元");
		list2.add(indicator47);

		EconomieIndicator indicator48 = new EconomieIndicator();
		indicator48.setField(IndicatorConstants.tax_rev_pro_cor_lev);
		indicator48.setFieldName("省本级税收收入(亿元)");
		
		BigDecimal val48 = data.getTaxRevProCorLev();
		if(val48==null || Objects.equal(val48.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator48.setValue(null);
		}else{
			indicator48.setValue(val48);
		}
		//indicator48.setValue(data.getTaxRevProCorLev());
		indicator48.setUnit("亿元");
		list2.add(indicator48);
		
		EconomieIndicator indicator49 = new EconomieIndicator();
		indicator49.setField(IndicatorConstants.nontax_rev_pro_cor_lev);
		indicator49.setFieldName("省本级非税收收入(亿元)");
		
		BigDecimal val49 = data.getNontaxRevProCorLev();
		if(val49==null || Objects.equal(val49.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator49.setValue(null);
		}else{
			indicator49.setValue(val49);
		}
		//indicator49.setValue(data.getNontaxRevProCorLev());
		indicator49.setUnit("亿元");
		list2.add(indicator49);
		
		//计算省本机税收收入占比
		BigDecimal num9 = data.getTaxRevProCorLev();
		BigDecimal num10 = data.getPubBudgetRevProCorLev();
		
		EconomieIndicator indicator50 = new EconomieIndicator();
		indicator50.setField(IndicatorConstants.tax_rev_pro_cor_proportion);
		indicator50.setFieldName("省本级税收收入占比");
		indicator50.setUnit("%");
		if(num10 == null || num9==null||Objects.equal(num10.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
			indicator50.setValue(null);
		}else{
			double b = num9.divide(num10, 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
			
			indicator50.setValue(new BigDecimal(String.valueOf(b)));
		}
		list2.add(indicator50);

		EconomieIndicator indicator51 = new EconomieIndicator();
		indicator51.setField(IndicatorConstants.grant_higher_authority);
		indicator51.setFieldName("上级补助收入");
		
		BigDecimal val51 = data.getGrantHigherAuthority();
		if(val51==null || Objects.equal(val51.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator51.setValue(null);
		}else{
			indicator51.setValue(val51);
		}
		//indicator51.setValue(data.getGrantHigherAuthority());
		indicator51.setUnit("亿元");
		list2.add(indicator51);
		
		EconomieIndicator indicator52 = new EconomieIndicator();
		indicator52.setField(IndicatorConstants.return_rev);
		indicator52.setFieldName("返还型收入");
		
		BigDecimal val52 = data.getReturnRev();
		if(val52==null || Objects.equal(val52.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator52.setValue(null);
		}else{
			indicator52.setValue(val52);
		}
		//indicator52.setValue(data.getReturnRev());
		indicator52.setUnit("亿元");
		list2.add(indicator52);
		
		EconomieIndicator indicator53 = new EconomieIndicator();
		indicator53.setField(IndicatorConstants.gen_transfer_pay_rev);
		indicator53.setFieldName("一般性转移支付收入");
		
		BigDecimal val53 = data.getGenTransferPayRev();
		if(val53==null || Objects.equal(val53.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator53.setValue(null);
		}else{
			indicator53.setValue(val53);
		}
		//indicator53.setValue(data.getGenTransferPayRev());
		indicator53.setUnit("亿元");
		list2.add(indicator53);
		
		EconomieIndicator indicator54 = new EconomieIndicator();
		indicator54.setField(IndicatorConstants.spec_transfer_pay_rev);
		indicator54.setFieldName("专项转移支付收入");
		
		BigDecimal val54 = data.getSpecTransferPayRev();
		if(val54==null || Objects.equal(val54.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator54.setValue(null);
		}else{
			indicator54.setValue(val54);
		}
		//indicator54.setValue(data.getSpecTransferPayRev());
		indicator54.setUnit("亿元");
		list2.add(indicator54);
		
		EconomieIndicator indicator55 = new EconomieIndicator();
		indicator55.setField(IndicatorConstants.gov_fund_rev);
		indicator55.setFieldName("政府性基金收入");
		
		BigDecimal val55 = data.getGovFundRev();
		if(val55==null || Objects.equal(val55.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator55.setValue(null);
		}else{
			indicator55.setValue(val55);
		}
		//indicator55.setValue(data.getGovFundRev());
		indicator55.setUnit("亿元");
		list2.add(indicator55);
		
		
		EconomieIndicator indicator56 = new EconomieIndicator();
		indicator56.setField(IndicatorConstants.whole_pro_gov_fund_budget_rev);
		indicator56.setFieldName("全省政府性基金预算收入(亿元)");
		
		BigDecimal val56 = data.getWholeProGovFundBudgetRev();
		if(val56==null || Objects.equal(val56.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator56.setValue(null);
		}else{
			indicator56.setValue(val56);
		}
		//indicator56.setValue(data.getWholeProGovFundBudgetRev());
		indicator56.setUnit("亿元");
		list2.add(indicator56);
		
		EconomieIndicator indicator57 = new EconomieIndicator();
		indicator57.setField(IndicatorConstants.pro_gov_fund_budget_rev);
		indicator57.setFieldName("省级政府性基金预算收入(亿元)");
		
		BigDecimal val57 = data.getProGovFundBudgetRev();
		if(val57==null || Objects.equal(val57.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator57.setValue(null);
		}else{
			indicator57.setValue(val57);
		}
		//indicator57.setValue(data.getProGovFundBudgetRev());
		indicator57.setUnit("亿元");
		list2.add(indicator57);
		
		EconomieIndicator indicator58 = new EconomieIndicator();
		indicator58.setField(IndicatorConstants.fund_budget_rev_pro_cor_lev);
		indicator58.setFieldName("省本级政府性基金预算收入(亿元)");
		
		BigDecimal val58 = data.getFundBudgetRevProCorLev();
		if(val58==null || Objects.equal(val58.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator58.setValue(null);
		}else{
			indicator58.setValue(val58);
		}
		//indicator58.setValue(data.getFundBudgetRevProCorLev());
		indicator58.setUnit("亿元");
		list2.add(indicator58);
		
		EconomieIndicator indicator59 = new EconomieIndicator();
		indicator59.setField(IndicatorConstants.land_leasing_rev);
		indicator59.setFieldName("土地出让收入(亿元)");
		
		BigDecimal val59 = data.getLandLeasingRev();
		if(val59==null || Objects.equal(val59.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator59.setValue(null);
		}else{
			indicator59.setValue(val59);
		}
		//indicator59.setValue(data.getLandLeasingRev());
		indicator59.setUnit("亿元");
		list2.add(indicator59);
		
		EconomieIndicator indicator60 = new EconomieIndicator();
		indicator60.setField(IndicatorConstants.extra_budget_finance_spec_account_rev);
		indicator60.setFieldName("预算外财政专户收入(亿元)");
		
		BigDecimal val60 = data.getExtraBudgetFinanceSpecAccountRev();
		if(val60==null || Objects.equal(val60.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator60.setValue(null);
		}else{
			indicator60.setValue(val60);
		}
		//indicator60.setValue(data.getExtraBudgetFinanceSpecAccountRev());
		indicator60.setUnit("亿元");
		list2.add(indicator60);
		
		EconomieIndicator indicator61 = new EconomieIndicator();
		indicator61.setField(IndicatorConstants.fiscal_exp);
		indicator61.setFieldName("财政支出(亿元)");
		
		BigDecimal val61 = data.getFiscalExp();
		if(val61==null || Objects.equal(val61.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator61.setValue(null);
		}else{
			indicator61.setValue(val61);
		}
		//indicator61.setValue(data.getFiscalExp());
		indicator61.setUnit("亿元");
		list2.add(indicator61);
		
		EconomieIndicator indicator62 = new EconomieIndicator();
		indicator62.setField(IndicatorConstants.pub_fiscal_exp);
		indicator62.setFieldName("公共财政支出(亿元)");
		
		BigDecimal val62 = data.getPubFiscalExp();
		if(val62==null || Objects.equal(val62.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator62.setValue(null);
		}else{
			indicator62.setValue(val62);
		}
		//indicator62.setValue(data.getPubFiscalExp());
		indicator62.setUnit("亿元");
		list2.add(indicator62);
		
		
		EconomieIndicator indicator63 = new EconomieIndicator();
		indicator63.setField(IndicatorConstants.pro_budget_exp);
		indicator63.setFieldName("省级一般公共预算支出(亿元)");
		
		BigDecimal val63 = data.getProBudgetExp();
		if(val63==null || Objects.equal(val63.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator63.setValue(null);
		}else{
			indicator63.setValue(val63);
		}
		//indicator63.setValue(data.getProBudgetExp());
		indicator63.setUnit("亿元");
		list2.add(indicator63);
		
		EconomieIndicator indicator64= new EconomieIndicator();
		indicator64.setField(IndicatorConstants.budget_exp_pro_cor_lev);
		indicator64.setFieldName("省本级公共财政预算支出(亿元)");
		
		BigDecimal val64 = data.getBudgetExpProCorLev();
		if(val64==null || Objects.equal(val64.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator64.setValue(null);
		}else{
			indicator64.setValue(val64);
		}
		//indicator64.setValue(data.getBudgetExpProCorLev());	
		indicator64.setUnit("亿元");
		list2.add(indicator64);
		
		EconomieIndicator indicator65 = new EconomieIndicator();
		indicator65.setField(IndicatorConstants.gov_fund_exp);
		indicator65.setFieldName("政府性基金支出");
		
		BigDecimal val65 = data.getGovFundExp();
		if(val65==null || Objects.equal(val65.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator65.setValue(null);
		}else{
			indicator65.setValue(val65);
		}
		//indicator65.setValue(data.getGovFundExp());
		indicator65.setUnit("亿元");
		list2.add(indicator65);
		
		EconomieIndicator indicator66 = new EconomieIndicator();
		indicator66.setField(IndicatorConstants.whole_pro_gov_fund_budget_exp);
		indicator66.setFieldName("全省政府性基金预算支出(亿元)");
		
		BigDecimal val66 = data.getWholeProGovFundBudgetExp();
		if(val66==null || Objects.equal(val66.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator66.setValue(null);
		}else{
			indicator66.setValue(val66);
		}
		//indicator66.setValue(data.getWholeProGovFundBudgetExp());
		indicator66.setUnit("亿元");
		list2.add(indicator66);
	
		EconomieIndicator indicator67 = new EconomieIndicator();
		indicator67.setField(IndicatorConstants.pro_gov_fund_budget_exp);
		indicator67.setFieldName("省级政府性基金预算支出(亿元)");
		
		BigDecimal val67 = data.getProGovFundBudgetExp();
		if(val67==null || Objects.equal(val67.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator67.setValue(null);
		}else{
			indicator67.setValue(val67);
		}
		//indicator67.setValue(data.getProGovFundBudgetExp());
		indicator67.setUnit("亿元");
		list2.add(indicator67);
		
		EconomieIndicator indicator68 = new EconomieIndicator();
		indicator68.setField(IndicatorConstants.fund_budget_exp_pro_cor_lev);
		indicator68.setFieldName("省本级政府性基金预算支出(亿元)");
		
		BigDecimal val68 = data.getFundBudgetExpProCorLev();
		if(val68==null || Objects.equal(val68.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator68.setValue(null);
		}else{
			indicator68.setValue(val68);
		}
		//indicator68.setValue(data.getFundBudgetExpProCorLev());
		indicator68.setUnit("亿元");
		list2.add(indicator68);
		
		EconomieIndicator indicator69 = new EconomieIndicator();
		indicator69.setField(IndicatorConstants.extra_budget_finance_spec_account_exp);
		indicator69.setFieldName("预算外财政专户支出(亿元)");
		
		BigDecimal val69 = data.getExtraBudgetFinanceSpecAccountExp();
		if(val69==null || Objects.equal(val69.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator69.setValue(null);
		}else{
			indicator69.setValue(val69);
		}
		//indicator69.setValue(data.getExtraBudgetFinanceSpecAccountExp());
		indicator69.setUnit("亿元");
		list2.add(indicator69);
		
		//计算财政自给率
		BigDecimal num11 = data.getPubGovReceipts();
		BigDecimal num12 = data.getPubFiscalExp();							
		EconomieIndicator indicator70 = new EconomieIndicator();
		indicator70.setField(IndicatorConstants.finance_self_sufficiency_rate);
		indicator70.setFieldName("财政自给率");
		indicator70.setUnit("%");
		if(num12 == null || num11==null ||Objects.equal(num12.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
			indicator70.setValue(null);
		}else{
			double b = num11.divide(num12, 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
			
			indicator70.setValue(new BigDecimal(String.valueOf(b)));
		}
		list2.add(indicator70);
		
		Double totalAmount = null;				
		try {
			//计算地方债券总发行量
			List<Long> areaUniCodes = totalAmountAreaUniCode(data.getAreaUniCode()); 				
			List<Long>  comUniCodeList = queryComUniCodeList(areaUniCodes);
			totalAmount = queryBond(comUniCodeList,data);					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("=================");			
		}
						

		EconomieIndicator indicator71 = new EconomieIndicator();
		indicator71.setField(IndicatorConstants.total_issuance_local_bonds);
		indicator71.setFieldName("地方债券总发行量");
		indicator71.setUnit("亿元");
		
		BigDecimal total= new BigDecimal(Double.toString(totalAmount)).setScale(5,BigDecimal.ROUND_HALF_UP );
		if(totalAmount==null || Objects.equal(new BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP ), new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
			indicator71.setValue(null);
		}else{
			indicator71.setValue(new BigDecimal(totalAmount).setScale(5, BigDecimal.ROUND_HALF_UP ));
		}
		list2.add(indicator71);
		
		EconomieIndicator indicator72 = new EconomieIndicator();
		indicator72.setField(IndicatorConstants.gov_debt);
		indicator72.setFieldName("地方政府债务");
		indicator72.setUnit("亿元");
		
		BigDecimal val72 = data.getGovDebt();
		if(val72==null || Objects.equal(val72.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator72.setValue(null);
		}else{
			indicator72.setValue(val72);
		}
		//indicator72.setValue(data.getGovDebt());
		list2.add(indicator72);
		
		EconomieIndicator indicator73 = new EconomieIndicator();
		indicator73.setField(IndicatorConstants.gov_debt_gen);
		indicator73.setFieldName("地方政府债务一般债务");
		
		BigDecimal val73 = data.getGovDebtGen();
		if(val73==null || Objects.equal(val73.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator73.setValue(null);
		}else{
			indicator73.setValue(val73);
		}
		//indicator73.setValue(data.getGovDebtGen());
		indicator73.setUnit("亿元");
		list2.add(indicator73);
		
		EconomieIndicator indicator74 = new EconomieIndicator();
		indicator74.setField(IndicatorConstants.gov_debt_spec);
		indicator74.setFieldName("地方政府债务专项债务");
		
		BigDecimal val74 = data.getGovDebtSpec();
		if(val74==null || Objects.equal(val74.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator74.setValue(null);
		}else{
			indicator74.setValue(val74);
		}
		//indicator74.setValue(data.getGovDebtSpec());
		indicator74.setUnit("亿元");
		list2.add(indicator74);
		
		EconomieIndicator indicator75 = new EconomieIndicator();
		indicator75.setField(IndicatorConstants.gov_debt_limit);
		indicator75.setFieldName("地方政府债务限额(亿元)");
		
		BigDecimal val75 = data.getGovDebtLimit();
		if(val75==null || Objects.equal(val75.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator75.setValue(null);
		}else{
			indicator75.setValue(val75);
		}
		//indicator75.setValue(data.getGovDebtLimit());
		indicator75.setUnit("亿元");
		list2.add(indicator75);
		
		EconomieIndicator indicator76 = new EconomieIndicator();
		indicator76.setField(IndicatorConstants.gov_debt_limit_gen);
		indicator76.setFieldName("地方政府债务限额一般债务(亿元)");
		
		BigDecimal val76 = data.getGovDebtLimitGen();
		if(val76==null || Objects.equal(val76.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator76.setValue(null);
		}else{
			indicator76.setValue(val76);
		}
		//indicator76.setValue(data.getGovDebtLimitGen());
		indicator76.setUnit("亿元");
		list2.add(indicator76);
		
		EconomieIndicator indicator77 = new EconomieIndicator();
		indicator77.setField(IndicatorConstants.gov_debt_limit_spec);
		indicator77.setFieldName("地方政府债务限额专项债务(亿元)");
		
		BigDecimal val77 = data.getGovDebtLimitSpec();
		if(val77==null || Objects.equal(val77.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator77.setValue(null);
		}else{
			indicator77.setValue(val77);
		}
		//indicator77.setValue(data.getGovDebtLimitSpec());
		indicator77.setUnit("亿元");
		list2.add(indicator77);
		
		EconomieIndicator indicator78 = new EconomieIndicator();
		indicator78.setField(IndicatorConstants.debt_bal_pro_cor_lev);
		indicator78.setFieldName("省本级政府债务余额(亿元)");
		
		BigDecimal val78 = data.getDebtBalProCorLev();
		if(val78==null || Objects.equal(val78.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator78.setValue(null);
		}else{
			indicator78.setValue(val78);
		}
		//indicator78.setValue(data.getDebtBalProCorLev());
		indicator78.setUnit("亿元");
		list2.add(indicator78);
		
		EconomieIndicator indicator79 = new EconomieIndicator();
		indicator79.setField(IndicatorConstants.debt_bal_pro_cor_lev_gen);
		indicator79.setFieldName("省本级政府债务余额一般债务(亿元)");
		
		BigDecimal val79 = data.getDebtBalProCorLevGen();
		if(val79==null || Objects.equal(val79.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator79.setValue(null);
		}else{
			indicator79.setValue(val79);
		}
		//indicator79.setValue(data.getDebtBalProCorLevGen());
		indicator79.setUnit("亿元");
		list2.add(indicator79);
		
		EconomieIndicator indicator80 = new EconomieIndicator();
		indicator80.setField(IndicatorConstants.debt_bal_pro_cor_lev_spec);
		indicator80.setFieldName("省本级政府债务余额专项债务(亿元)");
		
		BigDecimal val80 = data.getDebtBalProCorLevSpec();
		if(val80==null || Objects.equal(val80.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator80.setValue(null);
		}else{
			indicator80.setValue(val80);
		}
		//indicator80.setValue(data.getDebtBalProCorLevSpec());
		indicator80.setUnit("亿元");
		list2.add(indicator80);
		
		EconomieIndicator indicator81 = new EconomieIndicator();
		indicator81.setField(IndicatorConstants.debt_limit_pro_cor_lev);
		indicator81.setFieldName("省本级政府债务限额(亿元)");
		
		BigDecimal val81 = data.getDebtLimitProCorLev();
		if(val81==null || Objects.equal(val81.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator81.setValue(null);
		}else{
			indicator81.setValue(val81);
		}
		//indicator81.setValue(data.getDebtLimitProCorLev());
		indicator81.setUnit("亿元");
		list2.add(indicator81);
		
		EconomieIndicator indicator82 = new EconomieIndicator();
		indicator82.setField(IndicatorConstants.debt_limit_pro_cor_lev_gen);
		indicator82.setFieldName("省本级政府债务限额一般债务(亿元)");
		
		BigDecimal val82 = data.getDebtLimitProCorLevGen();
		if(val82==null || Objects.equal(val82.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator82.setValue(null);
		}else{
			indicator82.setValue(val82);
		}
		//indicator82.setValue(data.getDebtLimitProCorLevGen());
		indicator82.setUnit("亿元");
		list2.add(indicator82);
		
		EconomieIndicator indicator83 = new EconomieIndicator();
		indicator83.setField(IndicatorConstants.debt_limit_pro_cor_lev_spec);
		indicator83.setFieldName("省本级政府债务限额专项债务(亿元)");
		
		BigDecimal val83 = data.getDebtLimitProCorLevSpec();
		if(val83==null || Objects.equal(val83.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator83.setValue(null);
		}else{
			indicator83.setValue(val83);
		}
		//indicator83.setValue(data.getDebtLimitProCorLevSpec());
		indicator83.setUnit("亿元");
		list2.add(indicator83);
		
		EconomieIndicator indicator84 = new EconomieIndicator();
		indicator84.setField(IndicatorConstants.whole_pro_gov_debt_bal);
		indicator84.setFieldName("全省政府债务余额(亿元)");
		
		BigDecimal val84 = data.getWholeProGovDebtBal();
		if(val84==null || Objects.equal(val84.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator84.setValue(null);
		}else{
			indicator84.setValue(val84);
		}
		//indicator84.setValue(data.getWholeProGovDebtBal());
		indicator84.setUnit("亿元");
		list2.add(indicator84);
		
		EconomieIndicator indicator85 = new EconomieIndicator();
		indicator85.setField(IndicatorConstants.whole_pro_gov_debt_bal_gen);
		indicator85.setFieldName("全省政府债务余额一般债务(亿元)");
		
		BigDecimal val85 = data.getWholeProGovDebtBalGen();
		if(val85==null || Objects.equal(val85.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator85.setValue(null);
		}else{
			indicator85.setValue(val85);
		}
		//indicator85.setValue(data.getWholeProGovDebtBalGen());
		indicator85.setUnit("亿元");
		list2.add(indicator85);
		
		EconomieIndicator indicator86 = new EconomieIndicator();
		indicator86.setField(IndicatorConstants.whole_pro_gov_debt_bal_spec);
		indicator86.setFieldName("全省政府债务余额专项债务(亿元)");
		
		BigDecimal val86 = data.getWholeProGovDebtBalSpec();
		if(val86==null || Objects.equal(val86.setScale(2, BigDecimal.ROUND_HALF_UP),new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))
				){
			indicator86.setValue(null);
		}else{
			indicator86.setValue(val86);
		}
		//indicator86.setValue(data.getWholeProGovDebtBalSpec());
		indicator86.setUnit("亿元");
		list2.add(indicator86);
		
		doc.setEconomieIndicators(list2);
		
		return doc;
	}
	
	//查找所有该地区下所地区(包括自身)
	public List<Long> totalAmountAreaUniCode(Long areaUniCode){
		//地区所有代码集合
		List<Long> areaUniCodeList = new ArrayList<>() ;		
		//areaUniCode 查找该地区下一级地区		
		String sql = "select area_uni_code as areaUniCode,area_chi_name as areaChiName "
				+ "\n\tfrom bond_ccxe.pub_area_code where sub_uni_code ="+areaUniCode ;					
		List<Map<String, Object>> nextList= jdbcTemplate.queryForList(sql);
		if(nextList.size()==0 || nextList == null){
			//areaUniCode表示区县一级
			areaUniCodeList.add(areaUniCode);
		}else{
			for(int i=0;i<nextList.size();i++){
				String sql2 = "select area_uni_code as areaUniCode ,area_chi_name as areaChiName"
						+"\n\tfrom bond_ccxe.pub_area_code where sub_uni_code ="+nextList.get(i).get("areaUniCode");		
				
				List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2);
				if(list2.size()==0 || list2 == null){
					//areaUniCode 为市一级   nextList封装区县一级  list2 为空
					areaUniCodeList.add((Long) nextList.get(i).get("areaUniCode"));				
				}else{
					//areaUniCode 为省一级   nextList封装市一级   list2 封装区县一级
					for(int k=0;k<list2.size();k++){
						areaUniCodeList.add((Long)list2.get(k).get("areaUniCode"));
						areaUniCodeList.add((Long)nextList.get(i).get("areaUniCode")) ;
					}
				}
			}
			areaUniCodeList.add(areaUniCode);
			
		}
		
		return areaUniCodeList;
	}
	
	
	//根据地区代码查找所有主体并判断主体是否为城投主体
	public List<Long> queryComUniCodeList(List<Long> areaUniCodes){
		List<Map<String, Object>> bondIssues = new ArrayList<>() ;
			
		if(areaUniCodes.size() != 0 && areaUniCodes != null){
			for(int i=0;i<areaUniCodes.size();i++){
				String sql = "select com_uni_code as comUniCode ,area_uni_code as areaUniCode from bond_ccxe.d_pub_com_info_2"
						+"\n\twhere isvalid = 1 and area_uni_code = "+areaUniCodes.get(i);
				
				List<Map<String, Object>> list= (List<Map<String, Object>>) jdbcTemplate.queryForList(sql);
				if(list.size()!=0 && list != null){
					bondIssues.addAll(list);
				}
				
			}
		}
		
		//判断主体是否是城投主体
		List<Long> list = new  ArrayList<>();
		if(bondIssues.size()!=0 && bondIssues != null){
			for(int k=0;k<bondIssues.size();k++){
				String sql = "select com_uni_code as comUniCode from dmdb.t_bond_com_ext where is_type_par='1' and com_uni_code= "+bondIssues.get(k).get("comUniCode");
				//Long comUniCode = (Long)jdbcTemplate.queryForObject(sql, Long.class);
				List<Map<String, Object>> list2= jdbcTemplate.queryForList(sql);
				
				for(Map<String, Object> map:list2){
					list.add((Long)map.get("comUniCode"));
				}
			}
		}
		
		return list;
	}
	
	
	
	//根据城投主体查找其发行的城投债劵并统计其数字
	public double queryBond(List<Long> comUniCodeList,BondAreaData data) throws ParseException{
		List<BondDetailDoc> bondDetailDocList = new ArrayList<>() ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(comUniCodeList!=null && comUniCodeList.size()>0){
			for(int i=0;i<comUniCodeList.size();i++){
				Query query = new Query();
				query.addCriteria(Criteria.where("comUniCode").is(comUniCodeList.get(i)));
				query.addCriteria(Criteria.where("newSize").gt(0));
				query.addCriteria(Criteria.where("munInvest").exists(true));
				
				if("年度".equals(data.getDataType())){	
					String time = data.getBondYear()+"-00-00";
					Date time2 = sdf.parse(time);
					query.addCriteria(Criteria.where("issStartDate").lte(time2));
				}else if("季度".equals(data.getDataType())){
					String time = "";
					if(data.getBondQuarter() == 1){
						time = data.getBondYear() +"-03-31";
					}else if(data.getBondQuarter() == 2){
						time = data.getBondYear() +"-06-30";
					}else if(data.getBondQuarter() == 3){
						time = data.getBondYear() +"-09-30";
					}else if(data.getBondQuarter() == 4){
						time = data.getBondYear() +"-12-31";
					}
					Date time2 = sdf.parse(time);
					query.addCriteria(Criteria.where("issStartDate").lte(time2));
				}else if("月度".equals(data.getDataType())){
					String time = "";
					if(data.getBondMonth()==1){
						time = data.getBondYear()+"-01-31";
					}else if(data.getBondMonth()==2){
						time = data.getBondYear()+"-02-30";
					}else if(data.getBondMonth()==3){
						time = data.getBondYear()+"-03-31";
					}else if(data.getBondMonth()==4){
						time = data.getBondYear()+"-04-30";
					}else if(data.getBondMonth()==5){
						time = data.getBondYear()+"-05-31";
					}else if(data.getBondMonth()==6){
						time = data.getBondYear()+"-06-30";
					}else if(data.getBondMonth()==7){
						time = data.getBondYear()+"-07-31";
					}else if(data.getBondMonth()==8){
						time = data.getBondYear()+"-08-30";
					}else if(data.getBondMonth()==9){
						time = data.getBondYear()+"-09-30";
					}else if(data.getBondMonth()==10){
						time = data.getBondYear()+"-10-31";
					}else if(data.getBondMonth()==11){
						time = data.getBondYear()+"-11-30";
					}else if(data.getBondMonth()==12){
						time = data.getBondYear()+"-12-31";
					}
					Date time2 = sdf.parse(time);
					query.addCriteria(Criteria.where("issStartDate").lte(time2));					
				}
								
				BondDetailDoc doc= mongoTemplate.findOne(query, BondDetailDoc.class);
				if(doc != null){
					bondDetailDocList.add(doc);
				}
			}
		}	
		
		
		
		
		//计算所有债劵的值
		double totalAmount = 0;
		double f = 0;
		if(bondDetailDocList.size()>0 && bondDetailDocList != null){
			for(BondDetailDoc doc:bondDetailDocList){
				totalAmount+= doc.getNewSize();
			}
		}
		
		if(totalAmount !=0){
			BigDecimal big = new BigDecimal(totalAmount);			
			f = big.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
					
		return f;
	}
	
	
	
}
