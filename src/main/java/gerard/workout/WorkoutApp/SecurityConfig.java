package gerard.workout.WorkoutApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> response.sendRedirect("/app"))
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
            .build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails demoUser = User.withDefaultPasswordEncoder()
            .username("mark")
            .password("password")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(demoUser);
    }
}
