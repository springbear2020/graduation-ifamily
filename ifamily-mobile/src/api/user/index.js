import request from "@/utils/request";

// 开放接口 =============================================================================================================
const apiBaseUrl = '/ifamily-user/user/api'

export function login(data) {
    return request({
        url: `${apiBaseUrl}/login`,
        method: 'post',
        data
    })
}

export function reset(data) {
    return request({
        url: `${apiBaseUrl}/reset`,
        method: 'put',
        data
    })
}

export function register(data) {
    return request({
        url: `${apiBaseUrl}/register`,
        method: 'post',
        data
    })
}

// 监督接口 =============================================================================================================

const superviseBaseUrl = '/ifamily-user/user/supervise'

export function getUser() {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get'
    })
}

/**
 * 用户账号注销
 */
export function logout(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'delete',
        params
    })
}

export function getUserLoginLog(params) {
    return request({
        url: `${superviseBaseUrl}/devices`,
        method: 'get',
        params
    })
}

/**
 * 更新用户个人资料：头像地址、用户昵称、个性签名
 * @param content 需要保存的新内容
 * @param type 类型：[1]用户昵称 [2]个性签名 [3]头像地址
 */
export function updateUserProfile(content, type) {
    return request({
        url: `${superviseBaseUrl}/profile/${type}`,
        method: 'put',
        params: {content}
    })
}

/**
 * 更新用户隐私信息：用户名、手机号、邮箱
 * @param content 需要保存的新内容
 * @param extra 额外携带的信息：[type==1]携带用户密码 [type==2 || type=3]携带验证码
 * @param type 类型：[1]用户名 [2]邮箱 [3]手机
 */
export function updateUserPrivacy(content, extra, type) {
    return request({
        url: `${superviseBaseUrl}/privacy/${type}`,
        method: 'put',
        params: {content, extra}
    })
}

// 认证接口 =============================================================================================================

/**
 * 刷新用户令牌
 * @param refreshToken 刷新令牌
 */
export function refreshToken(refreshToken) {
    return request({
        url: '/ifamily-auth/oauth/token',
        method: 'post',
        params: {
            'client_id': 'mobile-app',
            'client_secret': 'ifamily',
            'grant_type': 'refresh_token',
            'refresh_token': refreshToken,
        }
    })
}
