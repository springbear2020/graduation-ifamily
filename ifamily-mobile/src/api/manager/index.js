import request from "@/utils/request";

// 开放接口 =============================================================================================================

const apiBaseUrl = '/ifamily-manager/manager/api'

export function sendEmailCode(params) {
    return request({
        url: `${apiBaseUrl}/code/email`,
        method: 'post',
        params
    })
}

export function sendPhoneCode(params) {
    return request({
        url: `${apiBaseUrl}/code/phone`,
        method: 'post',
        params
    })
}

// 监督接口 =============================================================================================================

const superviseBaseUrl = '/ifamily-manager/manager/supervise'

export function getImageToken(params) {
    return request({
        url: `${superviseBaseUrl}/uploader/qiniu/img/token`,
        method: 'get',
        params
    })
}
