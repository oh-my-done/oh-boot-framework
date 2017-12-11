package com.gizwits.security.jwt;

import com.gizwits.common.entity.ResponseCode;
import com.gizwits.common.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 保证安全性,需对secret进行二次的MD5加密
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Component
@Slf4j
public class JwtTokenParser implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USER = "user";
    private static final String CLAIM_KEY_AUTHORITY = "auth";


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    /**
     * 获取登入用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {

            final Claims claims = getClaimsFromToken(token);
            return (String) claims.get(CLAIM_KEY_USER);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 获取token创建时间
     *
     * @param token
     * @return
     */
    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }


    /**
     * 获取token的过期时间
     *
     * @param token
     * @return
     */
    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }


    /**
     * 过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


    /**
     * 生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USER, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUTHORITY, userDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, DigestUtils.md5DigestAsHex((secret).getBytes()))
                .compact();
    }

    public AuthToken getToken(UserDetails userDetails) {
        String token = generateToken(userDetails);
        if (StringUtils.isNoneBlank(token)) {
            return new AuthToken(token, expiration);
        }
        return null;


    }

    /**
     * 刷新新的token值
     *
     * @param token
     * @return
     */
    public AuthToken refreshNewToken(String token) {

        try {

            if (isTokenExpired(token)) {
                throw new TokenException(ResponseCode.REFRESH_TOKEN_ERROR.getName());
            }
            String newToken = refreshToken(token);

            if (StringUtils.isNotEmpty(newToken)) {
                return new AuthToken(newToken, expiration);
            }
        } catch (Exception e) {
            throw new TokenException(ResponseCode.REFRESH_TOKEN_ERROR.getName());
        }

        throw new TokenException(ResponseCode.REFRESH_TOKEN_ERROR.getName());
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;

        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 生成token
     *
     * @param
     * @return
     */
    private String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setSubject(claims.get(CLAIM_KEY_USER).toString())
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, DigestUtils.md5DigestAsHex((secret).getBytes()))
                .compact();
    }


    /**
     * 解析token
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DigestUtils.md5DigestAsHex((secret).getBytes()))
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    /**
     * 解析用户
     *
     * @param token
     * @return
     */
    public UserDetails getUserFromToken(String token) {

        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(DigestUtils.md5DigestAsHex((secret).getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
            String username = (String) claims.get(CLAIM_KEY_USER);

            List<String> auth = (List<String>) claims.get(CLAIM_KEY_AUTHORITY);

            List<GrantedAuthority> grantedAuthorities = auth.stream()
                    .filter(f -> StringUtils.isNotEmpty(f))
                    .map(r -> new SimpleGrantedAuthority(r))
                    .collect(Collectors.toList());

            return new User(username, "", grantedAuthorities);


        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }


    /**
     * 验证token的合法性
     *
     * @param token
     * @param name
     * @return
     */
    public boolean validateToken(String token, String name) {

        if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(name)) {
            final String username = getUsernameFromToken(token);
            return (name.equals(username) && !isTokenExpired(token));
        }
        return false;
    }

    /**
     * token 是否过期
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        if (StringUtils.isNotEmpty(token)) {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }
        return false;

    }
}