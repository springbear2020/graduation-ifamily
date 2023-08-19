import request from "@/utils/request";

const momentSuperviseBaseUrl = '/social/supervise/moment'

// 用户动态监督接口 ======================================================================================================

export function saveMoment(data) {
    return request({
        url: `${momentSuperviseBaseUrl}`,
        method: 'post',
        data
    })
}

export function userMomentPageData(params) {
    return request({
        url: `${momentSuperviseBaseUrl}`,
        method: 'get',
        params
    })
}

export function removeMoment(momentId) {
    return request({
        url: `${momentSuperviseBaseUrl}/${momentId}`,
        method: 'delete',
    })
}

export function getMoment(momentId) {
    return request({
        url: `${momentSuperviseBaseUrl}/${momentId}`,
        method: 'get',
    })
}

export function editMoment(data) {
    return request({
        url: `${momentSuperviseBaseUrl}`,
        method: 'put',
        data
    })
}

export function genealogyMomentPageData(params) {
    return request({
        url: `${momentSuperviseBaseUrl}/genealogy`,
        method: 'get',
        params
    })
}

// 用户动态点赞监督接口 ==================================================================================================

export function likeMoment(momentId) {
    return request({
        url: `${momentSuperviseBaseUrl}/like/${momentId}`,
        method: 'post',
    })
}

export function unLikeMoment(momentId) {
    return request({
        url: `${momentSuperviseBaseUrl}/like/${momentId}`,
        method: 'delete',
    })
}

// 用户动态评论监督接口 ==================================================================================================

export function commentMoment(data) {
    return request({
        url: `${momentSuperviseBaseUrl}/comment`,
        method: 'post',
        data
    })
}
