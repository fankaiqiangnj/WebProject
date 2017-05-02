package impl;

import com.google.gson.Gson;
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
public class MenuMangerCallBackImpl implements SuccessCallback{
    @Override
    public void success(int code, Object o) {
        switch (code){
            case 0:
                if (o instanceof MemunBack){
                    System.out.print(((MemunBack) o).getErrcode()+((MemunBack) o).getErrmsg());
                }

                break;
            case 1:

                break;

        }

    }

    @Override
    public void error(int code, IOException e) {
        switch (code){
            case 0:

                break;
        }

    }
}
