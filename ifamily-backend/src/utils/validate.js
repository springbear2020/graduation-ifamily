/**
 * Determine whether it is a url link
 */
export function isExternal(path) {
    return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * Determine whether it is string
 */
export function isString(str) {
    return typeof str === 'string' || str instanceof String;
}

/**
 * Determine whether it is array
 */
export function isArray(arg) {
    if (typeof Array.isArray === 'undefined') {
        return Object.prototype.toString.call(arg) === '[object Array]'
    }
    return Array.isArray(arg)
}
