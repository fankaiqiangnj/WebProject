package menumanager;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import impl.MenuMangerCallBackImpl;
import model.ButtonModel;
import model.MemunBack;
import model.Token;
import util.MessageUtil;
import util.MySqlUtil;
import util.OkHttpUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kail on 2017/4/28.
 */
public class MenuManager {
    public static void main(String[] args) {
//        getToken();
//        String sql = "INSERT INTO locationinfo(open_id,Latitude,Longitude,Precisiones) VALUES(\"sdfsdf\",\"23423442\",\"2345234\",\"234234\") ON DUPLICATE KEY UPDATE Latitude=\"SDF\",Longitude=\"SDF\",Precisiones=\"SDF\";";
//        try {
//            MySqlUtil.change(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


    }

    public static void getToken() {
        if (MessageUtil.access_token.equals("")) {
            Request request = new Request.Builder().url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx01dea3921733e48f&secret=0c04a6505e3c71cb41eb34f6694b8af0").build();
            try {
                Gson gson = new Gson();
                Response response = OkHttpUtil.execute(request);
                if (response.isSuccessful()) {
                    Token TokenModel = (Token) gson.fromJson(response.body().charStream(), Token.class);
                    MessageUtil.access_token = TokenModel.getAccess_token();
                        creatMenu();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    static void creatMenu() {
        ButtonModel buttonModel = new ButtonModel();
        List list = new ArrayList();
        list.add(new ButtonModel.ButtonBean("click", "天气资讯", "11"));
        list.add(new ButtonModel.ButtonBean("click", "位置信息", "12"));
        buttonModel.setButton(list);
        try {
            Gson gson = new Gson();
            System.out.println(gson.toJson(buttonModel));
            OkHttpUtil.post(MessageUtil.memu_creat_url + MessageUtil.access_token, gson.toJson(buttonModel), new MenuMangerCallBackImpl(), 0, MemunBack.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
