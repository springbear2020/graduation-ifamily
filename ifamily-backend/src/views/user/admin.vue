<template>
  <div class="app-container">
    <el-button type="primary" icon="el-icon-plus">新增</el-button>

    <el-table :data="tableData">
      <el-table-column label="#" type="index" width="50" align="center"/>
      <el-table-column label="ID" prop="id" align="center"/>
      <el-table-column label="用户名" prop="username"/>
      <el-table-column label="用户昵称" prop="nickname"/>
      <el-table-column label="上次登录" prop="lastLogin" align="center"
                       :formatter="(row, column, cellValue) => {return diffDate(cellValue)}"/>
      <el-table-column label="注册时间" prop="created" align="center"/>

      <el-table-column label="禁用状态">
        <template slot-scope="scope">
          <el-switch :value="scope.row.status === 1" @click.native="updateStatus(scope)"/>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="info"
              icon="el-icon-info"
              @click="handleView(scope.$index, scope.row)"
          />
          <el-button
              size="mini"
              type="warning"
              icon="el-icon-edit"
          />
          <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="handleDelete(scope.$index, scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        style="text-align: center; margin-top: 20px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageQuery.current"
        :page-sizes="[20, 40, 60, 80, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="2">
    </el-pagination>

    <!-- role resources drawer -->
    <el-drawer :visible.sync="drawerShow" title="管理员详情">
      <div style="padding: 0 20px; text-align: center">
        <pan-thumb :image="drawerData.adminDetails.avatar" style="margin-bottom: 20px"/>

        <el-descriptions direction="vertical" :column="3" border>
          <el-descriptions-item label="UID">{{ drawerData.adminDetails.id }}</el-descriptions-item>
          <el-descriptions-item label="用户名">{{ drawerData.adminDetails.username }}</el-descriptions-item>
          <el-descriptions-item label="用户状态">
            <el-tag size="small" v-if="drawerData.adminDetails.status === 0">启用中</el-tag>
            <el-tag size="small" type="danger" v-else>禁用中</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="用户昵称">{{ drawerData.adminDetails.nickname }}</el-descriptions-item>
          <el-descriptions-item label="个性签名" :span="2">{{ drawerData.adminDetails.signature }}</el-descriptions-item>
          <el-descriptions-item label="上次登录">{{ drawerData.adminDetails.lastLogin }}</el-descriptions-item>
          <el-descriptions-item label="上次修改">{{ drawerData.adminDetails.modified }}</el-descriptions-item>
          <el-descriptions-item label="注册时间">{{ drawerData.adminDetails.created }}</el-descriptions-item>
        </el-descriptions>

        <div style="text-align: left; padding-top: 20px; color: #72767b">
          管理员角色
        </div>
        <drag-select-demo :admin-id="drawerData.adminDetails.id"/>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {diffDate} from "@/utils";
import PanThumb from "@/views/mine/components/PanThumb"
import DragSelectDemo from "@/views/user/components/drag-selection";

export default {
  name: "AdminUser",
  components: {DragSelectDemo, PanThumb},
  data() {
    return {
      tableData: [],
      pageQuery: {
        current: 1,
        size: 20
      },
      // drawer
      drawerShow: false,
      drawerData: {
        adminDetails: {}
      }
    }
  },
  created() {
    this.fetchAdminPageData()
  },
  methods: {
    fetchAdminPageData() {
      this.$api.admin.adminPageData(this.pageQuery)
          .then(list => this.tableData = list)
          .catch(msg => this.$notify.error(msg))
    },
    handleDelete(index, row) {
      this.$confirm(`您确定要删除 ${row.username} 这个管理员账户吗？`, '删除提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.$api.admin.deleteAccount(row.id).then(() => {
          this.tableData.splice(index, 1)
          this.$message.success(`删除 ${row.username} 账户成功`)
        }).catch(msg => {
          this.$notify.error(msg)
        })
      }).catch(() => {
      })
    },
    handleSizeChange(size) {
      this.pageQuery.size = size
      this.fetchAdminPageData()
    },
    handleCurrentChange(pageNum) {
      this.pageQuery.current = pageNum
      this.fetchAdminPageData()
    },
    diffDate(val) {
      return diffDate(val)
    },
    updateStatus({row}) {
      const newStatus = row.status === 0 ? 1 : 0
      this.$api.admin.updateStatus(row.id, {newStatus})
          .then(() => {
            const msg = newStatus === 1 ? '禁用' : '启用'
            this.$message.success(`${msg} ${row.username} 管理员账户成功`)
            row.status = newStatus
          })
          .catch(msg => this.$notify.error(msg))
    },
    handleView(index, row) {
      this.drawerShow = true
      this.drawerData.adminDetails = row
    }
  },
}
</script>
