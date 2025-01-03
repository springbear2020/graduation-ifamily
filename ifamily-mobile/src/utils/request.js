import axios from 'axios'
import {getToken, TOKEN_KEY} from '@/utils/auth'
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import {Notify} from 'vant';
import store from '@/store'

// 隐藏 nprogress 进度环
nprogress.configure({showSpinner: false})

const service = axios.create({
    baseURL: '/ifamily-gateway-api',
    timeout: 10000
})

/**
 * 请求拦截器
 */
service.interceptors.request.use(
    config => {
        if (!store.state.loading.isLoading) {
            nprogress.start()
        }

        const token = getToken();
        if (token) {
            config.headers[TOKEN_KEY] = token
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
        nprogress.done()

        const jsonData = res.data
        const {code} = jsonData

        if (code === 200) {
            // [200]成功，直接返回数据
            return jsonData.data
        } else {
            // [500]内部异常 [503]服务不可用 [401]身份认证 [403]拒绝访问
            if (code === 500 || code === 503 || code === 401 || code === 403) {
                Notify({
                    type: 'danger',
                    message: jsonData.data || jsonData.desc
                })
            }

            // [code]失败，返回提示消息
            return Promise.reject(jsonData.data || jsonData.desc)
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
