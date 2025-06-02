package com.example.petclinic2.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

        @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              // Вимкнення CSRF для API-запитів
              .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/auth/**"))
              .authorizeHttpRequests(auth-> auth
                      // Публічні ресурси
                      .requestMatchers("/login", "/register", "/auth/**", "/css/**", "/js/**").permitAll()
                      // HTML-інтерфейси
                      .requestMatchers("/user/**").hasRole("USER")
                      .requestMatchers("/admin/**").hasRole("ADMIN")
                      // ДОБАВЛЯЄМО ДОСТУП ДО КОНТРОЛЕРІВ ВЕТКЛІНІКИ:
                      .requestMatchers(HttpMethod.GET, "/pets/**", "/visits/**", "/vets/**").hasAnyRole("USER", "ADMIN")
                      .requestMatchers(HttpMethod.POST, "/pets/**", "/visits/**", "/vets/**").hasRole("ADMIN")
                      // API-запити
                      .requestMatchers("/api/user/**").hasRole("USER")
                      .requestMatchers("/api/admin/**").hasRole("ADMIN")


                      // все інше – автентифікація
                      .anyRequest().authenticated()
              )
              // Форма логіну
              .formLogin(form -> form
                      .loginPage("/login")
                      .defaultSuccessUrl("/default", true)
                      .permitAll()
              )
              //  Basic auth (для REST-тестування)
              .httpBasic(Customizer.withDefaults())
              .logout(logout -> logout
               .logoutUrl("/logout")
              .logoutSuccessUrl("/login?logout")
              .permitAll()
              )
              //  Обробка відмов доступу
              .exceptionHandling(ex -> ex
                      .accessDeniedHandler((request, response, accessDeniedException) ->
                              response.sendRedirect("/access-denied"))
              )
              // Сесії тільки при потребі (для HTML)
              .sessionManagement(sess -> sess
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authenticationProvider(daoAuthenticationProvider());
            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(new RequestValidationFilter(), UsernamePasswordAuthenticationFilter.class); // додаємо ПІСЛЯ
            return http.build();
  }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()  {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }



    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

  @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

}
