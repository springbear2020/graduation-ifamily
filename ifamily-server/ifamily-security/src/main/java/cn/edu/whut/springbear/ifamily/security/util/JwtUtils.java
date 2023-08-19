package cn.edu.whut.springbear.ifamily.security.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author Spring-_-Bear
 * @since 23/03/12 08:33
 */
public class JwtUtils {

    /**
     * 加密串 FIXME update the sensitive info before deploy
     */
    private static final String SECRET = "ifamily";
    /**
     * 有限期 3 天
     */
    private static final Long EXPIRE = 259200000L;
    /**
     * 令牌键
     */
    public static final String TOKEN_KEY = "Authentication";

    /**
     * 创建 token 字符串
     */
    public static String create(String key, String val) {
        JwtBuilder builder = Jwts.builder();
        Date expiration = new Date(System.currentTimeMillis() + JwtUtils.EXPIRE);

        builder.setSubject(TOKEN_KEY)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, JwtUtils.SECRET);

        return builder.claim(key, val).compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * 从 token 中根据 key 获取 value
     */
    public static String get(String token, String key) {
        if (!StringUtils.hasLength(token)) {
            return null;
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(JwtUtils.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

        return (String) claims.get(key);
    }

}
