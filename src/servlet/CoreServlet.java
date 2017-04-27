package servlet;

import com.squareup.okhttp.Request;
import model.Token;
import myinterface.SuccessCallback;
import service.CoreService;
import util.MessageUtil;
import util.OkHttpUtil;
import util.SignUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 类名: CoreServlet </br>
 * 描述: 来接收微信服务器传来信息 </br>
 * 开发人员： souvc</br>
 * 创建时间：2015-9-29 </br>
 * 发布版本：V1.0 </br>
 */
public class CoreServlet extends HttpServlet implements SuccessCallback {


    private static final long serialVersionUID = 4323197796926899691L;

    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
           if (MessageUtil.access_token.equals("")) {
               Request request1 = new Request.Builder().url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx01dea3921733e48f&secret=0c04a6505e3c71cb41eb34f6694b8af0").build();
               OkHttpUtil.enqueue(request1, this, 0, Token.class);
           }
            out.print(echostr);
        }

        out.close();
        out = null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO 消息的接收、处理、响应
        //设置请求,响应编码,防止乱码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //调用核心业务类接收消息,处理消息
        String respMessage = CoreService.processRequest(request);
        //响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();

    }

    @Override
    public void success(int code, Object o) {
        if (o instanceof Token){
            Token model = (Token) o;
            if (!model.getAccess_token().equals("")){
                MessageUtil.access_token = model.getAccess_token();
            }


        }

    }

    @Override
    public void error(int code, IOException e) {

    }
}