import request from "@/utils/request";

const codeApiBaseUrl = '/manager/api/code'
const transferSuperviseBaseUrl = '/manager/supervise/transfer'

// 验证码开放接口 *******************************************************************************************************

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

// 文件传输监督接口 ======================================================================================================

export function getImageToken(params) {
    return request({
        url: `${transferSuperviseBaseUrl}/upload/qiniu/img/token`,
        method: 'get',
        params
    })
}
