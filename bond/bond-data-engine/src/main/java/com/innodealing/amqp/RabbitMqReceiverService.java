package com.innodealing.amqp;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.innodealing.amqp.config.MqConstants;
import com.innodealing.util.StringUtils;


@Component
public class RabbitMqReceiverService {
	
	@Autowired
	Gson gson;

	private final Log LOG = LogFactory.getLog(RabbitMqReceiverService.class);

	@RabbitListener(queues = MqConstants.MQ_QUEUE_BOND_CHANGE_EVENT)
	public void receiveBondChangeEvent(String queue) {
		LOG.info("RabbitMqReceiverService receive:" + queue);
	}
}