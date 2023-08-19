import request from "@/utils/request";

export function login(data) {
    return request({
        url: '/api/user/login',
        method: 'post',
        data
    })
}
