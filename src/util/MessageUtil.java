package util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import model.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kail on 2017/4/26.
 */
public class MessageUtil {

    public static String access_token = "";

    /**
     * 文本消息
     */
    public static  final  String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 事件类型,订阅
     */
    public static  final  String RESP_MESSAGE_TYPE_EVENT = "event";
    /**
     * 事件类型,订阅
     */
    public static  final  String RESP_MESSAGE_TYPE_SUBSCRIBE = "subscribe";
    /**
     * 事件类型,订阅
     */
    public static  final  String RESP_MESSAGE_TYPE_UNSUBSCRIBE = "subscribe";

    /**
     * 事件类型,订阅
     */
    public static  final  String RESP_MESSAGE_TYPE_CLICK = "CLICK";



    private static XStream xStream = new XStream(new XppDriver(){
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out){
              //对所有节点增加cdata标记
                boolean cdata = true;
                @SuppressWarnings("unchecked")
                public void startNode(String name,Class clazz){
                    super.startNode(name,clazz);

                }
                protected void writeText(QuickWriter writer,String text){
                    if (cdata){
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else {
                        writer.write(text);
                    }

                }
            };
        }
    });


    /**
     * 解析微信发来的请求
     *
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> parseXml(HttpServletRequest request)throws Exception{

        Map<String,String> map =new HashMap<>();
        //从request 取得输入流
        InputStream inputStream = request.getInputStream();
        //读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        //获得根元素
        Element root = document.getRootElement();
        //得到根元素子节点
        List<Element> elementList = root.elements();
        //遍历所有子节点
        for (Element e:elementList)
            map.put(e.getName(),e.getText());

        //释放资源
        inputStream.close();
        inputStream = null;
        return map;

    }

    /**
     *
     * 文本消息转成xml
     */
    public static String textMessageToXml(TextMessage textMessage){
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);

    }

}
