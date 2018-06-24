package io.xiaoyaoyou.xmall.core.common.ds;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("file:/xmall/conf/core/jdbc.properties")
@MapperScan(basePackages = InternalDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "internalSqlSessionFactory")
public class InternalDataSourceConfig {
    static final String PACKAGE = "io.xiaoyaoyou.xmall.core.**.dao";
    static final String MAPPER_LOCATION = "classpath*:dao/*Mapper.xml";

    @Bean(name = "internalDataSource")
    @Qualifier("internalDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource internalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "internalTransactionManager")
    @Primary
    public DataSourceTransactionManager internalTransactionManager(@Qualifier("internalDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "internalSqlSessionFactory")
    @Primary
    public SqlSessionFactory internalSqlSessionFactory(@Qualifier("internalDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(InternalDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        return sessionFactory.getObject();
    }

    @Bean(name = "internalJdbcTemplate")
    public JdbcTemplate internalJdbcTemplate(@Qualifier("internalDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}