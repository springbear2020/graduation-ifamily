export const TOKEN_KEY = 'Authorization'
export const REFRESH_TOKEN = 'refreshToken'

export function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token)
}

export function getToken() {
    return localStorage.getItem(TOKEN_KEY)
}

export function removeToken() {
    localStorage.removeItem(TOKEN_KEY)
}

export function setRefreshToken(refreshToken) {
    localStorage.setItem(REFRESH_TOKEN, refreshToken)
}

export function removeRefreshToken() {
    localStorage.removeItem(REFRESH_TOKEN)
}

export function getRefreshToken() {
    return localStorage.getItem(REFRESH_TOKEN)
}

