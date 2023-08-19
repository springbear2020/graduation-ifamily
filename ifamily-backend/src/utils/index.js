import dayjs from "dayjs";

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

/**
 * 计算传入日期与当前日期的差异，根据差异返回不同的时间差异字符串
 * @param dateStr 日期字符串
 * @returns {string} 计算后的字符串
 */
export function diffDate(dateStr) {
    const now = dayjs().format('YYYY-MM-DD')
    const diff = dayjs(now).diff(dateStr, 'day')

    if (diff === 0) {
        return '今天';
    } else  {
        return `${diff}天前`;
    }
}
