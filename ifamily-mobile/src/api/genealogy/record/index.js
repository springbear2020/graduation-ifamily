import request from "@/utils/request";

const superviseBaseUrl = '/ifamily-genealogy/genealogy/record/supervise'

export function visitorLogPageData(params) {
    return request({
        url: `${superviseBaseUrl}/visitor`,
        method: 'get',
        params
    })
}

export function revisionLogPageData(params) {
    return request({
        url: `${superviseBaseUrl}/revision`,
        method: 'get',
        params
    })
}
