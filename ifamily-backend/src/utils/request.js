import axios from 'axios'
import {getToken, TOKEN_KEY} from '@/utils/auth'
import {Message} from 'element-ui'
import {TOKEN_PREFIX} from "@/utils/auth"

export const BASE_REQUEST_URL = 'http://localhost:8888'

const service = axios.create({
    baseURL: BASE_REQUEST_URL,
    timeout: 10000
})

/**
 * 请求拦截器
 */
service.interceptors.request.use(
    config => {

        const accessToken = getToken();
        if (accessToken) {
            config.headers[TOKEN_KEY] = TOKEN_PREFIX + accessToken
        }

        return config
    },
    err => {
        return Promise.reject(err)
    }
)

/**
 * 响应拦截器
 */
service.interceptors.response.use(
    res => {
        const jsonData = res.data
        const {code} = jsonData

        // [200]success, return the data directly
        if (code === 200) {
            return jsonData.data
        }

        /*
         * [500]server internal exception
         * [503]service not available
         * [406]request failed
         * [412]incorrect request conditions
         * [401]not authentication
         * [403]access denied
         */
        return Promise.reject(jsonData.data || jsonData.desc)
    },
    err => {
        Message({
            message: '网络链接超时，请稍后重试',
            type: 'error',
            duration: 3000
        })

        return Promise.reject(err)
    }
)

export default service
