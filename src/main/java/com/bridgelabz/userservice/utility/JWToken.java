package com.bridgelabz.userservice.utility;

import java.util.Date;
import org.slf4j.Logger;
//import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import com.bridgelabz.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:creating token
 ************************************************************************************/
public class JWToken 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(JWToken.class);
	 
	public String createJWT(User user)
	{
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		JwtBuilder builder = Jwts.builder()
				.setSubject(user.getEmail())
				.setExpiration(new Date((System.currentTimeMillis()+600000)))
				.setIssuedAt(new Date())
				.setIssuer(user.getId())
				.signWith(signatureAlgorithm, "passKey");
				 LOGGER.info(builder.compact());
				 LOGGER.debug("doStuff needed to debug");
				return builder.compact();
	}

	public static Claims verifyToken(String token) 
	{
		try
		{
			Claims claims = Jwts.parser()
					.setSigningKey("passKey")
					.parseClaimsJws(token).
					getBody();
			return claims;
		} 
		catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) 
		{
			throw new JWTException("Error in verifying JW Token");
		} 
		catch (ExpiredJwtException e) 
		{
			throw new JWTException("Token Expired");
		}
	}
}
