<template>
  <div class="home" style="padding: 10px;">
    <!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" style="float: left">新增</el-button>
      <!--      <el-button type="primary" size="small">导入</el-button>-->
      <!--      <el-button type="primary" size="small">导出</el-button>-->
      <!--    搜索区-->
      <div style="float: right;">
        <el-input v-model="search" placeholder="请输入姓名" style="width: 75%" clearable />
        <el-button type="primary" style="margin-left: 1.1%" @click="load">查询</el-button>
      </div>
    </div>
    <!--    内容-->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="tnum" label="工号" sortable/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="sex" label="性别"/>
      <el-table-column prop="title" label="职称"/>
      <el-table-column prop="college" label="学院"/>
      <el-table-column fixed="right" label="Operations" width="140">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
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

      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"
      >
        <el-form :model="form" label-width="120px">
          <el-form-item label="工号">
            <el-input v-model="form.tnum" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男" size="large"></el-radio>
            <el-radio v-model="form.sex" label="女" size="large"></el-radio>
          </el-form-item>
          <el-form-item label="学院">
            <el-input v-model="form.college" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="职称">
            <el-input v-model="form.title" style="width: 80%"/>
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
  name: 'Teacher',
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
      request.get("/teacher", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
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
      if (!this.addItem) { //更新
        request.put("/teacher", this.form).then(res => {
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
        request.post("/teacher", this.form).then(res => {
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
      this.dialogVisible=true;
    },
    handleDelete(tnum){
      console.log(tnum);
      request.delete("/teacher/" + tnum).then(res => {
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
    }
  }
}
</script>
