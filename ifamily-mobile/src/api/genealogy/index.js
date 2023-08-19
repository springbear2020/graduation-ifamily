import request from "@/utils/request";

// 监督接口 =============================================================================================================

const superviseBaseUrl = '/ifamily-genealogy/genealogy/supervise'

export function createGenealogy(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function reqListGenealogies() {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
    })
}

export function setDefaultGenealogyForUser(genealogyId) {
    return request({
        url: `${superviseBaseUrl}/${genealogyId}`,
        method: 'put',
    })
}

// 管理接口 =============================================================================================================

const adminBaseUrl = '/ifamily-genealogy/genealogy/admin'

export function updateDefaultGenealogy(data) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'put',
        data
    })
}
