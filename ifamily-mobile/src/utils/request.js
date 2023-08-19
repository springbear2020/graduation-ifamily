import axios from 'axios'
import responseCode from '@/api/response-code'
import {getToken, TOKEN_KEY} from '@/utils/auth'
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import {Notify} from 'vant';

// 隐藏 nprogress 进度环
// nprogress.configure({showSpinner: false})

const service = axios.create({
    baseURL: 'http://10.128.184.117:8888',
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
            config.headers[TOKEN_KEY] = token
        }

        return config
    },
    err => {
        return Promise.reject(err)
    }
)

/*
 * 响应拦截器
 */
service.interceptors.response.use(
    res => {
        nprogress.done()
        const jsonData = res.data
        const {code} = jsonData

        // [200]成功，直接返回数据
        if (code === responseCode.SUCCESS) {
            return jsonData.data
        } else {
            if (code === responseCode.SERVER_INTERNAL_ERROR || code === responseCode.SERVICE_UNAVAILABLE) {
                // [500]内部异常 [503]服务不可用
                Notify({
                    type: 'danger',
                    message: '服务器维护中，请稍后重试'
                })
            } else if (code === responseCode.UNAUTHORIZED || code === responseCode.FORBIDDEN) {
                // [401]身份认证 [403]拒绝访问
                Notify({
                    type: 'danger',
                    message: `[${jsonData.desc}]${jsonData.data}`
                })
            }
            return Promise.reject(jsonData)
        }
    },
    err => {
        nprogress.done()
        Notify({
            type: 'danger',
            message: '网络连接超时，请稍后重试'
        })
        return Promise.reject(err)
    }
)

export default service
