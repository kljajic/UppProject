package com.process.security;

import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenGenerator {

    @Value(value = "${jwt.app.name}")
    private String APP_NAME;

    @Value(value = "${jwt.secret}")
    private String SECRET;

    @Value(value = "${jwt.expirationTime}")
    private int EXPIRES_IN;
    
    @Value(value = "${jwt.tokenPrefix}")
    private String TOKEN_PREFIX;

    @Value(value = "${jwt.header}")
    private String HEADER_STRING;
    
    public JWTTokenGenerator() {}
    
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String generateToken(String username) {
    	
    	byte[] bytesEncoded = Base64.encodeBase64(SECRET.getBytes());
    	String encodedSecret = new String(bytesEncoded);
    	
        String jws = Jwts.builder()
                .setIssuer( APP_NAME )
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith( SIGNATURE_ALGORITHM,  encodedSecret)	
                .compact();
        return jws;
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private long getCurrentTimeMillis() {
        return new DateTime().getMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(getCurrentTimeMillis() + this.EXPIRES_IN);
    }

	public String getAPP_NAME() {
		return APP_NAME;
	}

	public String getSECRET() {
		return SECRET;
	}

	public int getEXPIRES_IN() {
		return EXPIRES_IN;
	}

	public SignatureAlgorithm getSIGNATURE_ALGORITHM() {
		return SIGNATURE_ALGORITHM;
	}

	public String getTOKEN_PREFIX() {
		return TOKEN_PREFIX;
	}

	public String getHEADER_STRING() {
		return HEADER_STRING;
	}
}
