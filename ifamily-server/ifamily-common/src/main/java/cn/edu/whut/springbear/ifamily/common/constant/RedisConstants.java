package cn.edu.whut.springbear.ifamily.common.constant;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 12:56
 */
public class RedisConstants {

    /**
     * Redis 缓存有效期，单位：天
     */
    public static final long CACHE_EXPIRE = 1L;
    /**
     * 验证码前缀
     */
    public static final String CODE_PREFIX = "code:";
    /**
     * 验证码有效期，单位：分钟
     */
    public static final Long CODE_TIMEOUT = 10L;

}
