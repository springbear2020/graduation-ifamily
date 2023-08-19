import Layout from '@/components/layout/Layout'

const userRoutes = {
    path: '/user',
    component: Layout,
    redirect: '/user/index',
    name: 'UserManagement',
    meta: {
        title: '用户管理',
        icon: 'user'
    },
    children: [
        {
            path: '/user/admin',
            component: () => import('@/views/user/admin'),
            name: 'AdminUser',
            meta: {title: '用户管理', icon: 'admin'}
        }
    ]
}
export default userRoutes
