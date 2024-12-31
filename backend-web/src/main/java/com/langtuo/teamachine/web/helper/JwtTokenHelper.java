package com.langtuo.teamachine.web.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.langtuo.teamachine.web.security.model.AdminDetails;
import com.langtuo.teamachine.web.security.model.MachineDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt Token 生成的工具类
 * JWT Token 的格式：header.payload.signature
 * header的格式（算法、token的类型）：{"alg": "HS512", "typ": "JWT"}
 * payload 的格式（用户名、创建时间、生成时间）：{"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature 的生成算法：HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * @author Jiaqing
 */
@Slf4j
public class JwtTokenHelper {
    /**
     * claim 中的 key
     */
    private static final String CLAIM_KEY_USER_NAME = "sub";
    private static final String CLAIM_KEY_GMT_CREATED = "created";
    private static final String CLAIM_KEY_TENANT_CODE = "tenantCode";

    @Value("${teamachine.jwt.secret}")
    private String secret;

    @Value("${teamachine.jwt.expiration}")
    private Long expiration;

    /**
     * 根据负责生成 JWT Token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从 token 中获取 JWT 中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("jwt format verify error: " + token, e);
        }
        return claims;
    }

    /**
     * 生成 token 的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从 token 中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从 token 中获取登录租户编码
     */
    public String getTenantCodeFromToken(String token) {
        String tenantCode;
        try {
            Claims claims = getClaimsFromToken(token);
            tenantCode = claims.getAudience();
        } catch (Exception e) {
            tenantCode = null;
        }
        return tenantCode;
    }

    /**
     * 验证 token 是否还有效
     *
     * @param token
     *      客户端传入的 token
     * @param userDetails
     *      从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        if (StringUtils.isBlank(token) || userDetails == null) {
            return false;
        }

        String username = getUserNameFromToken(token);
        if (StringUtils.isBlank(username)) {
            return false;
        }
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断 token 是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从 token 中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成 token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_NAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_GMT_CREATED, new Date());

        if (userDetails instanceof AdminDetails) {
            claims.put(CLAIM_KEY_TENANT_CODE, ((AdminDetails) userDetails).getTenantCode());
        } else if (userDetails instanceof MachineDetails) {
            claims.put(CLAIM_KEY_TENANT_CODE, ((MachineDetails) userDetails).getTenantCode());
        }
        return generateToken(claims);
    }

    /**
     * 刷新 token
     *
     * @param oldToken
     */
    public String refreshToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }
        //  oldToken 校验不通过
        Claims claims = getClaimsFromToken(oldToken);
        if (claims == null) {
            return null;
        }
        // 如果 oldToken 已经过期，不支持刷新
        if (isTokenExpired(oldToken)) {
            return null;
        }
        // 如果 oldToken 在 30 分钟之内刚刷新过，返回原 oldToken
        if (tokenRefreshJustBefore(oldToken, 30 * 60)) {
            return oldToken;
        } else {
            claims.put(CLAIM_KEY_GMT_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断 token 在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_GMT_CREATED, Date.class);
        Date refreshDate = new Date();
        // 刷新时间在创建时间的指定时间内
        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time))) {
            return true;
        }
        return false;
    }
}
