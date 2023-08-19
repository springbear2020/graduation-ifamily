import AntPathMatcher from 'ant-path-matcher'

const antMatcher = new AntPathMatcher()

/**
 * Check whether the `patterns` includes the `path` under the ant match rules,
 * return the rule pattern matched array if matched successfully
 */
export function findMatched(patterns, path) {
    if (!Array.isArray(patterns)) {
        return undefined
    }

    let resultList = []
    for (let i = 0; i < patterns.length; i++) {
        if (antMatcher.match(patterns[i], path)) {
            resultList.push(patterns[i])
        }
    }

    return resultList
}

/**
 * determine whether the `path` is included in the `patterns` under the ant matching rules
 */
export function anyMatch(patterns, path) {
    if (!Array.isArray(patterns)) {
        return false
    }

    for (let i = 0; i < patterns.length; i++) {
        if (antMatcher.match(patterns[i], path)) {
            return true
        }
    }

    return false
}
