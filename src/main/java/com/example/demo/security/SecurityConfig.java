package com.example.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.demo.service.SecurityUserService;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	SecurityUserService securityUserService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		log.info("build Auth global");
		
		// 기본 계정 생성 DB Connection X
		// authAuthentication을 2번 선언하면 첫 번째꺼만 먹힘.
		/*auth.inMemoryAuthentication()
			.withUser("manager").password("{noop}1111").roles("MANAGER").and()
			.withUser("user").password("{noop}user").roles("GUEST").and()
			.withUser("admin").password("{noop}admin").roles("ADMIN");*/
		
		// DB Connetcion O
		String query1 = 
				"SELECT uid AS username, CONCAT('{noop}', upw) AS password, true enabled FROM tbl_members WHERE uid=?";
		
		String query2 = 
				"SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";
		
		auth.jdbcAuthentication()
			.dataSource(datasource)
			.usersByUsernameQuery(query1)
			.rolePrefix("ROLE_")
			.authoritiesByUsernameQuery(query2);
	}
	
	
	
	/*@Override
	//@Order(2)
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/**");
	}*/

		

	@Override
	//@Order(1)
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		log.info("security config..........");
		
		//http.authorizeRequests().antMatchers("/guest/**").permitAll();
		
		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER").and()
			.authorizeRequests().antMatchers("/admin/**").hasRole("AdDMIN").and()
			.formLogin().loginPage("/login").and()
			.exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
		
		// userService 사용
		http.userDetailsService(securityUserService);
		
		// http.formLogin().loginPage("/login");
		
		// http.exceptionHandling().accessDeniedPage("/accessDenied");
	}
}
