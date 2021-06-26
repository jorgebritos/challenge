package com.cliente.seguridad;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cliente.util.LoginSuccessMessage;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	private LoginSuccessMessage successMessage;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**", "/index").permitAll()
		.antMatchers("/views/characters/").hasAnyRole("USER")
		.antMatchers("/views/characters/details**").hasAnyRole("USER")
		.antMatchers("/views/characters/create").hasAnyRole("ADMIN")
		.antMatchers("/views/characters/save").hasAnyRole("ADMIN")
		.antMatchers("/views/characters/edit/**").hasAnyRole("ADMIN")
		.antMatchers("/views/characters/delete/**").hasAnyRole("ADMIN")
		
		.antMatchers("/views/movies/").hasAnyRole("USER")
		.antMatchers("/views/movies/details**").hasAnyRole("USER")
		.antMatchers("/views/movies/create").hasAnyRole("ADMIN")
		.antMatchers("/views/movies/save").hasAnyRole("ADMIN")
		.antMatchers("/views/movies/edit/**").hasAnyRole("ADMIN")
		.antMatchers("/views/movies/delete/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successMessage)
		.loginPage("/auth/login").permitAll()
		.and()
		.logout().permitAll();
	}

	@Autowired
	public void configurerSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passEncoder)
				.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
				.authoritiesByUsernameQuery(
						"SELECT u.username, r.rol FROM roles r INNER JOIN users u ON r.user_id=u.id "
								+ "WHERE u.username = ?");
	}

}
