import axios from 'axios'
import responseCode from '@/api/response-code'
import {getToken} from '@/utils/auth'
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import {Notify} from 'vant';

const service = axios.create({
    baseURL: 'http://10.129.190.209:8888',
    timeout: 5000
})

/*
 * 请求拦截器
 */
service.interceptors.request.use(
    config => {
        nprogress.start()

        // 将 token 信息携带在请求头中
        const token = getToken()
        if (token) {
            config.headers['Authentication'] = token
        }

        return config
    },
    error => {
        return Promise.reject(error)
    }
)

/*
 * 响应拦截器
 */
service.interceptors.response.use(
    response => {
        nprogress.done()

        const restData = response.data
        const { code } = restData
        // [200]成功，直接返回数据
        if (code === responseCode.SUCCESS) {
            return restData.data
        } else {
            if (code === responseCode.SERVER_INTERNAL_ERROR || code === responseCode.SERVICE_UNAVAILABLE) {
                // [500]内部异常 [503]服务不可用
                Notify({
                    type: 'danger',
                    message: '服务器被吃了( ╯□╰ )'
                })
            } else if (code === responseCode.UNAUTHORIZED || code === responseCode.FORBIDDEN) {
                // [401]身份认证 [403]拒绝访问
                Notify({
                    type: 'danger',
                    message: `[${restData.desc}]${restData.data}`
                })
            } else {
                return Promise.reject(restData)
            }
        }
    },
    error => {
        nprogress.done()
        return Promise.reject(error)
    }
)

export default service
