<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">选课系统</div>
    <div style="flex: 1"></div>
    <div style="width: 150px;">
      <el-dropdown>
    <span class="el-dropdown-link">
      <br>{{user.name}}
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item v-if="user.name === 'student'">用户信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">注销</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { ArrowDown } from '@element-plus/icons-vue'
export default {
  name: "Header",
  data() {
    return {
      user: {}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  },
  components: {
    ArrowDown
  }
}
</script>
<style scoped>

</style>