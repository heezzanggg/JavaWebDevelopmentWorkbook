package org.zerock.b02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//EnableJpaAuditing: @AuditingEntityListener 활성화 시키기위한 어노테이션
public class B02Application {

    public static void main(String[] args) {
        SpringApplication.run(B02Application.class, args);
    }

}
