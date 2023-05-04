package cn.dnspod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * TODO
 *
 * @className: DnsPodApplication
 * @author: weixuan
 * @date: 2023/4/28 17:17
 **/
@SpringBootApplication
@EnableTransactionManagement
public class DnsPodApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DnsPodApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DnsPodApplication.class);
    }
}
