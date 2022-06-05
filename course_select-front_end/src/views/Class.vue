<template>
  <div class="home" style="padding: 10px;">
    <!--    功能区-->
    <div style="margin: 10px 0">
<!--      <el-button type="primary" @click="add" style="float: left">新增</el-button>-->
      <!--      <el-button type="primary" size="small">导入</el-button>-->
      <!--      <el-button type="primary" size="small">导出</el-button>-->
      <!--    搜索区-->
      <div style="float: right;">
        <el-input v-model="search" placeholder="请输入姓名" style="width: 75%" clearable/>
        <el-button type="primary" style="margin-left: 1.1%" @click="load">查询</el-button>
      </div>
    </div>
    <div style="display: flex; width: 100%; margin-top: 50px;">
      <p style="margin: 6px">学期: </p>
      <el-select v-model="semester" placeholder="选择学期" @change="saveSemester">
        <el-option
            v-for="item in semesterList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </div>
    <!--    内容-->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="cnum" label="课号" sortable/>
      <el-table-column prop="cname" label="课程名"/>
      <el-table-column prop="openTime" label="开课时间"/>
      <el-table-column prop="place" label="上课地点"/>
      <el-table-column prop="curNum" label="当前人数"/>
      <el-table-column prop="maxNum" label="最大人数"/>
      <el-table-column fixed="right" label="Operations" width="140">
        <template #default="scope" v-if="curSemester === semester">
          <el-button size="small" @click="handleEdit(scope.row)">管理</el-button>
<!--          <el-popconfirm title="确认删除吗?" @confirm="handleDelete(scope.row.tnum)">-->
<!--            <template #reference>-->
<!--              <el-button type="danger" size="small">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
        </template>
      </el-table-column>
    </el-table>
    <!--    分页-->
    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          v-model:total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

    </div>
  </div>
</template>

<script>
import request from "../../utils/request";
import router from "@/router";

export default {
  name: 'Score',
  components: {},
  data() {
    return {
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 1,
      tableData: [],
      dialogVisible: false,
      form: {},
      addItem: false,
      semesterList: [],
      semester: "", //下拉框选择的学期
      curSemester: "", //可以登分的学期
      tnum: "",
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.tnum = JSON.parse(sessionStorage.getItem("user")).username;
      this.curSemester = sessionStorage.getItem("curSemester");
      request.get("/class", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          semester: this.semester,
          tnum: this.tnum,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
      request.get("/course/semester").then(res => {
        console.log(res);
        this.semesterList = [];
        for (let x of res.data) {
          this.semesterList.push(x);
        }
      })
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
      this.addItem = true;
    },
    save() {
      if (!this.addItem) { //更新
        request.put("/class", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load(); //刷新表格数据
          this.dialogVisible = false; //关闭弹窗
        })
      } else { //新增
        request.post("/class", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load(); //刷新表格数据
          this.dialogVisible = false; //关闭弹窗
          this.addItem = false;
        })
      }

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      router.push("/loadscore");
      sessionStorage.setItem("cnum", row.cnum);
      sessionStorage.setItem("tnum", this.tnum);
    },
    handleDelete(tnum) {
      console.log(tnum);
      request.delete("/class/", {
        params: {
          tnum: tnum,
          semester: this.semester
        }
      }).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  //重新加载表格数据
      })
    },
    handleSizeChange() { //改变当前每页个数触发
      this.load();
    },
    handleCurrentChange() { //改变当前页码触发
      this.load();
    },
    //修改学期
    saveSemester() {
      this.$message({
        type: "success",
        message: "学期设定成功！"
      })
      sessionStorage.setItem("semester", this.semester);
      this.load();
    },
  }
}
</script>
