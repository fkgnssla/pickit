package com.ssafy.pickit.global.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.pickit.global.jwt.utils.JwtConstants;
import com.ssafy.pickit.global.jwt.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationRegisterFilter extends OncePerRequestFilter {

	/*
	블랙리스트 검증할 때, => 게이트웨이 필터단
	리프레시 토큰 발급할 때, => 서비스단
	리프레시 토큰 검증할 떄, => 서비스단
	 */
	// @Autowired
	// private RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader(JwtConstants.JWT_HEADER);
		String accessToken = JwtUtils.getTokenFromHeader(authHeader);
		Authentication authentication = JwtUtils.getAuthentication(accessToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}

