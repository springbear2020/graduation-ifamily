import request from "@/utils/request";

const userApiBaseUrl = '/user/api'
const userSuperviseBaseUrl = '/user/supervise'

// 用户开放接口 =========================================================================================================

export function login(data) {
    return request({
        url: `${userApiBaseUrl}/login`,
        method: 'post',
        data
    })
}

export function reset(data) {
    return request({
        url: `${userApiBaseUrl}/reset`,
        method: 'put',
        data
    })
}

export function register(data) {
    return request({
        url: `${userApiBaseUrl}/register`,
        method: 'post',
        data
    })
}

// 用户监督接口 =========================================================================================================

export function getCurrentUser() {
    return request({
        url: `${userSuperviseBaseUrl}`,
        method: 'get'
    })
}

export function logout(params) {
    return request({
        url: `${userSuperviseBaseUrl}`,
        method: 'delete',
        params
    })
}

export function loginLogPageData(params) {
    return request({
        url: `${userSuperviseBaseUrl}/devices`,
        method: 'get',
        params
    })
}

export function updateUserProfile(content, type) {
    return request({
        url: `${userSuperviseBaseUrl}/profile/${type}`,
        method: 'put',
        params: {content}
    })
}

export function updateUserPrivacy(content, extra, type) {
    return request({
        url: `${userSuperviseBaseUrl}/privacy/${type}`,
        method: 'put',
        params: {content, extra}
    })
}

// 用户认证接口 =========================================================================================================

export function refreshUserToken(refreshToken) {
    return request({
        url: '/auth/oauth/token',
        method: 'post',
        params: {
            'client_id': 'mobile-app',
            'client_secret': 'ifamily',
            'grant_type': 'refresh_token',
            'refresh_token': refreshToken,
        }
    })
}
