<template>
  <div>
    <!-- data table -->
    <el-table ref="multipleTable" border :data="dataList">
      <el-table-column type="selection" width="55" :selectable="(row)=> {return !row.checked}"/>
      <el-table-column align="center" label="#" width="50" type="index"/>
      <el-table-column label="接口名称" prop="controller" sortable/>
      <el-table-column label="接口描述" prop="description" sortable/>
      <el-table-column
          label="请求方法"
          prop="method"
          sortable
          :formatter="(row, column, cell) => {return cell.toUpperCase()}"
      />
      <el-table-column label="请求路径" prop="path" sortable/>
      <el-table-column label="权限路径" prop="permission" sortable :formatter="permissionArrFormatter"/>
    </el-table>
  </div>
</template>

<script>
import {findMatched} from "@/utils/ant-matcher";

export default {
  name: "TabDataTable",
  props: {
    tabData: {
      type: Object,
      default: () => {
        return {}
      }
    },
    permissionPathList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  computed: {
    dataList() {
      return this.generateApiList()
    }
  },
  methods: {
    generateApiList() {
      let resultList = []

      Object.keys(this.tabData).forEach(methodPath => {
        // match whether the current method path associated with some permission pattern
        const patterns = findMatched(this.permissionPathList, methodPath)

        /**
         * notes important: please don't change the if cases to if-else,
         * because it is possible to have get, put, delete or put methods at the same time
         */
        if (this.tabData[methodPath].post) {
          let apiOperation = this.generateApiOperation(methodPath, 'post', patterns)
          resultList.push(apiOperation)
        }
        if (this.tabData[methodPath].delete) {
          let apiOperation = this.generateApiOperation(methodPath, 'delete', patterns)
          resultList.push(apiOperation)
        }
        if (this.tabData[methodPath].put) {
          let apiOperation = this.generateApiOperation(methodPath, 'put', patterns)
          resultList.push(apiOperation)
        }
        if (this.tabData[methodPath].get) {
          let apiOperation = this.generateApiOperation(methodPath, 'get', patterns)
          resultList.push(apiOperation)
        }
      })


      // set the check status of current row
      this.$nextTick(() => {
        resultList.forEach(item => {
          if (item.checked) {
            this.$refs.multipleTable.toggleRowSelection(item)
          }
        })
      })

      return resultList
    },

    permissionArrFormatter({permission}) {
      if (permission && permission.length > 0) {
        return `${permission.join('、')}`
      }
      return ''
    },

    generateApiOperation(path, method, patterns) {
      const methodOperation = this.tabData[path][method]
      return {
        path,
        method,
        controller: methodOperation.tags[0],
        description: methodOperation.summary,
        permission: patterns,
        checked: patterns && patterns.length > 0,
      }
    }

  }
}
</script>
