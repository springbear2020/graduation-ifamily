import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/page-title'

// NProgress Configuration
NProgress.configure({showSpinner: false})

// no redirect whitelist
const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
    // start progress bar
    NProgress.start()

    // set page title
    document.title = getPageTitle(to.meta.title)

    // determine whether the user has the token
    const hasToken = store.getters.token

    if (hasToken) {
        if (to.path === '/login') {
            // if is logged in, redirect to the home page
            NProgress.done()
            next({path: '/'})
        } else {
            // determine whether the user has obtained his personal information
            if (store.getters.userInfo.uid) {
                next()
            } else {
                try {
                    // ask server for the user's info, and get the route ant patterns the user can access
                    let {menus} = await store.dispatch('user/getInfo')
                    // ensure the `*` route generated for the 404 not found page redirection
                    menus = menus ? menus : []

                    // generate accessible routes map based on menus
                    const accessRoutes = await store.dispatch('permission/generateRoutes', menus);

                    // dynamically add accessible routes
                    router.addRoutes(accessRoutes)

                    // hack method to ensure that addRoutes is complete, set the replace: true, so the navigation will not leave a history record
                    next({...to, replace: true})
                } catch (err) {
                    Message.error({message: '身份令牌已过期，正在尝试刷新', duration: 3000})

                    // try to refresh token after 3 seconds
                    setTimeout(async () => {
                        try {
                            await store.dispatch('user/refreshUserToken')
                            next({...to, replace: true})
                        } catch (err) {
                            // get user info and refresh token request were failed, remove the tokens and go to login page to re-login
                            await store.dispatch('user/logout')
                            Message.error('身份令牌刷新失败，请重新登录')
                            NProgress.done()
                            next(`/login?redirect=${to.path}`)
                        }
                    }, 3000)
                }
            }
        }
    } else {
        /* has no token */
        if (whiteList.indexOf(to.path) !== -1) {
            // in the free login whitelist, go directly
            next()
        } else {
            // other pages that do not have permission to access are redirected to the login page.
            NProgress.done()
            next(`/login?redirect=${to.path}`)
        }
    }
})

router.afterEach(() => {
    // finish progress bar
    NProgress.done()
})
