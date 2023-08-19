/**
 * Check if an element has a class
 */
export function hasClass(element, className) {
    return !!element.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
}

/**
 * Add class to element
 */
export function addClass(element, className) {
    if (!hasClass(element, className)) element.className += ' ' + className
}

/**
 * Remove class from element
 */
export function removeClass(element, className) {
    if (hasClass(element, className)) {
        const reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
        element.className = element.className.replace(reg, ' ')
    }
}
