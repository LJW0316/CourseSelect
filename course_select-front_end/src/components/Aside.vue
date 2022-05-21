<template>
  <div style="width: 200px">
    <el-menu
        style="min-height: calc(100vh - 50px)"
        :default-active="$route.path.substr(1)"
        router
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
    >
      <el-sub-menu index="1" v-if="role === 'admin'">
        <template #title>
          <span>系统管理</span>
        </template>
        <el-menu-item index="student">
          <template #title>学生管理</template>
        </el-menu-item>
        <el-menu-item index="teacher">
          <template #title>教师管理</template>
        </el-menu-item>
        <el-menu-item index="college">
          <template #title>学院管理</template>
        </el-menu-item>
        <el-menu-item index="course">
          <template #title>课程管理</template>
        </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="2" v-if="role === 'student'">
        <template #title>
          <span>学生中心</span>
        </template>
        <el-menu-item index="selectCourse">
          <template #title >学生选课</template>
        </el-menu-item>
        <el-menu-item index="searchCourse">
          <template #title>课表查询</template>
        </el-menu-item>
        <el-menu-item index="searchGrade">
          <template #title>成绩查询</template>
        </el-menu-item>
        <el-menu-item index="Graph">
          <template #title>成绩统计</template>
        </el-menu-item>

      </el-sub-menu>

      <el-sub-menu index="3" v-if="role === 'teacher'">
        <template #title>
          <span>教师中心</span>
        </template>
        <el-menu-item index="score">
          <template #title>成绩管理</template>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
export default {
  name: "Aside",
  components: {
    Document,
    IconMenu,
    Location,
    Setting
  },
  data() {
    return {
      role: "",
    }
  },
  created() {
    let userJson = sessionStorage.getItem("user");
    userJson = JSON.parse(userJson);
    this.role = userJson.role;
    console.log(this.$route.path);
  }
}
</script>

<style scoped>

</style>