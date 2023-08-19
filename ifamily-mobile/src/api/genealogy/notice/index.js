import request from "@/utils/request";

// 监督接口 =============================================================================================================

const superviseBaseUrl = '/ifamily-genealogy/genealogy/notice/supervise'

export function save(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        params
    })
}

export function noticesPageData(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
        params
    })
}

// 管理接口 =============================================================================================================

const adminBaseUrl = '/ifamily-genealogy/genealogy/notice/admin'

export function edit(params) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'put',
        params
    })
}

export function remove(params) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'delete',
        params
    })
}
