import request from "@/utils/request";

export function sendEmailCode(params) {
    return request({
        url: '/api/manager/code/email',
        method: 'post',
        params
    })
}
