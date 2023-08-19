import Layout from '@/components/layout/Layout'

const systemRoutes = {
    path: '/system',
    component: Layout,
    redirect: '/system/role',
    name: 'SystemSecurity',
    meta: {
        title: '系统安全',
        icon: 'security'
    },
    children: [
        {
            path: '/system/role',
            component: () => import('@/views/system/role/index'),
            name: 'SystemRole',
            meta: {title: '系统角色', icon: 'peoples'}
        },
        {
            path: '/system/menu',
            component: () => import('@/views/system/menu/index'),
            name: 'SystemMenu',
            meta: {title: '菜单列表', icon: 'menu'}
        },
        {
            path: '/system/permission',
            component: () => import('@/views/system/permission/index'),
            name: 'SystemPermission',
            meta: {title: '权限资源', icon: 'lock'}
        }
    ]
}
export default systemRoutes
