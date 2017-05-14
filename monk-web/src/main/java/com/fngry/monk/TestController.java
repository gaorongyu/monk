package com.fngry.monk;

import com.fngry.monk.biz.demo.message.rabbitmq.spring.MessageSender;
import com.fngry.monk.biz.demo.searchengin.elasticsearch.index.full.OrderInfoIndex;
import com.fngry.monk.biz.demo.searchengin.elasticsearch.search.OrderInfoQueryHandler;
import com.fngry.monk.biz.demo.searchengin.elasticsearch.search.request.OrderQueryRequest;
import com.fngry.monk.biz.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gaorongyu on 16/11/12.
 */
@RequestMapping("test")
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private OrderInfoIndex orderInfoIndex;

    @Autowired
    private OrderInfoQueryHandler orderInfoQueryHandler;


    @RequestMapping("first")
    @ResponseBody
    public String first(String passWord) {
        String passWordStored = "onlyfortest";

        if (!passWordStored.equals(passWord)) {
            return "first passWord Error!!!";
        }

        return "first Welcome";
    }

    @RequestMapping("second")
    @ResponseBody
    public String second(String passWord) {

        if (!"onlyfortest".equals(passWord)) {
            return "second passWord Error!!!";
        }

        return "second Welcome";
    }

    @RequestMapping("send_mq_message")
    @ResponseBody
    public String mqSend(String message) {
//        producer.pushMsg(message);
        messageSender.send(message);
        return "push message: " + message;
    }

    @RequestMapping("db_query")
    @ResponseBody
    public String dbQuery() {
        return testService.test().toString();
    }

    @RequestMapping("full_index_order")
    @ResponseBody
    public String fullIndexOrder() {
        orderInfoIndex.makeIndex();
        return "index order finished ";
    }

    @RequestMapping("index_query_order")
    @ResponseBody
    public Object indexQueryOrder(Integer orderId, String orderSn) {
        OrderQueryRequest queryRequest = new OrderQueryRequest();
        queryRequest.setOrderSn(orderSn);
        return orderInfoQueryHandler.queryOrderInfo(queryRequest);
    }

    @RequestMapping("index_query_order_request")
    @ResponseBody
    public Object indexQueryOrder(OrderQueryRequest queryRequest) {
        return orderInfoQueryHandler.queryOrderInfo(queryRequest);
    }

    @RequestMapping("plugin_query_plugin")
    @ResponseBody
    public Object queryPlugin(String bizType, String opType) {
        return testService.getPlugin(bizType, opType);
    }

}
