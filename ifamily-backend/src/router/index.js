import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/components/layout/Layout'

import systemRoutes from "@/router/modules/system";

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * Constant routes, don't need permission control
 */
export const constantRoutes = [
    {
        path: '/login',
        component: () => import('@/views/login/index'),
        hidden: true,
        meta: {title: '登录'}
    },
    {
        path: '/404',
        component: () => import('@/views/error/404'),
        hidden: true,
        meta: {title: '404'}
    },
    {
        path: '/403',
        component: () => import('@/views/error/403'),
        hidden: true,
        meta: {title: '403'}
    },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [{
            path: '/dashboard',
            component: () => import('@/views/dashboard/index'),
            name: 'Dashboard',
            meta: {title: '首页', icon: 'dashboard', affix: true}
        }]
    },
    {
        path: '/mine',
        component: Layout,
        redirect: '/mine/index',
        hidden: true,
        children: [{
            path: '/mine/index',
            component: () => import('@/views/mine/index'),
            name: 'UserProfile',
            meta: {title: '我的'}
        }]
    },
    {
        /*
         * Solve the problem that the request address does not change and the routing page does not refresh，
         * details see https://panjiachen.gitee.io/vue-element-admin-site/zh/guide/essentials/router-and-nav.html
         */
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [{
            path: '/redirect/:path(.*)',
            component: () => import('@/views/redirect/index')
        }]
    },
]

/**
 * Async routes, need to grant the authorities, but except the `{path: '*', redirect: '/404', hidden: true}`
 */
export const asyncRoutes = [
    systemRoutes,

    // error 404 page must be placed at the end, can't move it to the constant routes
    {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

// Details see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}

const router = createRouter()

export default router
