import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import {getToken} from "@/utils/auth";

Vue.use(VueRouter)

export const constantRoutes = [
    {
        path: '/',
        redirect: '/home'
    },
    // 404
    {
        path: '/404',
        component: () => import('@/views/404')
    },
    // user
    {
        path: '/user/login',
        component: () => import('@/views/user/login'),
    },
    {
        path: '/user/reset/:type',
        component: () => import('@/views/user/reset'),
    },
    // home
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
    // mine
    {
        path: '/mine',
        component: () => import('@/views/mine'),
        meta: {footerShow: true},
    },
    {
        path: '/mine/about',
        component: () => import('@/views/mine/about'),
    },
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
        path: '/mine/security',
        component: () => import('@/views/mine/security'),
    },
    {
        path: '/mine/security/form/:type',
        component: () => import('@/views/mine/security/form'),
    },
    {
        path: '/mine/security/devices',
        component: () => import('@/views/mine/security/devices'),
    },
    {
        path: '/mine/contact',
        component: () => import('@/views/mine/contact'),
    },
    // family
    {
        path: '/family',
        component: () => import('@/views/family'),
        meta: {footerShow: true}
    },
    {
        path: '/family/album',
        component: () => import('@/views/family/album'),
    },
    {
        path: '/family/info',
        component: () => import('@/views/family/info'),
    },
    {
        path: '/family/list',
        component: () => import('@/views/family/list'),
    },
    {
        path: '/family/members',
        component: () => import('@/views/family/members'),
    },
    {
        path: '/family/members/people/:type',
        component: () => import('@/views/family/members/people'),
    },
    {
        path: '/family/tree/:type',
        component: () => import('@/views/family/tree'),
    },
    {
        path: '/family/notice',
        component: () => import('@/views/family/notice'),
    },
    {
        path: '/family/records',
        component: () => import('@/views/family/records'),
    },
    {
        path: '/family/manage',
        component: () => import('@/views/family/manage'),
    },
    {
        path: '/family/manage/form/:type',
        component: () => import('@/views/family/manage/form'),
    },
    {
        path: '/family/manage/members',
        component: () => import('@/views/family/manage/members'),
    },
    {
        path: '/family/manage/members/add/:type',
        component: () => import('@/views/family/manage/members/add'),
    },
    {
        path: '/family/manage/members/edit/:type',
        component: () => import('@/views/family/manage/members/edit'),
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
    // 404 page must be placed at the end
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
    // 基于本地存储的 token 实现用户免登录
    if (!token) {
        token = getToken()
        if (token) {
            store.commit('user/SET_TOKEN', token)
        }
    }

    if (token) {
        // 已登录且前往用户登录、注册、找回密码页面，跳转到首页
        const toPath = to.path
        if (toPath === '/user/login' || toPath === '/user/reset/0' || toPath === '/user/reset/1') {
            next('/home')
        }
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
