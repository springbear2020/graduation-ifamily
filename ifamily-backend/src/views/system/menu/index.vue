<template>
  <div class="app-container">
    <el-button type="primary" icon="el-icon-plus" @click="dialogType = 'new'; dialogVisible = true">新增</el-button>

    <!-- data table -->
    <el-table :data="menuList" style="width: 100%; margin-top:20px;" border>
      <el-table-column align="center" label="#" width="50" type="index"/>
      <el-table-column label="菜单名称" width="220" prop="title" sortable autofocus/>
      <el-table-column label="菜单描述" prop="description" sortable/>
      <el-table-column label="路由路径" width="220" prop="path" sortable/>

      <el-table-column align="center" label="菜单状态" width="220" sortable>
        <template slot-scope="scope">
            <span>
              <el-tag type="danger" v-if="scope.row.status">已禁用</el-tag>
              <el-tag v-else>启用中</el-tag>
            </span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建时间" width="220" prop="created" sortable/>
      <el-table-column align="center" label="修改时间" width="220" prop="modified" sortable/>

      <el-table-column align="center" label="操作" width="220">
        <template slot-scope="scope">
          <el-button type="warning" size="small" @click="openEditDialog(scope)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteMenu(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- form dialog -->
    <el-dialog
        :visible.sync="dialogVisible"
        :title="dialogType === 'edit'? '编辑菜单' : '新增菜单'"
        @close="resetDialogForm"
        @opened="() => {this.$refs.routeTree.checkTreeNodes()}"
    >
      <el-form
          label-width="80px"
          label-position="left"
          ref="menuForm"
          :model="formData"
          :rules="formRules"
      >

        <el-form-item label="名称" required prop="title">
          <el-input v-model="formData.title" placeholder="菜单名称"/>
        </el-form-item>
        <el-form-item label="描述" required prop="description">
          <el-input
              v-model="formData.description"
              :autosize="{ minRows: 2, maxRows: 5}"
              type="textarea"
              placeholder="菜单描述"
          />
        </el-form-item>
        <el-form-item label="路径" required prop="path">
          <el-input v-model="formData.path" placeholder="路由路径"/>
        </el-form-item>
        <el-form-item label="禁用" required>
          <el-switch v-model="formData.status"></el-switch>
        </el-form-item>

        <div style="text-align: right;">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogConfirm">确认</el-button>
        </div>
      </el-form>

      <el-divider>权限控制路由表</el-divider>

      <route-tree :patterns="pathPatternList" ref="routeTree"/>

    </el-dialog>
  </div>
</template>

<script>
import {deepClone} from "@/utils"
import RouteTree from "@/views/system/menu/components/RouteTree"

export default {
  name: "SystemMenu",
  components: {RouteTree},
  data() {
    return {
      menuList: [],
      // dialog form
      dialogVisible: false,
      dialogType: 'new',
      formData: {
        id: undefined,
        path: undefined,
        title: undefined,
        description: undefined,
        status: false
      },
      formRules: {
        path: [{required: true, message: '路由路径值不能为空'}],
        title: [{required: true, message: '菜单名称值不能为空'}],
        description: [{required: true, message: '菜单描述值不能为空'}]
      }
    }
  },
  created() {
    this.fetchMenus()
  },
  computed: {
    pathPatternList() {
      return this.menuList.map(item => item.path) || []
    }
  },
  methods: {

    fetchMenus() {
      this.$api.system.listAllMenus().then(menus => {
        this.menuList = menus
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    dialogConfirm() {
      this.$refs["menuForm"].validate(result => {
        if (result) {
          // treatment the `status` specially, whether disabled ? [0]No [1]Yes
          this.formData.status = this.formData.status ? 1 : 0

          const isAdd = this.dialogType === 'new'
          if (isAdd) {
            this.saveMenu()
          } else {
            this.editMenu()
          }
        }
      })
    },

    saveMenu() {
      this.$api.system.saveMenu(this.formData).then(() => {
        this.dialogVisible = false
        this.$message.success('保存成功')
        this.fetchMenus()
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    deleteMenu({$index, row}) {
      this.$confirm(`您确定要删除【${row.title}】菜单资源吗？`, '删除提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.$api.system.deleteMenu(row.id).then(() => {
          this.menuList.splice($index, 1)
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

    editMenu() {
      this.$api.system.updateMenu(this.formData).then(() => {
        this.dialogVisible = false
        this.$message.success('更新成功')
        this.fetchMenus()
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },

    resetDialogForm() {
      this.$nextTick(() => {
        this.$refs['menuForm'].resetFields()
      })
    }

  }
}
</script>
