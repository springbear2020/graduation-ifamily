import dayjs from "dayjs"

/**
 * 日期转换
 * @param date 日期字符串
 * @param isLunar 是否农历日期
 * @returns {string} [公历]公历 2023年04月02日 [农历]农历 二零二三年四月二日
 */
export function convert(date, isLunar) {
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
