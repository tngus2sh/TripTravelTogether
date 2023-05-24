package com.ssafy.trip.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.trip.interceptor.JwtInterceptor;

@Configuration // 이 클래스가 Spring의 구성 클래스임을 나타냄
@MapperScan(basePackages = {"com.ssafy.**.mapper"}) // MyBatis의 매퍼 인터페이스들이 위치하는 패키지를 스캔하도록 지정
@EnableWebMvc // Spring MVC를 사용하는 웹 애플리케이션임을 나타냄
public class WebMvcConfiguration implements WebMvcConfigurer {
	/*
	 * WebMvcConfigurer : Web MVC 구성을 위한 인터페이스. 해당 클래스는 이 인터페이스를 구현하고 있으므로
	 * 웹 애플리케이션의 구성을 수정할 수 있는 메서드를 제공
	 */
	private final List<String> patterns = Arrays.asList("/board/**", "/hotplace/**", "/notice/**", "/plan/**", "/user/logout", "/user/modify", "/user/resign");
	private final List<String> excludePatterns = Arrays.asList("/board/list", "/hotplace/list", "/hotplace/top", "/notice/list", "/plan/list", "/plan/top", "/user/find", "user/email");
	
	private JwtInterceptor jwtInterceptor;
	
	public WebMvcConfiguration(JwtInterceptor jwtInterceptor) {
		super();
		this.jwtInterceptor = jwtInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// JwtInterceptor를 등록하고, patterns에 정의된 경로 패턴에 해당하는 요청에 대해 인터셉터가 동작하도록 실행
		//  excludePatterns에 정의된 경로 패턴에 해당하는 요청은 JwtInterceptor를 제외하고 처리된다.
		// 즉, excludePatterns에 정의된 경로인 /board/list, /hotplace/list, /notice/list, /plan/list에 대한 요청은 JwtInterceptor가 동작하지 않는다.
		registry.addInterceptor(jwtInterceptor)
		.addPathPatterns(patterns)
		.excludePathPatterns(excludePatterns);
	}



	/**
	 * CORS(Cross-Origin Resource Sharing) 설정을 추가한다. 
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//
		registry.addMapping("/**")
		.allowedOrigins("*")
//		.allowedOrigins("http://localhost:8080", "http://localhost:8081")
			.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
					HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
					HttpMethod.PATCH.name())
//			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
			.exposedHeaders("X-ACCESS-TOKEN")
			.maxAge(1800);
	}
	
	/**
	 * 특정 URL 경로에 대한 컨트롤러 등록
	 * "/" 경로에 대해 "index"뷰를 반환하는 컨트롤러
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	/**
	 * 정적 리소스의 핸들러를 등록
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//	Swagger UI 실행시 404처리
		//	Swagger2 일경우
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		// image 파일 경로
		// "/hotplace/image" 경로에 대한 요청을 로컬 파일 시스템의 특정 경로로 매핑하고, "/static/"경로를 classpath의 "/static/" 디렉토리와 매핑
		registry.addResourceHandler("/hotplace/image/**").addResourceLocations("file:///C:/springboot/upload/hotplace/image/");
	}
}
 