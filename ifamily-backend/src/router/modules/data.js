import Layout from '@/components/layout/Layout'

const dataRoutes = {
    path: '/data',
    component: Layout,
    redirect: '/data/index',
    name: 'DataManagement',
    meta: {
        title: '数据管理',
        icon: 'chart'
    },
    children: [
        {
            path: '/data/index',
            component: () => import('@/views/data'),
            name: 'DataManagement',
            meta: {title: '数据管理', icon: 'chart'}
        }
    ]
}
export default dataRoutes
