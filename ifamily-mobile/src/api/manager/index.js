import request from "@/utils/request";

// 开放接口 =============================================================================================================

const codeApiBaseUrl = '/ifamily-manager/manager/code/api'

export function sendEmailCode(params) {
    return request({
        url: `${codeApiBaseUrl}/email`,
        method: 'post',
        params
    })
}

export function sendPhoneCode(params) {
    return request({
        url: `${codeApiBaseUrl}/phone`,
        method: 'post',
        params
    })
}

// 监督接口 =============================================================================================================

const transferSuperviseBaseUrl = '/ifamily-manager/manager/transfer/supervise'

export function getImageToken(params) {
    return request({
        url: `${transferSuperviseBaseUrl}/upload/qiniu/img/token`,
        method: 'get',
        params
    })
}
