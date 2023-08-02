//package ru.smartjava.config;
//
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import ru.smartjava.test.Singleton;
//
//@Configuration
//public class SingletonBeanConfig {
//
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public Singleton singletonBean() {
//        return new Singleton();
//    }
//
//}