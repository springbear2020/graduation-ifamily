import request from "@/utils/request";

const aclAdminBaseUrl = '/acl/admin'

export function listAllPermissions() {
    return request({
        url: `${aclAdminBaseUrl}/permission`,
        method: 'get'
    })
}

export function deletePermission(permissionId) {
    return request({
        url: `${aclAdminBaseUrl}/permission/${permissionId}`,
        method: 'delete'
    })
}

export function savePermission(data) {
    return request({
        url: `${aclAdminBaseUrl}/permission`,
        method: 'post',
        data
    })
}

export function updatePermission(data) {
    return request({
        url: `${aclAdminBaseUrl}/permission`,
        method: 'put',
        data
    })
}

export function listAllMenus() {
    return request({
        url: `${aclAdminBaseUrl}/menu`,
        method: 'get'
    })
}

export function deleteMenu(menuId) {
    return request({
        url: `${aclAdminBaseUrl}/menu/${menuId}`,
        method: 'delete'
    })
}

export function saveMenu(data) {
    return request({
        url: `${aclAdminBaseUrl}/menu`,
        method: 'post',
        data
    })
}

export function updateMenu(data) {
    return request({
        url: `${aclAdminBaseUrl}/menu`,
        method: 'put',
        data
    })
}

export function listAllRoles() {
    return request({
        url: `${aclAdminBaseUrl}/role`,
        method: 'get'
    })
}

export function saveRole(data) {
    return request({
        url: `${aclAdminBaseUrl}/role`,
        method: 'post',
        data
    })
}

export function deleteRole(roleId) {
    return request({
        url: `${aclAdminBaseUrl}/role/${roleId}`,
        method: 'delete'
    })
}

export function updateRole(data) {
    return request({
        url: `${aclAdminBaseUrl}/role`,
        method: 'put',
        data
    })
}

export function listRolesOfAdmin(adminId) {
    return request({
        url: `${aclAdminBaseUrl}/role/${adminId}`,
        method: 'get'
    })
}

export function addAdminRole(adminId, roleId) {
    return request({
        url: `${aclAdminBaseUrl}/role/${adminId}/${roleId}`,
        method: 'post',
    })
}

export function removeAdminRole(adminId, roleId) {
    return request({
        url: `${aclAdminBaseUrl}/role/${adminId}/${roleId}`,
        method: 'delete',
    })
}
