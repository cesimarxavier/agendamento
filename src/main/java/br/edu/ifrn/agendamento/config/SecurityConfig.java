package br.edu.ifrn.agendamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/info").permitAll() // Endpoint público
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails aluno = User.withDefaultPasswordEncoder()
            .username("aluno").password("123").roles("ALUNO").build();
        UserDetails professor = User.withDefaultPasswordEncoder()
            .username("professor").password("123").roles("PROFESSOR").build();
        UserDetails coordenador = User.withDefaultPasswordEncoder()
            .username("coordenador").password("123").roles("COORDENADOR").build();
        
        return new InMemoryUserDetailsManager(aluno, professor, coordenador);
    }
}