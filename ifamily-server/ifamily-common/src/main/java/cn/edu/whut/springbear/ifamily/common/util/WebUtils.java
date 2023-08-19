package cn.edu.whut.springbear.ifamily.common.util;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Spring-_-Bear
 * @since 23/03/12 09:22
 */
public final class WebUtils {

    private static final String DEFAULT_LOCATION = "未知地点";

    /**
     * 从请求头中获取 IP 地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        // 获取 HTTP 代理服务器转发的客户端真实 IP
        String ipAddress = request.getHeader("x-forwarded-for");
        if (invalidIp(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (invalidIp(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (invalidIp(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (invalidIp(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 获取客户端的真实 IP
        if (invalidIp(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // 通过多个代理转发的情况，第一个 IP 为客户端真实 IP，多个 IP 会按照 ',' 分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        // 从本地访问时根据网卡获取本机局域网 IP
        if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
            try {
                ipAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return ipAddress;
    }

    /**
     * 使用百度地图 API 解析 IP 归属地
     */
    public static String baiduParse(String ip) {
        if (invalidIp(ip)) {
            return DEFAULT_LOCATION;
        }

        try {
            String url = "https://api.map.baidu.com/location/ip?ak=IPhSgYKe4YCEn2KfYyQwdp3S8RtqrvV4&coor=bd09ll&ip=" + ip;
            // 尝试与百度服务器建立一个 GET 连接，获得其响应的 json 数据字符串
            String responseStr = HttpRequest.get(url).execute().body();
            // 将响应的 json 字符串解析为 json 对象
            JSONObject responseJson = JSONUtil.parseObj(responseStr);
            // 从响应的 json 对象中获取含有地址信息的 json 对象
            JSONObject addressJson = JSONUtil.parseObj(responseJson.getObj("content"));
            // 获取 IP 地址，默认为 “未知地点”
            return addressJson.getStr("address", DEFAULT_LOCATION);
        } catch (Exception e) {
            return DEFAULT_LOCATION;
        }
    }

    /**
     * 使用淘宝公共 API 解析 IP 归属地
     */
    public static String taobaoParse(String ip) {
        if (invalidIp(ip)) {
            return DEFAULT_LOCATION;
        }

        String url = "https://ip.taobao.com/outGetIpInfo?accessKey=alibaba-inc&ip=" + ip;
        try {
            // 尝试与淘宝服务器建立一个 GET 连接，获得其响应的 json 数据字符串
            String responseStr = HttpRequest.get(url).execute().body();
            // 将响应的 json 字符串解析为 json 对象
            JSONObject responseJson = JSONUtil.parseObj(responseStr);
            // 从响应的 json 对象中获取含有地址信息的 json 对象
            JSONObject addressJson = JSONUtil.parseObj(responseJson.getObj("data"));
            String country = addressJson.getStr("country", "未知国家");
            String region = addressJson.getStr("region", "未知地区");
            String city = addressJson.getStr("city", "未知城市");
            return country + "/" + region + "/" + city;
        } catch (Exception e) {
            return DEFAULT_LOCATION;
        }
    }

    /**
     * 从请求头中获取用户浏览器 UA 信息
     */
    public static String userAgent(HttpServletRequest request) {
        String uaStr = request.getHeader("user-agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        String platform = ua.getPlatform().toString();
        String os = ua.getOs().toString();
        String browser = ua.getBrowser().toString();
        return platform + "/" + os + "/" + browser;
    }

    /**
     * 从请求头中解析用户信息
     */
    public static UserDTO parseGeneralUser(HttpServletRequest request) {
        /*
         * {
         *      "user_name":"bear",
         *      "scope":["all"],
         *      "id":1,
         *      "exp":1681631893,
         *      "authorities":["MEMBER"],
         *      "jti":"b9f09a3f-8a18-4045-a5b0-7baa196830cf",
         *      "client_id":"mobile-app"
         * }
         */
        UserDTO userDTO = new UserDTO();
        try {
            String userStr = request.getHeader(AuthConstants.GENERAL_USER_HEADER);
            JSONObject jsonObject = JSONUtil.parseObj(userStr);
            userDTO.setUsername(jsonObject.getStr("user_name"));
            userDTO.setId(jsonObject.getLong("id"));
            userDTO.setRoles(jsonObject.getBeanList("authorities", String.class));
            userDTO.setClientId(jsonObject.getStr("client_id"));
        } catch (Exception e) {
            // 解析失败，请求头非法
            throw new IncorrectConditionException("账号未登录或令牌已过期");
        }
        return userDTO;
    }

    /**
     * 判断传入的 IP 地址是否无效
     *
     * @param ip IP
     * @return [true]无效
     */
    private static boolean invalidIp(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

}
