package com.innodealing.amqp;

import com.google.gson.Gson;
import com.innodealing.consts.RabbitmqConstants;
import com.innodealing.consts.SocketIOConstants;
import com.innodealing.json.consts.Constants;
import com.innodealing.json.socketio.ToAllSocketIoMsg;
import com.innodealing.json.socketio.ToMembersSocketIoMsg;
import com.innodealing.model.mongo.dm.BondDiscoveryAbnormalDealDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryAbnormalQuoteDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryTodayDealDetailDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryTodayQuoteDetailDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQReceiverService {
    private final static Logger logger = LoggerFactory.getLogger(MQReceiverService.class);

    private @Autowired
    MQSenderService senderService;

    private @Autowired
    Gson gson;

    @RabbitListener(queues = RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_DEAL)
    public void receiveBondDiscoveryTodayDeal(BondDiscoveryTodayDealDetailDoc todayDealDoc) {
        this.sendMsg2SocketIOQueue(todayDealDoc, SocketIOConstants.BOND_DISCOVERY_TODAY_DEAL);
    }

    @RabbitListener(queues = RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE)
    public void receiveBondDiscoveryTodayQuote(BondDiscoveryTodayQuoteDetailDoc todayQuoteDoc) {
        this.sendMsg2SocketIOQueue(todayQuoteDoc, SocketIOConstants.BOND_DISCOVERY_TODAY_QUOTE);
    }

    @RabbitListener(queues = RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_AP_DEAL)
    public void receiveBondDiscoveryAbnormalDeal(BondDiscoveryAbnormalDealDoc abnormalDealDoc) {
        this.sendMsg2SocketIOQueue(abnormalDealDoc, SocketIOConstants.BOND_DISCOVERY_AP_DEAL);
    }

    @RabbitListener(queues = RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_AP_QUOTE)
    public void receiveBondDiscoveryAbnormalQuote(BondDiscoveryAbnormalQuoteDoc abnormalQuoteDoc) {
        this.sendMsg2SocketIOQueue(abnormalQuoteDoc, SocketIOConstants.BOND_DISCOVERY_AP_QUOTE);
    }

    /**
     * 将消息发送到SocketIO队列
     * @param obj
     */
    private void sendMsg2SocketIOQueue(Object obj, String msgType) {
        ToAllSocketIoMsg allMsg = new ToAllSocketIoMsg();
        allMsg.setData(obj);
        allMsg.setMessageType(msgType);
        allMsg.setNamespaces(Constants.SOCKETIO_NAMESPACE_PORTFOLIO);
        senderService.sendMsg2SocketIO(gson.toJson(allMsg));
    }
}
