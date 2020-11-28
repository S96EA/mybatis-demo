package cn.s96ea.mybatis_demo;

import cn.s96ea.mybatis_demo.entity.KV;
import cn.s96ea.mybatis_demo.mapper.KVMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class Main {
    @Test
    public void getSqlSessionFactoryByXML() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        /*
         * SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
         * 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。
         * 因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        /*
         * 每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
         * 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。
         * 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。
         * 如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。
         * 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。
         * 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。
         */
        try (var session = sqlSessionFactory.openSession()) {

            /*
             * 映射器实例应该在调用它们的方法中被获取，使用完毕之后即可丢弃。
             * 映射器实例并不需要被显式地关闭。尽管在整个请求作用域保留映射器实例不会有什么问题，
             * 但是你很快会发现，在这个作用域上管理太多像 SqlSession 的资源会让你忙不过来。
             * 因此，最好将映射器放在方法作用域内。
             */
            System.out.println(session.getMapper(KVMapper.class).selectKV("A1"));
        }

    }

    @Test
    public void queryByAnnotation() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (var session = sqlSessionFactory.openSession()) {
            System.out.println(session.getMapper(KVMapper.class).selectKVByK("A1"));
        }

    }

    @Test
    public void getSqlSessionFactoryByDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("", transactionFactory, ds);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(KVMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        try (var session = sqlSessionFactory.openSession()) {
            System.out.println(session.getMapper(KVMapper.class).selectKV("A1"));
        }
    }


    @Test
    public void queryList() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (var session = sqlSessionFactory.openSession()) {
            System.out.println(session.getMapper(KVMapper.class).selectByV(1));
        }

    }

    @Test
    public void selectByResultMap() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (var session = sqlSessionFactory.openSession()) {
            System.out.println(session.getMapper(KVMapper.class).selectKVByResultMap("A1"));
        }
    }

    @Test
    public void selectByResultMapAdvanced() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (var session = sqlSessionFactory.openSession()) {
            System.out.println(session.getMapper(KVMapper.class).selectKVListByResultMap());
        }
    }

    @Test
    public void insert() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        /*
         * 默认的 openSession() 方法没有参数，它会创建具备如下特性的 SqlSession：
         *
         * 事务作用域将会开启（也就是不自动提交）。
         * 将由当前环境配置的 DataSource 实例中获取 Connection 对象。
         * 事务隔离级别将会使用驱动或数据源的默认设置。
         * 预处理语句不会被复用，也不会批量处理更新。
         */
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(KVMapper.class).insert(new KV("A" + System.currentTimeMillis(), 1));
        }

    }
}
