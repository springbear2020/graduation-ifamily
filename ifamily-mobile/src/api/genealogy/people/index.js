import request from "@/utils/request";

// 监督接口 =============================================================================================================

const superviseBaseUrl = '/ifamily-genealogy/genealogy/people/supervise'

export function getPeopleDetails(params) {
    return request({
        url: `${superviseBaseUrl}/details`,
        method: 'get',
        params
    })
}

export function reqCurrentUserPeople() {
    return request({
        url: `${superviseBaseUrl}/current`,
        method: 'get'
    })
}

export function saveUserPeople(data) {
    return request({
        url: `${superviseBaseUrl}/current`,
        method: 'post',
        data
    })
}

// 管理接口 =============================================================================================================

const adminBaseUrl = '/ifamily-genealogy/genealogy/people/admin'

export function getPeople(params) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'get',
        params
    })
}

export function updatePeople(data) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'put',
        data
    })
}

export function removePeople(params) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'delete',
        params
    })
}

export function addRelatives(data, params) {
    return request({
        url: `${adminBaseUrl}`,
        method: 'post',
        data,
        params
    })
}
