package com.innodealing.engine.ccxe;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.innodealing.model.dm.BondCcxeDataChangeEvent;

@Component
public class BondCcxeDataChangeEventChannel {
	private static final Logger LOG = LoggerFactory.getLogger(BondCcxeDataChangeEventChannel.class);
	
    @Autowired
    private  ApplicationEventPublisher inproPublisher;
   
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    
    @Autowired
    private Gson gson;
    
    private static final Integer RABBIT_PREFETCH_COUNT = 7;
    
    private static final String MQ_DEFAULT_EXCHANGE = "message";
    
    private static final String MQ_QUEUE_BOND_CHANGE_EVENT = "queue.bond_change_event";

    
    public void sendCreateEvents(String tableName, Object obj){
        BondCcxeDataChangeEvent event 
            = new BondCcxeDataChangeEvent(new Date(), tableName, BondCcxeDataChangeEvent.ACT_UPDATE, obj);
        inproPublisher.publishEvent(event);
        sendRabbitmq(gson.toJson(event));
    }
    
    public void sendUpdateEvents(String tableName, Object obj){
        BondCcxeDataChangeEvent event 
            = new BondCcxeDataChangeEvent(new Date(), tableName, BondCcxeDataChangeEvent.ACT_CREATE, obj);
        inproPublisher.publishEvent(event);
        sendRabbitmq(gson.toJson(event));
    }
    
    private void sendRabbitmq(final String json) {
        this.rabbitMessagingTemplate.convertAndSend(MQ_DEFAULT_EXCHANGE,
                MQ_QUEUE_BOND_CHANGE_EVENT, json);
    }
 
}
