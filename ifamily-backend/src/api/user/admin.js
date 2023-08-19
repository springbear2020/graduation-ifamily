import request from "@/utils/request";

const adminBaseUrl = '/backend/admin'

export function adminPageData(params) {
    return request({
        url: `${adminBaseUrl}/page`,
        method: 'get',
        params
    })
}

export function dbTables() {
    return request({
        url: `${adminBaseUrl}/db`,
        method: 'get',
    })
}

export function updateStatus(adminId, params) {
    return request({
        url: `${adminBaseUrl}/status/${adminId}`,
        method: 'put',
        params
    })
}

export function deleteAccount(adminId) {
    return request({
        url: `${adminBaseUrl}/${adminId}`,
        method: 'delete'
    })
}
