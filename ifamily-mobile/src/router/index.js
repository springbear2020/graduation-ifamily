import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

let router = new VueRouter({
    routes: [
        {
            path: '/',
            redirect: '/user'
        },
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
        {
            path: '/home',
            component: () => import('@/views/home'),
            meta: {footerShow: true}
        },
        {
            path: '/home/announcement',
            component: () => import('@/views/home/announcement'),
        },
        {
            path: '/message',
            component: () => import('@/views/message'),
            meta: {footerShow: true}
        },
        {
            path: '/message/contacts',
            component: () => import('@/views/message/contacts'),
        },
        {
            path: '/genealogy',
            component: () => import('@/views/genealogy'),
            meta: {footerShow: true}
        },
    ],
    scrollBehavior() {
        // 路由切换时回到最顶部
        return {y: 0}
    }
})

export default router