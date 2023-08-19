import request from "@/utils/request";

const superviseBaseUrl = '/ifamily-genealogy/genealogy/member/supervise'

export function reqMemberTreeOfGenealogy() {
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

export function listGenerationMember(params) {
    return request({
        url: `${superviseBaseUrl}/generation`,
        method: 'get',
        params
    })
}
