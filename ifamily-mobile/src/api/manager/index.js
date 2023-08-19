import request from "@/utils/request";

const apiBaseUrl = '/api/manager'
const superviseBaseUrl = '/supervise/manager'

export function sendEmailCode(params) {
    return request({
        url: `${apiBaseUrl}/code/email`,
        method: 'post',
        params
    })
}

export function getAvatarToken(params) {
    return request({
        url: `${superviseBaseUrl}/uploader/qiniu/token/img/avatar`,
        method: 'get',
        params
    })
}
