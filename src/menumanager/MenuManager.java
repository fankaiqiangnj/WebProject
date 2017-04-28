package menumanager;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import impl.MenuMangerCallBackImpl;
import model.ButtonModel;
import model.MemunBack;
import model.Token;
import myinterface.SuccessCallback;
import util.MessageUtil;
import util.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kail on 2017/4/28.
 */
public class MenuManager {
    public static void getToken() {
        if (MessageUtil.access_token.equals("")) {
            Request request = new Request.Builder().url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx01dea3921733e48f&secret=0c04a6505e3c71cb41eb34f6694b8af0").build();
            try {
                Gson gson = new Gson();
                Response response =OkHttpUtil.execute(request);
                if (response.isSuccessful()){
                  Token TokenModel = gson.fromJson(response.body().charStream(), Token.class);
                    MessageUtil.access_token = TokenModel.getAccess_token();
                    MessageUtil.accessTokenSize++;
                    if (MessageUtil.menuSize==0){
                        creatMenu();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    static void creatMenu(){
        ButtonModel buttonModel = new ButtonModel();
        List<ButtonModel.ButtonBean> list = new ArrayList<>();
        list.add( new ButtonModel.ButtonBean("11","天气资讯","click"));
        list.add( new ButtonModel.ButtonBean("12","位置信息","click"));
        buttonModel.setButton(list);
        try {
            Gson gson = new Gson();
            OkHttpUtil.post(MessageUtil.memu_creat_url+MessageUtil.access_token, gson.toJson(buttonModel), new MenuMangerCallBackImpl(), 0, MemunBack.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}