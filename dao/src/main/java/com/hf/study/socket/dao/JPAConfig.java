package com.hf.study.socket.dao;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author xiehongfei
 * @description
 * @date 2023/8/27 16:20
 */
@Configuration
@EnableJpaRepositories
@EntityScan
@EnableTransactionManagement
public class JPAConfig {

    @Bean
    public DataSource dataSource() {
        return new DruidDataSourceBuilder().build();
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
