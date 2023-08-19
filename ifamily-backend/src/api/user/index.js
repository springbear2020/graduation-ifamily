import request from "@/utils/request";

const adminApiBaseUrl = '/backend/api/admin'
const adminSuperviseBaseUrl = '/backend/supervise/admin'

export function login(data) {
    return request({
        url: `${adminApiBaseUrl}/login`,
        method: 'post',
        data
    })
}

export function getCurrentAdminInfo() {
    return request({
        url: `${adminSuperviseBaseUrl}`,
        method: 'get'
    })
}

export function refreshUserToken(refreshToken) {
    return request({
        url: '/auth/oauth/token',
        method: 'post',
        params: {
            'client_id': 'backend-app',
            'client_secret': 'ifamily',
            'grant_type': 'refresh_token',
            'refresh_token': refreshToken,
        }
    })
}
