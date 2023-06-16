package com.nishan.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nishan.demo.service.CustomUserDetailService;
import com.nishan.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	@Autowired
	private JwtService jwtService;
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt=request.getHeader("Authorization");		
		if (jwt == null || !jwt.startsWith("Bearer ")) {
		    filterChain.doFilter(request, response);
		    return;
		}

		
		jwt=jwt.substring(7);
		String username=jwtService.extractUsername(jwt);
		
		if(username!=null||SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
			
			if(jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username, null,userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
