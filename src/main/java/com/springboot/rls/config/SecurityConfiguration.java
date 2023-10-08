package com.springboot.rls.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.springboot.rls.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	// tạo đối tượng mã hóa mật khẩu
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/**	
	 * +là một AuthenticationProvider triển khai sử dụng UserDetailsService và PasswordEncoder để xác thực tên người dùng và 
	 * mật khẩu.
	 * + UserDetailsService tạo trình load UserDetails qua username của user. Lưu ý rằng method chỉ nhận một tham số: 
	 * username (không phải password).
	 * 
	 * + Interface UserDetails có các method để lấy (hashed) password và một method để lấy username.
	 * 
	 * QUY TRÌNH LÀM VIỆC:
	 * 	1)tạo một UsernamePasswordAuthenticationToken từ tên người dùng và mật khẩu được cung cấp.
	 *  2)chuyển mã thông báo này cho người quản lý xác thực.
	 *  3)Người quản lý nhà cung cấp sẽ ủy quyền xác thực DaoAuthenticationProvider bao gồm mã thông báo xác thực.
	 *  4)DaoAuthenticationProvider sử dụng dịch vụ tùy chỉnh UserDetailsService để lấy thông tin khách hàng từ cơ sở dữ liệu.
	 *  5)Khi xác thực thành công, đối tượng xác thực sẽ chứa đối tượng được điền đầy đủ bao gồm các chi tiết về cơ quan chức năng.
	 *  6)Giá trị trả về UsernamePasswordAuthenticationToken sẽ được đặt trên SecurityContextHolder bởi Bộ lọc xác thực.
	 */		
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	// build authentication
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	// config websecurity
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				 "/registration**",
	                "/js/**",
	                "/css/**",
	                "/img/**").permitAll()
		.anyRequest().authenticated() // authenticate() sẽ lấy username và password làm đầu vào và tìm kiếm người dùng trong cơ sở dữ liệu. Nếu người 
									// dùng được tìm thấy, phương thức này sẽ sử dụng PasswordEncoder để xác thực mật khẩu
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}

}