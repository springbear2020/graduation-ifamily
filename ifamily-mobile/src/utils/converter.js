import dayjs from "dayjs"

/**
 * 公历日期转换为农历日期：2023-05-01 -> 二零二三年五月一日
 * @param dateStr 日期字符串
 * @param isLunar 是否农历日期
 * @returns {string} [公历]公历 2023年04月02日 [农历]农历 二零二三年四月二日
 */
export function solarToLunar(dateStr, isLunar) {
    // 非农历日期，直接返回公历
    if (!isLunar) {
        return '公历 ' + dayjs(dateStr).format('YYYY年MM月DD日')
    }

    const monthDays = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二', '十三', '十四', '十五', '十六', '十七',
        '十八', '十九', '二十', '二十一', '二十二', '二十三', '二十四', '二十五', '二十六', '二十七', '二十八', '二十九', '三十', '三十一'];

    let year = dayjs(dateStr).year().toString()
    let month = dayjs(dateStr).month() + 1
    let day = dayjs(dateStr).date()

    let result = '农历 '
    // 年
    result += (monthDays[year[0]] + monthDays[year[1]] + monthDays[year[2]] + monthDays[year[3]] + '年')
    // 月
    result += (monthDays[month] + '月')
    // 日
    result += (monthDays[day] + '日')

    return result
}

/**
 * 将阿拉伯整数数字转换为中文汉字
 * @param num 阿拉伯整数数字
 * @returns {string} 中文汉字
 */
export function numToChinese(num) {
    // 非数字
    if (!/^\d*(\.\d*)?$/.test(num)) {
        return undefined;
    }

    // 浮点数
    num = parseFloat(num);
    if (isNaN(num)) {
        return undefined;
    }

    let integerNum = num.toString().split(".")[0];
    // 值过大
    if (integerNum.length > 16) {
        return undefined;
    }

    const cnNums = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
    const cnIntRanice = ["", "十", "百", "千"];
    const cnIntUnits = ["", "万", "亿", "兆"];

    let parts = Array.from(integerNum).reverse().map((item, index) => {
        return cnNums[parseInt(item)] + cnIntRanice[index % 4] + (index % 4 === 0 ? cnIntUnits[Math.floor(index / 4)] : "");
    }).reverse();

    let chineseStr = parts.join("")
        .replace(/零([十百千])/g, "零")
        .replace(/零+/g, "零")
        .replace(/零([万亿兆])/g, "$1")
        .replace(/亿万/, "亿");
    if (chineseStr.charAt(chineseStr.length - 1) === "零") {
        chineseStr = chineseStr.substr(0, chineseStr.length - 1);
    }

    return chineseStr;
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
    } else if (diff >= 1 && diff <= 7) {
        return `${diff}天前`;
    }

    return dateStr
}

/**
 * 计算朋友圈格式时间
 * @param dateStr 时间字符串
 * @returns {string} 计算后的字符串
 */
export function momentDate(dateStr) {
    const now = dayjs().format('YYYY-MM-DD')
    const date = dayjs(dateStr).format('YYYY-MM-DD')
    const diff = dayjs(now).diff(date, 'day')

    // 今年内
    if (new Date().getFullYear() === new Date(now).getFullYear()) {
        if (diff === 0) {
            return '今天 ' + dayjs(dateStr).format('HH:mm')
        } else if (diff === 1) {
            return '昨天 ' + dayjs(dateStr).format('HH:mm')
        } else if (diff === 2) {
            return '前天 ' + dayjs(dateStr).format('HH:mm')
        } else {
            return dayjs(dateStr).format('MM月DD日 HH:mm')
        }
    }

    return dayjs(dateStr).format('YYYY年MM月DD日 HH:mm')
}

/**
 * 获取对象中的非空键值，合并所有的非空键值为一个新对象
 * @param obj 对象
 * @returns {{}} 去除空键值的新对象或 {}
 */
export function mergeNonNullValues(obj) {
    return Object.keys(obj).reduce((acc, key) => {
        if (obj[key]) {
            acc[key] = obj[key];
        }
        return acc;
    }, {});
}
