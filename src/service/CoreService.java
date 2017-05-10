package service;

import model.TextMessage;
import util.MessageUtil;
import util.MySqlUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
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
            Map requestMap = MessageUtil.parseXml(request);

            //发送方账号(open_id)
            String fromUserName = (String) requestMap.get("FromUserName");
            //公众号
            String toUserName = (String) requestMap.get("ToUserName");
            //消息类型
            String msgType = (String) requestMap.get("MsgType");


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
                String eventType = (String) requestMap.get("Event");
                //订阅
                if (eventType.equals(MessageUtil.RESP_MESSAGE_TYPE_SUBSCRIBE)) {
                    respConternt = "感谢您的关注";
                } else if (eventType.equals(MessageUtil.RESP_MESSAGE_TYPE_CLICK)) {
                    String enentKey = (String) requestMap.get("EventKey");
                    if (enentKey.equals("11"))
                        respConternt = "天气已被点击";
                    if (enentKey.equals("12")) {
                        String sql = "select * from locationinfo where open_id = \"" + fromUserName + "\";";
                        respConternt = select(sql);
                        MySqlUtil.close();
                    }

                } else if (eventType.equals(MessageUtil.RESP_MESSAGE_TYPE_LOCATION)) {
                    String sql = "INSERT INTO locationinfo(open_id,Latitude,Longitude,Precisiones) VALUES(\"" +fromUserName + "\",\"" +
                            requestMap.get("Latitude") + "\",\"" + requestMap.get("Longitude") + "\",\"" + requestMap.get("Precision") + "\")ON DUPLICATE KEY UPDATE " +
                            "Latitude=\"" + requestMap.get("Latitude") + "\",Longitude=\"" + requestMap.get("Longitude") + "\",Precisiones=\"" + requestMap.get("Precision") + "\";";
                    MySqlUtil.change(sql);
                    MySqlUtil.close();
                    respConternt = "";
                }

            }
            if (!respConternt.equals("")) {
                textMessage.setContent(respConternt);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    public static String select(String sql) throws SQLException {
        String id = "";
        String Latitude = "";
        String Longitude = "";
        String Precisiones = "";
        ResultSet ret = MySqlUtil.select(sql);
        while (ret.next()) {
            id = ret.getString(1);
            Latitude = ret.getString(2);
            Longitude = ret.getString(3);
            Precisiones = ret.getString(4);
        }
        String s = "用户id:" + id + "经度:" + Longitude + "纬度:" + Latitude + "地理位置精度:" + Precisiones;

        return s;
    }
}
