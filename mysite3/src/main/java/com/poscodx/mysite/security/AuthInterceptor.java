package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		
		// 1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우 (정적자원, /assets/**, mapping이 안되어있는 URL)
			return true;
		}
		
		// 2. casting
		// casting 하는 이유 == 이 안에 어노테이션을 확인하는 정보가 담겨있어서
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 가져오기 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. Handler Method에 @Auth가 없으면 Type(Class)에 붙어있는지 확인
		if(auth == null) {
			auth = handlerMethod
					.getMethod()
					.getDeclaringClass()
					.getAnnotation(Auth.class);
		}
		
		// 5. Type이나 Method에 @Auth가 없는 경우
		if(auth == null) {
			// 없으니까 그냥 뒤에 있는 핸들러 실행, 동작이 필요 없음 
			return true;
		}
		
		// 여기까지 왔다는건 @Auth가 존재한다는 이야기
		// 6. @Auth가 붙어있기 때문에 인증 (Authentication) 확인 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 만약에 authUser가 null이라면 == 인증이 안되어있는 경우
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;	// 뒤에 핸들러가 있는 상황이므로 뒤로 못가게 하기 
		}
		
		// 7. 권한 (Authorization) 체크를 위해 @Auth의 role 가져오기 ("USER", "ADMIN")
		String role = auth.role();
		
		// 8. @Auth role이 "USER"인 경우, authUser의 role은 상관없다.
		if("USER".equals(role)) {	
			// role이 USER면 다 상관없이 기능 사용 가능
			return true;
		}
		
		// 9. @Auth의 role이 "ADMIN"인 경우, authUser의 role은 반드시 "ADMIN"
		if(!"ADMIN".equals(authUser.getRole())) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		// 10. 옳은 관리자 권한인 경우
		// @Auth(role="ADMIN) && authUser.getRole()=="ADMIN"
		return true;
	}

}
