import cn.linkey.orm.dao.Rdb;
import cn.linkey.orm.dao.impl.RdbImpl;
import cn.linkey.orm.doc.Document;
import cn.linkey.orm.factory.BeanCtx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 测试lkengine-db
 * add by alibao
 * 2021-1-4 22:02:08
 */
public class jdbctest {

    private Connection conn = null;


    public static void main(String[] args) {

        jdbctest testObj = new jdbctest();
        // 获取conn对象
        Connection conn = testObj.getConn();

        // 初始化连接
        Rdb rdb = new RdbImpl(conn);
        BeanCtx.setConnection(conn);
        BeanCtx.setRdb(rdb);

        Rdb r2 = BeanCtx.getRdb();
        // 简单查询测试
        Document doc = r2.getDocumentBySql("select * from bpm_maindata where WF_OrUnid = '04899ff608b7004ca0081c003f3abe8506a4'");
        System.out.println(doc.toJson());
        
    }


    /**
     * 获取数据库连接
     * @return  conn
     */
    private Connection getConn() {
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/flowchart";
        String username = "root";
        String password = "1234";
        try {
            //加载驱动
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.conn = conn;
        return conn;
    }

    /**
     * 关闭连接
     */
    private void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
                e.printStackTrace();
            }
        }
    }

}
