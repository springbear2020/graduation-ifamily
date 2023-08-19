import axios from "axios"
import {BASE_REQUEST_URL} from "@/utils/request"

const docUrl = 'v2/api-docs'

export function listAuthApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/auth/${docUrl}`,
        method: 'get'
    })
}

export function listAclApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/acl/${docUrl}`,
        method: 'get'
    })
}

export function listBackendApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/backend/${docUrl}`,
        method: 'get'
    })
}

export function listGenealogyApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/genealogy/${docUrl}`,
        method: 'get'
    })
}

export function listManagerApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/manager/${docUrl}`,
        method: 'get'
    })
}

export function listSocialApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/social/${docUrl}`,
        method: 'get'
    })
}

export function listUserApiPaths() {
    return axios({
        url: `${BASE_REQUEST_URL}/user/${docUrl}`,
        method: 'get'
    })
}
