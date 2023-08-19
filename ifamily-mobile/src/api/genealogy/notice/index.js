import request from "@/utils/request";

const superviseBaseUrl = '/ifamily-genealogy/genealogy/notice/supervise'

export function saveNotice(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        params
    })
}

export function noticePageData(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
        params
    })
}
