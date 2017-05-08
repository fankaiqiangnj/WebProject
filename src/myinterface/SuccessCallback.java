package myinterface;

import java.io.IOException;

/**
 * Created by kail on 2017/4/27.
 */
public interface SuccessCallback {
    void success(int code,Object o);

    void error(int code,IOException e);

}