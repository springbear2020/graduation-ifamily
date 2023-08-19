<template>
  <div class="app-container">
    <el-button type="primary" icon="el-icon-plus" @click="openAddDialog">新增</el-button>

    <!-- data table -->
    <el-table :data="permissionList" style="width: 100%; margin-top:20px;" border>
      <el-table-column align="center" label="#" width="50" type="index"/>
      <el-table-column label="权限名称" prop="name" sortable autofocus/>
      <el-table-column label="权限描述" prop="description" sortable/>
      <el-table-column label="权限路径" prop="path" sortable/>

      <el-table-column align="center" label="权限状态" sortable>
        <template slot-scope="scope">
            <span>
              <el-tag type="danger" v-if="scope.row.status">已禁用</el-tag>
              <el-tag v-else>启用中</el-tag>
            </span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建时间" prop="created" sortable/>
      <el-table-column align="center" label="修改时间" prop="modified" sortable/>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="warning" size="small" @click="openEditDialog(scope)">编辑</el-button>
          <el-button type="danger" size="small" @click="deletePermission(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- form dialog -->
    <el-dialog
        :visible.sync="dialogVisible"
        :title="dialogType === 'edit'? '编辑权限' : '新增权限'"
        @close="resetDialogForm"
    >
      <el-form
          label-width="80px"
          label-position="left"
          ref="permissionForm"
          :model="formData"
          :rules="formRules"
      >

        <el-form-item label="路径" required prop="path">
          <el-input v-model="formData.path" placeholder="权限路径"/>
        </el-form-item>
        <el-form-item label="名称" required prop="name">
          <el-input v-model="formData.name" placeholder="权限名称"/>
        </el-form-item>
        <el-form-item label="描述" required prop="description">
          <el-input
              v-model="formData.description"
              :autosize="{ minRows: 2, maxRows: 5}"
              type="textarea"
              placeholder="权限描述"
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

      <el-divider>/**/supervise/** 路径当用户登入系统后可访问，无需配置为系统权限资源</el-divider>

      <el-tabs v-model="activeName" type="card" style="margin-top: 20px">
        <el-tab-pane label="认证模块" name="auth" lazy>
          <tab-data-table :tab-data="tabData.auth" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
        <el-tab-pane label="权限模块" name="acl" lazy>
          <tab-data-table :tab-data="tabData.acl" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
        <el-tab-pane label="后台模块" name="backend" lazy>
          <tab-data-table :tab-data="tabData.backend" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
        <el-tab-pane label="家族模块" name="genealogy" lazy>
          <tab-data-table :tab-data="tabData.genealogy" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
        <el-tab-pane label="三方模块" name="manager" lazy>
          <tab-data-table :tab-data="tabData.manager" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
        <el-tab-pane label="社交模块" name="social" lazy>
          <tab-data-table :tab-data="tabData.social" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
        <el-tab-pane label="用户模块" name="user" lazy>
          <tab-data-table :tab-data="tabData.user" :permission-path-list="permissionPathList"/>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

  </div>
</template>

<script>
import TabDataTable from "@/views/system/permission/components/TabDataTable"
import {deepClone} from "@/utils"

export default {
  name: "SystemPermission",
  components: {TabDataTable},
  data() {
    return {
      permissionList: [],
      // dialog form
      dialogVisible: false,
      dialogType: 'new',
      formData: {
        id: undefined,
        name: undefined,
        path: undefined,
        description: undefined,
        // disabled：[0]No [1]Yes
        status: false
      },
      formRules: {
        name: [{required: true, message: '权限名称值不能为空'}],
        description: [{required: true, message: '权限描述值不能为空'}],
        path: [{required: true, message: '权限路径值不能为空'}]
      },
      // api-docs tabs
      activeName: 'auth',
      tabData: {
        auth: {},
        acl: {},
        backend: {},
        genealogy: {},
        manager: {},
        social: {},
        user: {}
      }
    }
  },
  created() {
    this.fetchPermissions()
  },
  watch: {
    activeName: {
      immediate: true,
      handler(val) {
        this.fetchTabDataList(val)
      }
    }
  },
  computed: {
    permissionPathList() {
      return this.permissionList.map(item => item.path) || []
    }
  },
  methods: {
    fetchPermissions() {
      this.$api.system.listAllPermissions().then(permissions => {
        this.permissionList = permissions
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    openAddDialog() {
      this.formData = Object.assign({}, this.$options.data().formData)
      this.dialogType = 'new'
      this.dialogVisible = true
    },

    fetchTabDataList(activeName) {
      // get different data list according to the active tab name
      switch (activeName) {
        case 'auth':
          if (Object.keys(this.tabData.auth).length === 0) {
            this.$api.doc.listAuthApiPaths().then(result => this.tabData.auth = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
        case 'acl':
          if (Object.keys(this.tabData.acl).length === 0) {
            this.$api.doc.listAclApiPaths().then(result => this.tabData.acl = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
        case 'backend':
          if (Object.keys(this.tabData.backend).length === 0) {
            this.$api.doc.listBackendApiPaths().then(result => this.tabData.backend = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
        case 'genealogy':
          if (Object.keys(this.tabData.genealogy).length === 0) {
            this.$api.doc.listGenealogyApiPaths().then(result => this.tabData.genealogy = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
        case 'manager':
          if (Object.keys(this.tabData.manager).length === 0) {
            this.$api.doc.listManagerApiPaths().then(result => this.tabData.manager = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
        case 'social':
          if (Object.keys(this.tabData.social).length === 0) {
            this.$api.doc.listSocialApiPaths().then(result => this.tabData.social = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
        case 'user':
          if (Object.keys(this.tabData.user).length === 0) {
            this.$api.doc.listUserApiPaths().then(result => this.tabData.user = result.data.paths).catch((err) => this.$notify.error(err))
          }
          break;
      }
    },

    deletePermission({$index, row}) {
      this.$confirm(`您确定要删除【${row.name}】权限资源吗？`, '删除提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.$api.system.deletePermission(row.id).then(() => {
          this.permissionList.splice($index, 1)
          this.$message.success('删除成功')
        }).catch(msg => {
          this.$notify.error(msg)
        })
      }).catch(() => {
      })
    },

    openEditDialog({row}) {
      this.formData = deepClone(row)
      // treatment the `status` specially
      this.formData.status = !!row.status
      this.dialogType = 'edit'
      this.dialogVisible = true
    },

    dialogConfirm() {
      this.$refs["permissionForm"].validate(result => {
        if (result) {
          // treatment the `status` specially, whether disabled ? [0]No [1]Yes
          this.formData.status = this.formData.status ? 1 : 0

          const isAdd = this.dialogType === 'new'
          if (isAdd) {
            this.savePermission()
          } else {
            this.editPermission()
          }
        }
      })
    },

    savePermission() {
      this.$api.system.savePermission(this.formData).then(() => {
        this.dialogVisible = false
        this.$message.success('保存成功')
        this.fetchPermissions()
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    editPermission() {
      this.$api.system.updatePermission(this.formData).then(() => {
        this.dialogVisible = false
        this.$message.success('更新成功')
        this.fetchPermissions()
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    resetDialogForm() {
      this.$nextTick(() => {
        this.$refs['permissionForm'].resetFields()
      })
    }

  }
}
</script>
