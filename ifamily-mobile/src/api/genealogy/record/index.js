import request from "@/utils/request";

const superviseBaseUrl = '/ifamily-genealogy/genealogy/record/supervise'

export function listVisitorLog(params) {
    return request({
        url: `${superviseBaseUrl}/visitor`,
        method: 'get',
        params
    })
}

export function listRevisionLog(params) {
    return request({
        url: `${superviseBaseUrl}/revision`,
        method: 'get',
        params
    })
}
