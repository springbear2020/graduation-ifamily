<template>
  <div class="app-container">
    <el-button type="primary" icon="el-icon-plus" @click="dialogType = 'new'; dialogVisible = true">新增</el-button>

    <!-- data table -->
    <el-table :data="roleDetailsList" style="width: 100%; margin-top:20px;" border>
      <el-table-column align="center" label="#" width="50" type="index"/>
      <el-table-column label="角色名称" prop="role.name" sortable autofocus/>
      <el-table-column label="角色描述" prop="role.description" sortable/>

      <el-table-column align="center" label="角色状态" sortable>
        <template slot-scope="scope">
            <span>
              <el-tag type="danger" v-if="scope.row.role.status">已禁用</el-tag>
              <el-tag v-else>启用中</el-tag>
            </span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建时间" prop="role.created" sortable/>
      <el-table-column align="center" label="修改时间" prop="role.modified" sortable/>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="openResourceDrawer(scope)">详情</el-button>
          <el-button type="warning" size="small" @click="openEditDialog(scope)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteRole(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- form dialog -->
    <el-dialog
        :visible.sync="dialogVisible"
        :title="dialogType === 'edit'? '编辑角色' : '新增角色'"
        @close="resetDialogForm"
    >
      <el-form
          label-width="80px"
          label-position="left"
          ref="roleForm"
          :model="formData"
          :rules="formRules"
      >

        <el-form-item label="名称" required prop="name">
          <el-input v-model="formData.name" placeholder="角色名称"/>
        </el-form-item>
        <el-form-item label="描述" required prop="description">
          <el-input
              v-model="formData.description"
              :autosize="{ minRows: 2, maxRows: 5}"
              type="textarea"
              placeholder="角色描述"
          />
        </el-form-item>
        <el-form-item label="禁用" required prop="status">
          <el-switch v-model="formData.status"></el-switch>
        </el-form-item>

        <div style="text-align: right;">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogConfirm">确认</el-button>
        </div>
      </el-form>

      <el-tabs v-model="activeName" type="card" style="margin-top: 20px">

        <!-- enable menu list -->
        <el-tab-pane label="菜单资源" name="menu">
          <el-table
              ref="menuTable"
              border
              :data="enableMenuList"
              @selection-change="(rows) => {tabData.selectedMenuIds = rows.map(item => item.id)}"
          >
            <el-table-column type="selection" width="55"/>
            <el-table-column align="center" label="#" width="50" type="index"/>
            <el-table-column label="菜单名称" prop="title" sortable/>
            <el-table-column label="菜单描述" prop="description" sortable/>
            <el-table-column label="路由路径" prop="path" sortable/>
          </el-table>
        </el-tab-pane>

        <!-- enable permission list -->
        <el-tab-pane label="权限资源" name="permission">
          <el-table
              ref="permissionTable"
              border
              :data="enablePermissionList"
              @selection-change="(rows) => {tabData.selectedPermissionIds = rows.map(item => item.id)}"
          >
            <el-table-column type="selection" width="55"/>
            <el-table-column align="center" label="#" width="50" type="index"/>
            <el-table-column label="权限名称" prop="name" sortable/>
            <el-table-column label="权限描述" prop="description" sortable/>
            <el-table-column label="权限路径" prop="path" sortable/>
          </el-table>
        </el-tab-pane>

      </el-tabs>

    </el-dialog>

    <!-- role resources drawer -->
    <el-drawer :visible.sync="drawerShow" title="角色详情">
      <div style="padding: 0 20px">

        <!-- role details info -->
        <el-card header="角色信息">
          <el-descriptions class="margin-top" border>
            <el-descriptions-item label="角色 ID">
              {{ drawerData.role.id }}
            </el-descriptions-item>
            <el-descriptions-item label="创建时间">
              {{ drawerData.role.created }}
            </el-descriptions-item>
            <el-descriptions-item label="修改时间">
              {{ drawerData.role.modified }}
            </el-descriptions-item>
            <el-descriptions-item label="角色名称">
              {{ drawerData.role.name }}
            </el-descriptions-item>
            <el-descriptions-item label="角色描述">
              {{ drawerData.role.description }}
            </el-descriptions-item>
            <el-descriptions-item label="角色状态">
            <span>
              <el-tag type="danger" v-if="drawerData.role.status">已禁用</el-tag>
              <el-tag v-else>启用中</el-tag>
            </span>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- role permission list -->
        <el-card header="权限列表" style="margin-top: 20px" v-if="drawerData.rolePermissions.length > 0">
          <el-table :data="drawerData.rolePermissions" stripe>
            <el-table-column label="权限路径" prop="path"/>
            <el-table-column label="权限名称" prop="name"/>
            <el-table-column label="权限状态">
              <template slot-scope="scope">
                <span>
                  <el-tag type="danger" v-if="scope.row.status">已禁用</el-tag>
                  <el-tag v-else>启用中</el-tag>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="权限描述" prop="description"/>
          </el-table>
        </el-card>

        <!-- role menu list -->
        <el-card header="菜单列表" style="margin-top: 20px" v-if="drawerData.roleMenus.length > 0">
          <el-table :data="drawerData.roleMenus" stripe>
            <el-table-column label="路由路径" prop="path"/>
            <el-table-column label="菜单名称" prop="title"/>
            <el-table-column label="菜单状态">
              <template slot-scope="scope">
                <span>
                  <el-tag type="danger" v-if="scope.row.status">已禁用</el-tag>
                  <el-tag v-else>启用中</el-tag>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="菜单描述" prop="description"/>
          </el-table>
        </el-card>

        <el-card header="用户列表" style="margin-top: 20px" v-if="drawerData.roleUsers.length > 0">
          <el-table :data="drawerData.roleUsers" stripe>
            <el-table-column label="UID" prop="id"/>
            <el-table-column label="用户名" prop="username"/>
            <el-table-column label="用户状态">
              <template slot-scope="scope">
                <span>
                  <el-tag type="danger" v-if="scope.row.status">已禁用</el-tag>
                  <el-tag v-else>启用中</el-tag>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="上次登录" prop="lastLogin"/>
          </el-table>
        </el-card>

        <el-card header="管理员列表" style="margin-top: 20px" v-if="drawerData.roleAdmins.length > 0">
          <el-table :data="drawerData.roleAdmins" stripe>
            <el-table-column label="UID" prop="id"/>
            <el-table-column label="用户名" prop="username"/>
            <el-table-column label="用户状态">
              <template slot-scope="scope">
                <span>
                  <el-tag type="danger" v-if="scope.row.status">已禁用</el-tag>
                  <el-tag v-else>启用中</el-tag>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="上次登录" prop="lastLogin"/>
          </el-table>
        </el-card>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {deepClone} from "@/utils";

