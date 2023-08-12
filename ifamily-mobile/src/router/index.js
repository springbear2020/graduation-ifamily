import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

let router = new VueRouter({
    routes: [
        // home
        {
            path: '/',
            redirect: '/discover'
        },
        // user
        {
            path: '/user',
            component: () => import('@/views/user'),
            meta: {footerShow: true},
        },
        {
            path: '/user/login',
            component: () => import('@/views/user/login'),
        },
        {
            path: '/user/password',
            component: () => import('@/views/user/password'),
        },
        {
            path: '/user/settings',
            component: () => import('@/views/user/settings'),
        },
        {
            path: '/user/settings/account',
            component: () => import('@/views/user/settings/account-security'),
        },
        {
            path: '/user/personal',
            component: () => import('@/views/user/personal'),
        },
        // discover
        {
            path: '/discover',
            component: () => import('@/views/discover'),
            meta: {footerShow: true}
        },
        {
            path: '/discover/message',
            component: () => import('@/views/discover/message'),
        },
        // family
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
            path: '/family/create',
            component: () => import('@/views/family/create'),
        },
        {
            path: '/family/info',
            component: () => import('@/views/family/info'),
        },
        {
            path: '/family/tree',
            component: () => import('@/views/family/tree/index'),
        },
        {
            path: '/family/members',
            component: () => import('@/views/family/members'),
        },
        {
            path: '/family/people',
            component: () => import('@/views/family/people'),
        },
        {
            path: '/family/album',
            component: () => import('@/views/family/album'),
        },
        {
            path: '/family/manage',
            component: () => import('@/views/family/manage'),
        },
    ],
    scrollBehavior() {
        // to top
        return {y: 0}
    }
})

export default router