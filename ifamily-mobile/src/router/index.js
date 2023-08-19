import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import {getToken} from "@/utils/auth";
import {Notify} from "vant";

Vue.use(VueRouter)

export const constantRoutes = [
    {
        path: '/',
        redirect: '/home'
    },
    /// user ===========================================================================================================
    {
        path: '/user/login',
        component: () => import('@/views/user/login'),
    },
    {
        path: '/user/reset/:type',
        component: () => import('@/views/user/reset'),
    },
    /// mine ===========================================================================================================
    {
        path: '/mine',
        component: () => import('@/views/mine'),
        meta: {footerShow: true},
    },
    {
        path: '/mine/info',
        component: () => import('@/views/mine/info'),
    },
    {
        path: '/mine/info/form/:type',
        component: () => import('@/views/mine/info/form'),
    },
    {
        path: '/mine/settings',
        component: () => import('@/views/mine/settings'),
    },
    {
        path: '/mine/settings/security',
        component: () => import('@/views/mine/settings/security'),
    },
    {
        path: '/mine/settings/security/uid',
        component: () => import('@/views/mine/settings/security/uid'),
    },
    {
        path: '/mine/settings/security/form/:type',
        component: () => import('@/views/mine/settings/security/form'),
    },
    {
        path: '/mine/settings/security/devices',
        component: () => import('@/views/mine/settings/security/devices'),
    },
    /// family =========================================================================================================
    {
        path: '/family',
        component: () => import('@/views/family'),
        meta: {footerShow: true}
    },
    {
        path: '/family/list',
        component: () => import('@/views/family/list'),
    },
    {
        path: '/family/info',
        component: () => import('@/views/family/info'),
    },
    {
        path: '/family/info/form/:type',
        component: () => import('@/views/family/info/form'),
    },
    {
        path: '/family/tree/:type',
        component: () => import('@/views/family/tree'),
    },
    {
        path: '/family/member',
        component: () => import('@/views/family/member'),
    },
    {
        path: '/family/member/info/:type',
        component: () => import('@/views/family/member/info'),
    },
    {
        path: '/family/member/edit/:type',
        component: () => import('@/views/family/member/edit'),
    },
    {
        path: '/family/member/add/:type',
        component: () => import('@/views/family/member/add'),
    },
    {
        path: '/family/revision',
        component: () => import('@/views/family/revision'),
    },
    {
        path: '/family/notice',
        component: () => import('@/views/family/notice'),
    },

    // *****************************************************************************************************************

    {
        path: '/family/manage/member/init',
        component: () => import('@/views/family/manage/member/init'),
    },
    {
        path: '/family/album',
        component: () => import('@/views/family/album'),
    },
    {
        path: '/family/manage',
        component: () => import('@/views/family/manage'),
    },
    {
        path: '/family/manage/member',
        component: () => import('@/views/family/manage/member'),
    },
    {
        path: '/family/manage/seniority',
        component: () => import('@/views/family/manage/seniority'),
    },
    {
        path: '/family/manage/access',
        component: () => import('@/views/family/manage/access'),
    },
    {
        path: '/family/manage/permission',
        component: () => import('@/views/family/manage/permission'),
    },

    // *****************************************************************************************************************

    {
        path: '/mine/family',
        component: () => import('@/views/mine/family'),
    },
    {
        path: '/mine/family/album',
        component: () => import('@/views/mine/family/album'),
    },
    {
        path: '/mine/moments',
        component: () => import('@/views/mine/moments'),
    },
    {
        path: '/mine/moments/post',
        component: () => import('@/views/mine/moments/post'),
    },
    {
        path: '/mine/memorial',
        component: () => import('@/views/mine/memorial'),
    },
    {
        path: '/mine/contact',
        component: () => import('@/views/mine/contact'),
    },

    /// home ===========================================================================================================
    {
        path: '/home',
        component: () => import('@/views/home'),
        meta: {footerShow: true}
    },
    {
        path: '/home/message',
        component: () => import('@/views/home/message'),
    },
    {
        path: '/home/message/chat',
        component: () => import('@/views/home/message/chat'),
    },
    /// 404 page must be placed at the end =============================================================================
    {
        path: '/404',
        component: () => import('@/views/404')
    },
    {
        path: '*',
        redirect: '/404'
    }
]

const createRouter = () => new VueRouter({
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}

/*
 * 全局前置路由守卫
 */
router.beforeEach((to, from, next) => {
    let token = store.state.user.token
    // 基于本地存储的 token 实现用户免密登录
    if (!token) {
        token = getToken()
        if (token) {
            store.commit('user/SET_TOKEN', token)
        }
    }

    if (token) {
        // 是否存在用户信息，不存在则派发 action 查询用户信息
        let uid = store.state.user.user.id
        if (!uid) {
            // 派发 action 获取用户信息，而后放行
            store.dispatch('user/getUser').then(() => {
                next()
            }).catch(() => {
                // 用户信息获取失败，令牌已过期，尝试刷新令牌
                store.dispatch('user/refreshUserToken').then(() => {
                    next()
                }).catch(() => {
                    // 请求刷新令牌失败，退出登录
                    store.dispatch('genealogy/logout').then(() => {
                        store.dispatch('user/logout').then(() => {
                            Notify({
                                type: 'danger',
                                message: '身份令牌已失效，即将前往登录页',
                                duration: 3000,
                                onClose: () => next('/user/login')
                            })
                        })
                    })
                })
            })
        }

        const toPath = to.path;
        // 已登录且前往用户登录、注册、找回密码页面，跳转到首页
        if (toPath === '/user/login' || toPath === '/user/reset/0' || toPath === '/user/reset/1') {
            next('/')
        }

        // 已登录，其它情况一律放行
        next();
    } else {
        // 未登录且前往用户登录、注册页面则直接放行，否则跳转到登录页面
        if (to.path.indexOf('/user') !== -1) {
            next()
        } else {
            next(`/user/login?redirect=${to.path}`)
        }
    }

})

export default router
