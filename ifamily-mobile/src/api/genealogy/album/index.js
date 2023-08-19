import request from "@/utils/request";

const superviseBaseUrl = '/ifamily-genealogy/genealogy/photo/supervise'

export function uploadPhotos(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function photoPageData(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
        params
    })
}
