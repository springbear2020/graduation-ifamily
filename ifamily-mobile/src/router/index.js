import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

let router = new VueRouter({
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        // 404
        {
            path: '/404',
            component: () => import('@/views/404')
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
        // mine
        {
            path: '/mine',
            component: () => import('@/views/mine'),
            meta: {footerShow: true},
        },
        {
            path: '/mine/login',
            component: () => import('@/views/mine/login'),
        },
        {
            path: '/mine/password/:type',
            component: () => import('@/views/mine/password'),
        },
        {
            path: '/mine/settings',
            component: () => import('@/views/mine/settings'),
        },
        {
            path: '/mine/settings/security',
            component: () => import('@/views/mine/settings/security'),
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
            path: '/family/members/people',
            component: () => import('@/views/family/members/people'),
        },
        {
            path: '/family/tree',
            component: () => import('@/views/family/tree'),
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
            path: '/family/manage/members/add',
            component: () => import('@/views/family/manage/members/add'),
        },
        {
            path: '/family/manage/members/edit',
            component: () => import('@/views/family/manage/members/edit'),
        },
        {
            path: '/family/manage/seniority',
            component: () => import('@/views/family/manage/seniority'),
        },
        // 404 page must be placed at the end
        {
            path: '*',
            redirect: '/404'
        }
    ],
    scrollBehavior() {
        return {y: 0}
    }
})

export default router