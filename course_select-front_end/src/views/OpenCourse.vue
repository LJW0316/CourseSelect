<template>
  <div class="home" style="padding: 10px;">
    <!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" style="float: left">开课</el-button>
      <!--      <el-button type="primary" size="small">导入</el-button>-->
      <!--      <el-button type="primary" size="small">导出</el-button>-->
      <!--    搜索区-->
      <div style="float: right;">
        <el-input v-model="search" placeholder="请输入课程名" style="width: 75%" clearable/>
        <el-button type="primary" style="margin-left: 1.1%" @click="load">查询</el-button>
      </div>
    </div>
    <div style="display: flex; width: 100%; margin-top: 50px;">
      <p style="margin: 6px">当前学期: {{ semester }}</p>
    </div>

    <!--    内容-->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="cnum" label="课程号" sortable/>
      <el-table-column prop="cname" label="课程名" sortable/>
      <el-table-column prop="semester" label="学期" sortable/>
      <el-table-column prop="credit" label="学分" sortable/>
      <el-table-column prop="college" label="学院" sortable/>
<!--      <el-table-column fixed="right" label="Operations" width="140">-->
<!--        <template #default="scope">-->
<!--          <el-popconfirm title="确认删除吗?" @confirm="handleDelete(scope.row)">-->
<!--            <template #reference>-->
<!--              <el-button type="danger" size="small">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
<!--        </template>-->
<!--      </el-table-column>-->
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

      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"
      >
        <el-form :model="form" label-width="120px">
          <el-form-item label="课程号">
            <el-input v-model="form.cnum" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="开课地点">
            <el-input v-model="form.place" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="开课时间">
            <el-input v-model="form.openTime" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="最大容量">
            <el-input v-model="form.maxNum" style="width: 80%"/>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: 'OpenCourse',
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
      semesterList: [],
      addItem: false, //新增表示，true表示新增，false表示更新
      semester: "",
      tnum: "",
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.tnum = JSON.parse(sessionStorage.getItem("user")).username;
      this.semester = sessionStorage.getItem('nextSemester');
      request.get("/opencourse", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          tnum: this.tnum,
          semester: this.semester
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
      this.addItem = true
    },
    save() {
      //新增
      this.form.tnum = this.tnum;
      this.form.semester = this.semester;
      this.form.curNum = 0;
      request.post("/opencourse", this.form).then(res => {
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
        this.addItem = false; //新增标识复位
      })

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleDelete(item) {
      console.log(item);
      request.delete("/course/", {data: item}).then(res => {
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
  }
}
</script>
