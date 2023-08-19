package cn.edu.whut.springbear.ifamily.common.constant;

/**
 * @author Spring-_-Bear
 * @since 23/04/14 10:26
 */
public class RegExpConstants {

    /**
     * 邮箱正则表达式
     */
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**
     * 手机正则表达式
     */
    public static final String PHONE_PATTERN = "^1[3456789]\\d{9}$";
    /**
     * URL 正则表达式
     */
    public static final String URL_PATTERN = "^((https?|http|ftp|file)://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$";
    /**
     * 用户名正则表达式：以字母开头，可包含字母、数字、下划线和连字符，长度限 5 至 20 位
     */
    public static final String USERNAME_PATTERN = "^[a-zA-Z]([-_a-zA-Z0-9]{4,19})+$";

}
