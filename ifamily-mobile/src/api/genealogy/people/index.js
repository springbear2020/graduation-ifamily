import request from "@/utils/request";

const superviseBaseUrl = '/supervise/genealogy/people'

export function currentUserPeople() {
    return request({
        url: `${superviseBaseUrl}/current`,
        method: 'get'
    })
}

export function getGenealogyPeopleDetails(peopleId) {
    return request({
        url: `${superviseBaseUrl}/details/${peopleId}`,
        method: 'get',
    })
}

export function getPeopleById(peopleId) {
    return request({
        url: `${superviseBaseUrl}/${peopleId}`,
        method: 'get',
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