export default {
  name: "SystemRole",
  data() {
    return {
      roleDetailsList: [],
      menuList: [],
      permissionList: [],
      // dialog form
      dialogVisible: false,
      dialogType: 'new',
      formData: {
        id: undefined,
        name: undefined,
        description: undefined,
        // disabled：[0]No [1]Yes
        status: false
      },
      formRules: {
        name: [{required: true, message: '角色名称值不能为空'}],
        description: [{required: true, message: '角色描述值不能为空'}],
      },
      // tabs
      activeName: 'menu',
      tabData: {
        selectedMenuIds: [],
        selectedPermissionIds: []
      },
      // drawer
      drawerShow: false,
      drawerData: {
        role: {},
        roleMenus: [],
        rolePermissions: [],
        roleUsers: [],
        roleAdmins: []
      }
    }
  },
  created() {
    this.fetchRoles()
    this.fetchMenus()
    this.fetchPermissions()
  },
  computed: {
    enableMenuList() {
      // status === 0 meaning the menu is not disabled
      return this.menuList.filter(item => item.status === 0) || []
    },
    enablePermissionList() {
      // status === 0 meaning the permission is not disabled
      return this.permissionList.filter(item => item.status === 0) || []
    }
  },
  methods: {
    fetchRoles() {
      this.$api.system.listAllRoles().then(roles => {
        this.roleDetailsList = roles
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    fetchMenus() {
      this.$api.system.listAllMenus().then(menus => {
        this.menuList = menus
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    fetchPermissions() {
      this.$api.system.listAllPermissions().then(permissions => {
        this.permissionList = permissions
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    resetDialogForm() {
      this.$nextTick(() => {
        this.$refs['roleForm'].resetFields()
        this.$refs['menuTable'].clearSelection()
        this.$refs['permissionTable'].clearSelection()
      })
    },

    dialogConfirm() {
      this.$refs["roleForm"].validate(result => {
        if (result) {
          // treatment the `status` specially, whether disabled ? [0]No [1]Yes
          this.formData.status = this.formData.status ? 1 : 0

          const isAdd = this.dialogType === 'new'
          if (isAdd) {
            this.saveRole()
          } else {
            this.editRole()
          }
        }
      })
    },

    saveRole() {
      this.formatRequestParams()

      this.$api.system.saveRole(this.formData).then(() => {
        this.dialogVisible = false
        this.$message.success('保存成功')
        this.fetchRoles()
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    deleteRole({$index, row}) {
      this.$confirm(`您确定要删除【${row.role.name}】系统角色吗？`, '删除提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.$api.system.deleteRole(row.role.id).then(() => {
          this.roleDetailsList.splice($index, 1)
          this.$message.success('删除成功')
        }).catch(msg => {
          this.$notify.error(msg)
        })
      }).catch(() => {
      })
    },

    openEditDialog({row}) {
      this.formData = deepClone(row.role)
      // treatment the `status` specially
      this.formData.status = !!row.role.status
      this.dialogType = 'edit'
      this.dialogVisible = true

      // checked some checkboxes of menu and permission tab
      const {menuIds, permissionIds} = row
      this.$nextTick(() => {
        this.enableMenuList.forEach(item => {
          if (menuIds && menuIds.includes(item.id)) {
            this.$refs.menuTable.toggleRowSelection(item)
          }
        })

        this.enablePermissionList.forEach(item => {
          if (permissionIds && permissionIds.includes(item.id)) {
            this.$refs.permissionTable.toggleRowSelection(item)
          }
        })
      })
    },

    editRole() {
      this.formatRequestParams()

      this.$api.system.updateRole(this.formData).then(() => {
        this.dialogVisible = false
        this.$message.success('更新成功')
        this.fetchRoles()
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    formatRequestParams() {
      // merge and format the request parameters
      const menuIds = this.tabData.selectedMenuIds
      if (menuIds && menuIds.length > 0) {
        this.formData.menuIds = menuIds
      }
      const permissionIds = this.tabData.selectedPermissionIds
      if (permissionIds && permissionIds.length > 0) {
        this.formData.permissionIds = permissionIds
      }
    },

    openResourceDrawer({row}) {
      this.drawerData.roleMenus = []
      this.drawerData.rolePermissions = []
      this.drawerData.roleUsers = []
      this.drawerData.roleAdmins = []

      const {role, menuIds, permissionIds, users, admins} = row
      this.drawerData.role = role

      // set the associated resources of current role
      this.menuList.forEach(item => {
        if (menuIds && menuIds.includes(item.id)) {
          this.drawerData.roleMenus.push(item)
        }
      })
      this.permissionList.forEach(item => {
        if (permissionIds && permissionIds.includes(item.id)) {
          this.drawerData.rolePermissions.push(item)
        }
      })

      this.drawerData.roleUsers = users || []
      this.drawerData.roleAdmins = admins || []

      this.drawerShow = true
    }

  }
}
</script>
