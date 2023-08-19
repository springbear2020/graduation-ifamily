package cn.edu.whut.springbear.ifamily.common.util;

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

    /**
     * 百度地图签发的 IP 归属地解析请求地址 FIXME update the sensitive info before deploying the server
     */
    private static final String BAIDU_MAP_URL = "https://api.map.baidu.com/location/ip?ak=IPhSgYKe4YCEn2KfYyQwdp3S8RtqrvV4&coor=bd09ll&ip=";
    /**
     * 淘宝公共 IP 归属地查询请求地址
     */
    private static final String TAOBAO_URL = "https://ip.taobao.com/outGetIpInfo?accessKey=alibaba-inc&ip=";

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
    public static String baiduParseIpLocation(String ip) {
        if (invalidIp(ip)) {
            return "未知地点";
        }

        String url = BAIDU_MAP_URL + ip;
        // 尝试与百度服务器建立一个 GET 连接，获得其响应的 json 数据字符串
        String responseStr = HttpRequest.get(url).execute().body();
        // 将响应的 json 字符串解析为 json 对象
        JSONObject responseJson = JSONUtil.parseObj(responseStr);
        // 从响应的 json 对象中获取含有地址信息的 json 对象
        JSONObject addressJson = JSONUtil.parseObj(responseJson.getObj("content"));
        // 获取 IP 地址，默认为 “未知地点”
        return addressJson.getStr("address", "未知地点");
    }

    /**
     * 使用淘宝公共 API 解析 IP 归属地
     */
    public static String taobaoParseIpLocation(String ip) {
        if (invalidIp(ip)) {
            return "未知地点";
        }

        String url = TAOBAO_URL + ip;
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
     * 判断传入的 IP 地址是否无效
     *
     * @param ip IP
     * @return [true]无效
     */
    private static boolean invalidIp(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

}