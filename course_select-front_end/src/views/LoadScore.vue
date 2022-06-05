<template>
  <!--  教师登分-->
  <div class="home" style="padding: 10px;">
    <!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" style="float: left">新增</el-button>
      <!--      <el-button type="primary" size="small">导入</el-button>-->
      <!--      <el-button type="primary" size="small">导出</el-button>-->
      <!--    搜索区-->
      <div style="float: right;">
        <el-input v-model="search" placeholder="请输入姓名" style="width: 75%" clearable/>
        <el-button type="primary" style="margin-left: 1.1%" @click="load">查询</el-button>
      </div>
    </div>
    <!--    内容-->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="snum" label="学号" sortable/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="usualGrade" label="平时成绩"/>
      <el-table-column prop="finalGrade" label="考试成绩"/>
      <el-table-column fixed="right" label="Operations" width="140">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <!--          <el-popconfirm title="确认删除吗?" @confirm="handleDelete(scope.row.snum)">-->
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

      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"
      >
        <el-form :model="form" label-width="120px">
          <el-form-item label="学号">
            <el-input v-model="form.snum" style="width: 80%" disabled/>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" style="width: 80%" disabled/>
          </el-form-item>
          <el-form-item label="平时成绩">
            <el-input v-model="form.usualGrade" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="考试成绩">
            <el-input v-model="form.finalGrade" style="width: 80%"/>
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
  name: 'LoadScore',
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
      addItem: false
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/loadscore", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          cnum: sessionStorage.getItem("cnum"),
          tnum: sessionStorage.getItem("tnum"),
          semester: sessionStorage.getItem("curSemester"),
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
      this.addItem = true;
    },
    save() {
      this.form.tnum = sessionStorage.getItem("tnum");
      this.form.cnum = sessionStorage.getItem("cnum");
      this.form.semester = sessionStorage.getItem("curSemester")
      request.put("/loadscore", this.form).then(res => {
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

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleSizeChange() { //改变当前每页个数触发
      this.load();
    },
    handleCurrentChange() { //改变当前页码触发
      this.load();
    }
  }
}
</script>
