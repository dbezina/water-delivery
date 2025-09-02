package com.bezina.water_delivery.auth_service;

import com.bezina.water_delivery.auth_service.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testJwt() {

		JwtService jwtService = new JwtService();
		jwtService.setSecret("urc3HCRdnPNQa7PE0WdiCcVsRvZWo85e0qMMhF79YVzmyjN9JRGRgODp5XbCLanalUSbuNMrLDpRdZwZYJHYbg==");

		UserDetails user = User.withUsername("alice")
				.password("123")
				.roles("USER")
				.build();

		String token = jwtService.generateToken(user);
		System.out.println("TOKEN: " + token);

		String username = jwtService.extractUsername(token);
		System.out.println("USERNAME: " + username);
	}

}
