import request from "@/utils/request";

const superviseBaseUrl = '/supervise/genealogy/people'

export function currentUserPeople() {
    return request({
        url: `${superviseBaseUrl}/current`,
        method: 'get'
    })
}

export function getGenealogyPeopleDetails(params) {
    return request({
        url: `${superviseBaseUrl}/details`,
        method: 'get',
        params
    })
}

export function getPeopleById(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'get',
        params
    })
}

export function updatePeople(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'put',
        data
    })
}

export function removePeople(params) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'delete',
        params
    })
}

export function addRelatives(data) {
    return request({
        url: `${superviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function saveUserPeople(data) {
    return request({
        url: `${superviseBaseUrl}/current`,
        method: 'post',
        data
    })
}
