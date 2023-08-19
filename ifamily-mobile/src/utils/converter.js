import dayjs from "dayjs"

/**
 * 日期转换
 * @param date 日期字符串
 * @param isLunar 是否农历日期
 * @returns {string} [公历]公历 2023年04月02日 [农历]农历 二零二三年四月二日
 */
export function solarToLunar(date, isLunar) {
    // 非农历日期，直接返回公历
    if (!isLunar) {
        return '公历 ' + dayjs(date).format('YYYY年MM月DD日')
    }

    const months = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二', '十三', '十四', '十五', '十六', '十七',
        '十八', '十九', '二十', '二十一', '二十二', '二十三', '二十四', '二十五', '二十六', '二十七', '二十八', '二十九', '三十', '三十一'];

    let year = dayjs(date).year()
    year = year.toString()
    let month = dayjs(date).month() + 1
    let day = dayjs(date).date()

    let result = '农历 '
    // 年
    result += (months[year[0]] + months[year[1]] + months[year[2]] + months[year[3]] + '年')
    // 月
    result += (months[month] + '月')
    // 日
    result += (months[day] + '日')

    return result
}

/**
 * 将阿拉伯数字整数转换为中文汉字
 * @param num 一个数字
 * @returns {string} 将该数字转换为中文汉字后的字符串
 */
export function numToChinese(num) {
    // 非数字
    if (!/^\d*(\.\d*)?$/.test(num)) {
        return "Number is not valid!";
    }

    num = parseFloat(num);
    if (isNaN(num)) {
        return "Number is not valid!";
    }

    let integerNum = num.toString().split(".")[0];
    if (integerNum.length > 16) {
        return "Number is too large!";
    }

    const cnNums = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
    const cnIntRanice = ["", "十", "百", "千"];
    const cnIntUnits = ["", "万", "亿", "兆"];

    let parts = Array.from(integerNum).reverse().map((item, index) => {
        return cnNums[parseInt(item)] + cnIntRanice[index % 4] + (index % 4 === 0 ? cnIntUnits[Math.floor(index / 4)] : "");
    }).reverse();

    let chineseStr = parts.join("").replace(/零([十百千])/g, "零").replace(/零+/g, "零").replace(/零([万亿兆])/g, "$1").replace(/亿万/, "亿");
    if (chineseStr.charAt(chineseStr.length - 1) === "零") {
        chineseStr = chineseStr.substr(0, chineseStr.length - 1);
    }

    return chineseStr;
}

/**
 * 获取对象中的非空属性值，并将所有的非空属性值合并为一个新对象
 * @param obj 对象
 * @returns {{}} 目标对象
 */
export function mergeNonNullValues(obj) {
    return Object.keys(obj).reduce((acc, key) => {
        if (obj[key]) {
            acc[key] = obj[key];
        }
        return acc;
    }, {});
}

/**
 * 计算传入日期与当前日期的差异，根据差异返回不同的字符串
 * @param dateStr 日期字符串
 * @returns {string} 计算后的字符串
 */
export function computedDate(dateStr) {
    const date = new Date(dateStr)
    const now = new Date();
    const diff = (now - date) / (1000 * 60 * 60 * 24);

    if (diff < 1) {
        return '今天';
    } else if (diff < 2) {
        return '昨天';
    } else if (diff < 7) {
        const days = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
        return days[date.getDay()];
    } else {
        return dateStr
    }
}


/*
 * 移除 params、data 中的 ''、null、undefined
 */
// function clearEmptyParam(config) {
//     ['data', 'params'].forEach(item => {
//         if (config[item]) {
//             const keys = Object.keys(config[item])
//             if (keys.length) {
//                 keys.forEach(key => {
//                     // 获取数据原始类型字符串，如 'String', 'Object', 'Null', 'Boolean', 'Number', 'Array'
//                     const rawType = Object.prototype.toString.call(config[item]).slice(8, -1)
//                     if (['', undefined, null].includes(config[item][key]) && ['Object'].includes(rawType)) {
//                         // 移除属性之前，进行深拷贝断开引用，避免影响页面
//                         // const _cloneDeep = require('lodash/cloneDeep')
//                         config[item] = _cloneDeep(config[item])
//                         delete config[item][key]
//                     }
//                 })
//             }
//         }
//     })
// }
