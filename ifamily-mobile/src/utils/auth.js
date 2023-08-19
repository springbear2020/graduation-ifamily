export function setToken(token) {
    localStorage.setItem("Authentication", token)
}

export function getToken() {
    return localStorage.getItem('Authentication')
}

export function removeToken() {
    localStorage.removeItem('Authentication')
}
