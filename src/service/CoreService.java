package service;

import model.TextMessage;
import util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by kail on 2017/4/26.
 */
public class CoreService {

    /**
     * 处理微信发送的请求
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            //默认返回的文本
            String respConternt = "请求处理异常,请稍后尝试";
            //xml 请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            //发送方账号(open_id)
            String fromUserName = requestMap.get("FromUserName");
            //公众号
            String toUserName = requestMap.get("ToUserName");
            //消息类型
            String msgType = requestMap.get("MsgType");


            //回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
                respConternt = "您发送的是文本消息";
            } else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_EVENT)) {
                //事件类型
                String eventType = requestMap.get("Event");
                //订阅
                if (eventType.equals(MessageUtil.RESP_MESSAGE_TYPE_SUBSCRIBE)) {
                    respConternt = "感谢您的关注";
                }else if (eventType.equals(MessageUtil.RESP_MESSAGE_TYPE_CLICK)){
                    String enentKey = requestMap.get("EventKey");
                    if (enentKey.equals(11)){
                        respConternt = "天气预报已被点击";
                    }
                }

            }
            textMessage.setContent(respConternt);
            respMessage = MessageUtil.textMessageToXml(textMessage);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }
}
