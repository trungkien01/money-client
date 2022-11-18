package com.credit.webcredit.security;

import com.credit.webcredit.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value(value = "${time.token.remember.me}")
	private String TIME_TOKEN;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private DataSource dataSource;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web){
		web.ignoring().antMatchers("/registration/**", "/favicon.ico", "/resources/**", "/error",
				"/users/js/**", "/users/css/**", "/users/img/**", "/users/assets/**", "/users/login/**", "/users/logoanhphc/**", "/profile/resetPassword", "/profile/sendEmailPassword");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/index", "/").hasAnyRole("ANONYMOUS", "USER")
				.antMatchers("/users/**", "/profile/**").hasRole("PRIEMIUM")
				.antMatchers("/profile/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/profile/myInformation")
				.failureUrl("/login?error")
				.permitAll()
				.and()
				.rememberMe()
				.key("remember-users")
				.rememberMeParameter("remember-me")
				.rememberMeCookieName("rememberlogin")
				.tokenValiditySeconds(Integer.parseInt(TIME_TOKEN))
				.tokenRepository(tokenRepository())
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "rememberlogin")
				.permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/403")
		;
	}
	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
}
