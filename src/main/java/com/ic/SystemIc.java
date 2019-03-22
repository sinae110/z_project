package com.ic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SystemIc implements HandlerInterceptor {

	@Override	//특정 범위의 패키지에 있는 메서드가 실행되기 전에 실행될 공통 메서드
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String grade=request.getParameter("grade");
		
		if(!(java.util.Objects.isNull(grade))&&grade.equals("시스템")) {
			return true;
		}else {
			response.sendRedirect("error");
			return false;
		}
		
		//true이면 핵심메서드가 실행, false면 핵심메서드가 실행x
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
