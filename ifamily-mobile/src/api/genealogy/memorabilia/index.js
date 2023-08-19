import request from "@/utils/request";

const superviseBaseUrl = '/ifamily-genealogy/genealogy/memorabilia/supervise'

export function saveMemorabilia(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function memorabiliaPageData(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
        params
    })
}
