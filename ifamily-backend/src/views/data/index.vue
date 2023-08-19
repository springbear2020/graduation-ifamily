<template>
  <div class="app-container">
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item v-for="(item, index) in tableData" :name="index" :key="index.id">
        <template slot="title">
          <div style="width: 100%">
            <span>{{ item.name }}：{{ item.description }}</span>
            <div class="backup-btn-wrapper">
              <el-button type="success" size="mini" icon="el-icon-upload" @click.stop="sqlBackup(item.id)">
                备份 SQL 脚本
              </el-button>
            </div>
          </div>
        </template>

        <el-table :data="item.tables">
          <el-table-column label="#" type="index" width="50" align="center"/>
          <el-table-column label="表名称" prop="name"/>
          <el-table-column label="表描述" prop="description"/>

          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" icon="el-icon-download" @click="downloadExcel(scope.row.id)">
                导出 Excel
              </el-button>
              <el-button size="mini" type="warning" icon="el-icon-refresh" disabled>初始化</el-button>
              <el-button size="mini" type="danger" icon="el-icon-document-delete" disabled>清空表</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import {BASE_REQUEST_URL} from "@/utils/request";

export default {
  name: "DataManagement",
  data() {
    return {
      activeName: '0',
      tableData: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.$api.admin.dbTables()
          .then(list => this.tableData = list)
          .catch(msg => this.$notify.error(msg))
    },
    sqlBackup(dbId) {
      window.location.href = `${BASE_REQUEST_URL}/backend/admin/backup?id=${dbId}`
    },
    downloadExcel(tableId) {
      this.$message.info(tableId + '')
    }
  }
}
</script>

<style lang="scss">
.app-container {
  .backup-btn-wrapper {
    float: right;
    margin-right: 20px;
  }
}
</style>
