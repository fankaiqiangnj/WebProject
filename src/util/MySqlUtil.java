package util;

import java.sql.*;

/**
 * Created by kail on 2017/5/9.
 */
public class MySqlUtil {
    //url数据库名
    public static String url = "jdbc:mysql://182.254.227.206:3306/mysql?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    //用户名
    public static String user = "root";
    //密码
    public static String password = "fankaiqiang";

    public static Connection conn = null;

    public static PreparedStatement preparedStatement;
    public static ResultSet ret;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据增删改
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public static int change(String sql) throws SQLException {
        int i = 0;
        if (conn == null)
            conn = DriverManager.getConnection(url, user, password);
        preparedStatement = conn.prepareStatement(sql);
        i = preparedStatement.executeUpdate();
        return i;//1执行成功,0执行失败
    }

    /**
     * 查询结果集
     * @param sql
     * @return
     * @throws SQLException
     */
    public static ResultSet select(String sql) throws SQLException {
        if (conn == null)
            conn = DriverManager.getConnection(url, user, password);
        preparedStatement = conn.prepareStatement(sql);
        ret = preparedStatement.executeQuery();
        return ret;
    }


    /**
     * 关闭数据库
     */
    public static void close() {
        if (conn != null && preparedStatement != null) {
            try {
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    public static Connection getConn() throws SQLException {
        if (conn == null)
            conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
