/**
 * This is just a simple version of object deep copy
 */
export function deepClone(source) {
    if (!source && typeof source !== 'object') {
        throw new Error('param is empty or not a object')
    }
    const targetObj = source.constructor === Array ? [] : {}
    Object.keys(source).forEach(keys => {
        if (source[keys] && typeof source[keys] === 'object') {
            targetObj[keys] = deepClone(source[keys])
        } else {
            targetObj[keys] = source[keys]
        }
    })
    return targetObj
}
