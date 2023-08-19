import request from "@/utils/request";

const superviseBaseUrl = '/supervise/genealogy/member'

export function memberTreeOfGenealogy() {
    return request({
        url: `${superviseBaseUrl}/tree`,
        method: 'get'
    })
}

export function listMemberByName(params) {
    return request({
        url: `${superviseBaseUrl}/search`,
        method: 'get',
        params
    })
}

export function listGenerationMember() {
    return request({
        url: `${superviseBaseUrl}/generation`,
        method: 'get'
    })
}
