import request from "@/utils/request";

const superviseBaseUrl = '/supervise/genealogy'

export function createGenealogy(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function listGenealogiesOfUser() {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
    })
}

export function setDefaultGenealogyOfUser(genealogyId) {
    return request({
        url: `${superviseBaseUrl}/${genealogyId}`,
        method: 'put',
    })
}

export function updateDefaultGenealogy(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'put',
        data
    })
}
