package com.schoolmanagementsystem.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@Service
public class JwtServiceImpl {

	public String generateToken(final UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))//30 minutes
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateRefreshToken(final Map<String, Object> extraClaims,final UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//1 hour
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private <T> T extractClaim(final String token,final Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(final String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public String extractUserName(final String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode("8523698521478569874563214587532569854769321458756985647315987582");
		return Keys.hmacShaKeyFor(key);
	}

	public boolean isTokenValid(final String token,final UserDetails userDetails) {
		final String username = extractUserName(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(final String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
