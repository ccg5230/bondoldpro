package com.innodealing.config;

import com.innodealing.listener.JobCompletionNotificationListener;
import com.innodealing.model.mongo.dm.ComRiskComInfo;
import com.innodealing.model.mongo.dm.ComRiskDoc;
import com.innodealing.processor.ComRiskRecordProcessor;
import com.innodealing.quartz.QuartzConfiguration;
import com.innodealing.reader.ComRiskReader;
import com.innodealing.tasklet.ComRiskRefreshTasklet;
import com.innodealing.writer.ComRiskWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableBatchProcessing
@Import({ QuartzConfiguration.class })
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	// Transaction
	@Autowired
	public DataSourceTransactionManager dataSourceTransactionManager;

	// processor
	@Bean
	public ItemProcessor<ComRiskComInfo, ComRiskDoc> processor() {
		return new ComRiskRecordProcessor();
	}

	// reader
	@Bean
	public ItemReader<ComRiskComInfo> reader() {
		return new ComRiskReader().reader();
	}

	// writer
	@Bean
	public ItemWriter<ComRiskDoc> writer() {
		return new ComRiskWriter();
	}

	// JobCompletionNotificationListener (File loader)
	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	// Configure job step
	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("comRiskJob").incrementer(new RunIdIncrementer()).listener(listener()) // 任务监听
				.flow(ComRiskRefreshStep()).next(comRiskStep()).end().build();
	}

	@Bean(name = "comRiskRefresh")
	public ComRiskRefreshTasklet comRiskRefreshTasklet() {
		return new ComRiskRefreshTasklet();
	}

	@Bean(name = "ComRiskRefreshStep")
	public Step ComRiskRefreshStep() {
		return stepBuilderFactory.get("ComRiskRefreshStep").tasklet(comRiskRefreshTasklet()).allowStartIfComplete(true)
				.transactionManager(dataSourceTransactionManager).build();
	}

	@Bean(name = "comRiskStep")
	public Step comRiskStep() {
		return stepBuilderFactory.get("comRiskStep").allowStartIfComplete(true)
				.<ComRiskComInfo, ComRiskDoc> chunk(2000).reader(reader()).processor(processor()).writer(writer())
				.faultTolerant().retry(Exception.class) // 重试
				.noRetry(ParseException.class).retryLimit(1) // 每条记录重试一次
				// .listener(new RetryFailuireItemListener()) // 重试监听
				// .skip(Exception.class).skipLimit(200) // 一共允许跳过200次异常
				// .taskExecutor(new SimpleAsyncTaskExecutor()) // 设置并发方式执行
				// .throttleLimit(10) // 并发任务数为 10,默认为4
				.transactionManager(dataSourceTransactionManager).build();
	}

}
