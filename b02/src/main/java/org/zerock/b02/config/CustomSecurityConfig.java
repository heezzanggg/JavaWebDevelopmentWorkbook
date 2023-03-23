package org.zerock.b02.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.b02.security.CustomUserDetailsService;
import org.zerock.b02.security.handler.Custom403Handler;

import javax.sql.DataSource;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
//EnableGlobalMethodSecurity의 prePostEnabled 속성
// : 원하는곳에 @PreAuthorize or PostAuthorize 어노테이션 이용해서 사전 혹은 사후의 권한 체크 가능
public class CustomSecurityConfig {

    //주입필요
    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //CustomeUserDetailsService 정상적으로 동작하기 위해 bean 지정 후,
        //CustomeUserDetailsService에 PasswordEncoder 주입
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        log.info("-------configure-------");

        //커스텀 로그인 페이지(loginPage("/member/login")), 로그인을 진행하는 설정
        http.formLogin().loginPage("/member/login"); //로그인이 필요한 경우 '/member/login' 경로로 자동 리다이렉트
        //CSRF 토큰 비활성화 => username, password라는 파라미터만으로 로그인 가능
        http.csrf().disable();

        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*30);

        //remember-me 쿠키 생성할 때 쿠키의 값을 인코딩하기 위한 key값과 필요한 정보를 저장하는 tokenRepository 지정.
        //코드상에서는 persistentTokenRepository 메소드를 이용하여 이를 처리

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new Custom403Handler();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //정적자원들은 스프링 시큐리티 적용에서 제외
        log.info("-------web configure-------");

        return (web)->web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //remember-me기능 설정
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
    //persistentTokenRepository 메소드 이용해서 toeknRepository를 지정
    //tokenRepository는 remember-me쿠키를 생성할 때는 쿠키의 값을 인코딩하기위한 키값과 필요한 정보를 저장하는 곳



}
