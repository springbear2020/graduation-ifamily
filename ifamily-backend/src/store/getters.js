const getters = {
    sidebar: state => state.app.sidebar,
    size: state => state.app.size,
    device: state => state.app.device,
    visitedViews: state => state.tagsView.visitedViews,
    cachedViews: state => state.tagsView.cachedViews,
    errorLogs: state => state.errorLog.logs,
    token: state => state.user.token,
    userInfo: state => state.user.userInfo,
    menus: state => state.user.menus,
    permission_routes: state => state.permission.routes
}

export default getters
