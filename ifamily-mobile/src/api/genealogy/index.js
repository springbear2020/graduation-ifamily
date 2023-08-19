import request from "@/utils/request";

const genealogyAdminBaseUrl = '/genealogy/admin'
const genealogySuperviseBaseUrl = '/genealogy/supervise'

// 家族监督接口 =========================================================================================================

export function createGenealogy(data) {
    return request({
        url: `${genealogySuperviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function listGenealogies() {
    return request({
        url: `${genealogySuperviseBaseUrl}`,
        method: 'get',
    })
}

export function setDefaultGenealogyForUser(genealogyId) {
    return request({
        url: `${genealogySuperviseBaseUrl}/${genealogyId}`,
        method: 'put',
    })
}

export function noticePageData(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/notice`,
        method: 'get',
        params
    })
}

export function memorabiliaPageData(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/memorabilia`,
        method: 'get',
        params
    })
}

export function photoPageData(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/photo`,
        method: 'get',
        params
    })
}

export function revisionLogPageData(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/revision`,
        method: 'get',
        params
    })
}

export function visitorLogPageData(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/visitor`,
        method: 'get',
        params
    })
}

export function getCurrentUserPeople() {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/current`,
        method: 'get'
    })
}

export function saveCurrentUserPeople(data) {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/current`,
        method: 'post',
        data
    })
}

export function getPeople(peopleId) {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/${peopleId}`,
        method: 'get',
    })
}

export function getPeopleDetails(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/details`,
        method: 'get',
        params
    })
}

export function getMemberTree() {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/tree`,
        method: 'get'
    })
}

export function listGenerationMember(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/generation`,
        method: 'get',
        params
    })
}

export function listMemberByName(params) {
    return request({
        url: `${genealogySuperviseBaseUrl}/people/search`,
        method: 'get',
        params
    })
}

// 家族管理接口 ---------------------------------------------------------------------------------------------------------

export function updateGenealogy(data) {
    return request({
        url: `${genealogyAdminBaseUrl}`,
        method: 'put',
        data
    })
}

export function saveNotice(params) {
    return request({
        url: `${genealogyAdminBaseUrl}/notice`,
        method: 'post',
        params
    })
}

export function uploadPhoto(data) {
    return request({
        url: `${genealogyAdminBaseUrl}/photo`,
        method: 'post',
        data
    })
}

export function saveMemorabilia(data) {
    return request({
        url: `${genealogyAdminBaseUrl}/memorabilia`,
        method: 'post',
        data
    })
}

export function updatePeople(data) {
    return request({
        url: `${genealogyAdminBaseUrl}/people`,
        method: 'put',
        data
    })
}

export function removePeople(params) {
    return request({
        url: `${genealogyAdminBaseUrl}/people`,
        method: 'delete',
        params
    })
}

export function addRelative(data, params) {
    return request({
        url: `${genealogyAdminBaseUrl}/people`,
        method: 'post',
        data,
        params
    })
}
