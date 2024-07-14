package com.sixplus.server.api.core.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@Import(value = { JwtTokenProvider.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class })
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/favicon.ico", "/assets/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(jwtAtuthenticationEntryPoint)
						.accessDeniedHandler(jwtAccessDeniedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/pub/**", "/api/auth/login","/api/auth/retoken", "/api/v1/auth/reLogin", "/",  "/health-check").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//		http
//			// .addFilterBefore(new TenantFilter(), AuthorizationFilter.class)
//			//	.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//			.csrf(AbstractHttpConfigurer::disable)
//			.httpBasic(Customizer.withDefaults())
//			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	@ConditionalOnMissingBean(UserDetailsService.class)
//	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//		String generatedPassword = "1234124";
//		return new InMemoryUserDetailsManager(User.withUsername("user")
//				.password(generatedPassword).roles("SAMPLE_ROLE").build());
//	}
//	// .password(generatedPassword).roles("ROLE_USER").build());
//
//	@Bean
//	@ConditionalOnMissingBean(AuthenticationEventPublisher.class)
//	DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
//		return new DefaultAuthenticationEventPublisher(delegate);
//	}
}

/// **
// *
// * @programName : SecurityConfig
// * @author : cwcho
// * @description : 스프링 시큐리티 설정
// */
// @Slf4j
// @Configuration
// @EnableWebSecurity
// @EnableGlobalAuthentication
// @ComponentScan(basePackages = {
// "co.kr.dsi.common.spring.*" })
// public class SecurityConfig {
// // private final Logger logger = LoggerFactory.getLogger(getClass());
//
// @Value("${spring.security.staticContents-url-patterns}")
// private String[] staticContentsUrlPatterns;
//
// @Value("${spring.security.permitAll-url-patterns}")
// private String[] permitAllUrlPatterns;
//
// @Value("${spring.security.fullyAuthenticated-url-patterns}")
// private String[] fullyAuthenticatedUrlPatterns;
//
// @Value("${spring.security.login.page}")
// private String loginPageUrl;
//
// @Value("${spring.security.login.processingUrl}")
// private String loginProcessingUrl;
//
// @Value("${spring.security.login.user.id-param-name}")
// private String loginUserIdParamName;
//
// @Value("${spring.security.login.user.pwd-param-name}")
// private String loginUserPwdParamName;
//
// @Value("${spring.security.login.successUrl}")
// private String loginSuccessUrl;
//
// @Value("${spring.security.logout.reqUrl}")
// private String logoutReqUrl;
//
// @Value("${spring.application.name}")
// private String applicationName;
//
// @Autowired
// private AuthenticationEntryPointImpl authenticationEntryPointImpl;
//
// @Autowired
// private AuthAccessDeniedHandler authAccessDeniedHandler;
//
// @Autowired
// private AuthLoginFailHandler authLoginFailureHandler;
//
// @Autowired
// private AuthLoginSuccessHandler authLoginSuccessHandler;
//
// @Autowired
// private AuthLogoutHandler authLogoutHandler;
//
// @Autowired
// private AuthLoginProvider authProvider;
//
// @Autowired
// private RoleService roleService;
//
// @Autowired
// private RoleDao roleDao;
//
// @Override
// public void configure(WebSecurity web) throws Exception {
// // 허용되어야 할 경로들, 특히 정적파일들(필요한경우만 설정)
// // web.ignoring().antMatchers("/resources/contents/**");
// web.ignoring().antMatchers(this.staticContentsUrlPatterns);
// }
//
// @Override
// protected void configure(HttpSecurity http) throws Exception {
// // ! 서버기동시 역할별 접근할 수 있는 API 들을 여기서 등록
// // example.. Ant Pattern 참고
// // ex) spEL 표현식 사용 가능
// // .antMatchers("/users/**").access("hasRole('ROLE_SYSTEM') and
// // hasIpAddress('192.168.1.2')")
// // ! 제어하고자 하는 URL을 작성해줘야 하며, 작성하지 않은 URL은 로그인만 되면 접근이 가능하게 된다 !
// // url 마지막이 파라미터 처리된 경우, 상위 url까지는 동일 권한으로 접근이 가능함.(기본 정책임)
//
// // 1. ROLE_SYSTEM 역할 이외의 역할을 할당한다.
// List<RoleApiAuthMapg> roleApiAuthMapgs =
/// roleDao.getRoleApiMapgForSpringSecurity();
// for (RoleApiAuthMapg roleApiAuthMapg : roleApiAuthMapgs) {
// String[] pathPtrnList = roleApiAuthMapg.getApiPathPtrn().split(",");
// http.authorizeRequests()
// .antMatchers(pathPtrnList).access(String.format("hasRole('%s')",
/// roleApiAuthMapg.getRoleId()));
// }
//
// // 2. 인증없이 접근할 수 있는 servlet URL
// http.authorizeRequests()
// .antMatchers(this.permitAllUrlPatterns).permitAll();
//
// // 3. 인증된 모든 역할의 사용자가 접근할 수 있는 URL
// // http.authorizeRequests()
// // .antMatchers(HttpMethod.GET,
// // fullyAuthenticatedUrlPatterns).fullyAuthenticated();
//
// // 4. 그밖에 특정되지 않은, 모든 URL 패턴에 대해서 ROLE_SYSTEM(최고 권한자)에 모두 할당해준다.
// // 어떠한 URL이든 최소한 하나 이상의 역할에 할당되어야 접근제어가 가능하다.
// http.authorizeRequests()
// .antMatchers("/**").access(String.format("hasRole('%s')",
/// ComConstantAuth.ROLE_TOP)); // ROLE_SYSTEM
//
// http.authorizeRequests().anyRequest().authenticated()
// .and()
// .formLogin()
// .loginPage(this.loginPageUrl) // 로그인 페이지(get)
// .loginProcessingUrl(this.loginProcessingUrl) // 로그인 처리 페이지(post)
// // .defaultSuccessUrl(this.loginSuccessUrl) // 인증완료 후 이동할 페이지
// .failureHandler(this.authLoginFailureHandler) // 인증실패시 처리할 handler
// .successHandler(this.authLoginSuccessHandler) // 인증성공시 처리할 handler
// .usernameParameter(this.loginUserIdParamName) // 로그인시 user id에 해당하는 파라미터의 명
// .passwordParameter(this.loginUserPwdParamName) // 로그인시 user password에 해당하는
/// 파라미터의 명
// .and()
// .exceptionHandling()
// .authenticationEntryPoint(authenticationEntryPointImpl)
// .accessDeniedHandler(authAccessDeniedHandler) // 인증되지 않은상태로 접근할 때 처리할 handler
// .and()
// .logout().logoutSuccessHandler(this.authLogoutHandler)
// .logoutRequestMatcher(new AntPathRequestMatcher(this.logoutReqUrl)) // 로그아웃을
/// 요청하는 url
// // .logoutSuccessUrl(this.logoutSuccessUrl) // 로그아웃 후 이동할 페이지
// // .invalidateHttpSession(true) // 로그아웃 요청시
// .and()
// .authenticationProvider(this.authProvider) // 로그인 요청 처리를 담당할 provider (본
/// 프로젝트에서는 컨트롤러에서 처리할것임)
// .csrf().disable(); // 위변조방지(본 서비스에서 제공한 페이지에서 요청한것인지를 체킹하는 csrf 파라미터를 추가로
/// 체그함) 사용유무 설정
// }
//
// private String getAccessString(String authRwd, String urlPattern) {
// return String.format("@authorizationChecker.check('%s','%s')", authRwd,
/// urlPattern);
// }
//
// }
