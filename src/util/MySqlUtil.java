package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kail on 2017/5/9.
 */
public class MySqlUtil {
    //驱动程序名
    public static String driver = "com.mysql.jdbc.Driver";
    //url数据库名
    public static String url = "jdbc:mysql://182.254.227.206:3306/mysql?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    //用户名
    public static String user = "root";
    //密码
    public static String password = "fankaiqiang";

    public static Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        if (!conn.isClosed())
            return conn;
        else
            return null;
    }


}
