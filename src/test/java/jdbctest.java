import cn.linkey.orm.dao.Rdb;
import cn.linkey.orm.dao.impl.RdbImpl;
import cn.linkey.orm.doc.Document;
import cn.linkey.orm.doc.Documents;
import cn.linkey.orm.doc.impl.DocumentsUtil;
import cn.linkey.orm.factory.BeanCtx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

        // 测试查询
//        testSelect(r2);

        // 测试新增或修改
//        testAddAndUpdate(r2);

        // 测试xmlData字段保存和获取
          testxmlData(r2);

        // 关闭连接
        rdb.close(conn);
        
    }

    /**
     * 测试xmlData字段保存和获取
     * @param rdb Rdb类实例对象
     */
    private static void testxmlData(Rdb rdb) {

        // 1 保存直接操作document对象
        String WF_OrUnid = rdb.getNewUnid(); // WF_OrUnid，有则是修改；这里通过getNewUnid()新增；
        String sql1 = "SELECT * from bpm_modtasklist where WF_OrUnid ='" + WF_OrUnid + "'";
        Document doc = rdb.getDocumentBySql(sql1);

        // 通过doc.s("","") 将数据初始化到document对象中
        doc.s("createTime","这个字段将会保存到xmlData中" + LocalDateTime.now());
        doc.s("NodeName","测试节点名称");
        //  通过 doc.save() 保存数据到数据库中, 返回非负数表示存盘成功，否则存盘失败
        int i = doc.save();
        if(i > 0){
            System.out.println("存储成功！");
        }

        // 2 查询获取刚刚保存的字段
        String sql2 = "SELECT * from bpm_modtasklist where NodeName ='测试节点名称'";
        Document doc2 = rdb.getDocumentBySql(sql2);
        System.out.println("这个是刚刚保存的字段createTime： " + doc2.g("createTime"));

    }

    /**
     * 测试新增或修改
     * @param rdb Rdb类实例对象
     */
    private static void testAddAndUpdate(Rdb rdb) {

        int i = -1;

        // 方式一：通过document新增或修改
        String WF_OrUnid = rdb.getNewUnid(); // WF_OrUnid，有则是修改；这里通过getNewUnid()新增；
//        String sql1 = "SELECT * from bpm_modtasklist where WF_OrUnid ='9613041d0c3560454c09a9606214073b77e4'";
        String sql1 = "SELECT * from bpm_modtasklist where WF_OrUnid ='" + WF_OrUnid + "'";
        Document doc = rdb.getDocumentBySql(sql1);


        // 通过doc.s("","") 将数据初始化到document对象中
        doc.s("userid","zhangsan");
        doc.s("NodeName","测试节点名称");
        //  通过 doc.save() 保存数据到数据库中, 返回非负数表示存盘成功，否则存盘失败
        i = doc.save();

        if(i > 0){
            System.out.println("存储成功！");
        }

        // 方式二：通过执行SQL新增或修改
        String sql2 = "INSERT into bpm_modtasklist(WF_OrUnid,Nodeid,NodeName) VALUES('"+WF_OrUnid+"', '这个是字段idT0001','测试节点名称')";
        i = rdb.execSql(sql2);

        if(i > 0){
            System.out.println("存储成功！");
        }

    }

    /**
     * 测试查询
     * @param rdb Rdb类实例对象
     */
    private static void testSelect(Rdb rdb) {

        // 查询一条数据并打印
        Document doc = rdb.getDocumentBySql("select * from bpm_maindata where WF_OrUnid = '04899ff608b7004ca0081c003f3abe8506a4'");
        System.out.println(doc.toJson());

        // 查询多条数据并打印
        Document[] docs = rdb.getAllDocumentsBySql("select * from bpm_maindata");
        System.out.println(DocumentsUtil.dc2json(docs,""));

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
