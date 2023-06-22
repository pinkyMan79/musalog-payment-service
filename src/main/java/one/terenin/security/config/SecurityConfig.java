package one.terenin.security.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import one.terenin.security.filter.ExceptionFilter;
import one.terenin.security.filter.JwtAuthorizationFilter;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter authorizationFilter;
    private final ExceptionFilter filter;

    /*http.csrf().disable();
        http// -- common configuration
                //add custom JWT filter
                .exceptionHandling()
                .authenticationEntryPoint(exceptionInterceptorJwt())
            .and()
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .cors()
                .and()
    // сессию отключаем, так как теперь у нас всё хранится в JWT и оттуда мы тянем информацию
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/forum/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/file/**").permitAll()
            ;*/

    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c -> c.configure(http))
                .exceptionHandling(e -> e.authenticationEntryPoint(filter))
                .authorizeHttpRequests(req -> req.anyRequest().permitAll());
        return http.build();
    }

}
