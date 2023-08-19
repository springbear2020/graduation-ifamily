<template>
  <div class="components-container">
    <el-drag-select v-model="value" style="width:500px;" multiple placeholder="请选择">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
    </el-drag-select>

    <div style="margin-top:30px;">
      <el-tag v-for="item of value" :key="item" style="margin-right:15px;">
        {{ item }}
      </el-tag>
    </div>
  </div>
</template>

<script>
import ElDragSelect from './DragSelect' // base on element-ui

export default {
  name: 'DragSelectDemo',
  components: {ElDragSelect},
  props: ['adminId'],
  data() {
    return {
      roles: [],
      value: [],
    }
  },
  created() {
    this.fetchRoles()
    this.fetchAdminRoles()
  },
  computed: {
    options() {
      let arr = []
      this.roles.forEach(item => arr.push({value: item.role.name, label: item.role.title}))
      return arr
    }
  },
  watch: {
    value(newVal, oldVal) {
      this.updateAdminRoles(newVal, oldVal)
    }
  },
  methods: {
    fetchRoles() {
      this.$api.system.listAllRoles().then(roles => {
        this.roles = roles
      }).catch(msg => {
        this.$notify.error(msg)
      })
    },
    fetchAdminRoles() {
      this.$api.system.listRolesOfAdmin(this.adminId)
          .then(list => this.value = list)
          .catch(() => {
          })
    },
    updateAdminRoles(newRoles, oldRoles) {
      const diff = [...newRoles.filter(x => !oldRoles.includes(x)), ...oldRoles.filter(x => !newRoles.includes(x))];
      const target = this.roles.find(item => item.role.name === diff[0]);
      if (newRoles.length < oldRoles.length) {
        // remove the current role of admin
        this.$api.system.removeAdminRole(this.adminId, target.role.id)
            .then(() => this.$message.success(`移除 ${target.role.name} 角色成功`))
            .catch(msg => this.$notify.error(msg))
      } else {
        // add a new role for the admin
        this.$api.system.addAdminRole(this.adminId, target.role.id)
            .then(() => this.$message.success(`添加 ${target.role.name} 角色成功`))
            .catch(msg => this.$notify.error(msg))
      }
    }
  }
}
</script>
