package util;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import myinterface.SuccessCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by kail on 2017/4/27.
 */
public class OkHttpUtil {


    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果
     *
     * @param request
     */
    public static void enqueue(Request request, final SuccessCallback mCallBackLinster, final int code, final Class clazz) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response arg0) throws IOException {
                Gson gson = new Gson();
                mCallBackLinster.success(code, gson.fromJson(arg0.body().toString(), clazz));

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                mCallBackLinster.error(code, arg1);

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * post 提交json
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void post(String url, String json, final SuccessCallback mCallBackLinster, final int code, final Class clazz) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpUtil.enqueue(request, mCallBackLinster, code, clazz);

    }


}
