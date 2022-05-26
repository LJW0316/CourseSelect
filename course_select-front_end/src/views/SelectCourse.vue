<template>
  <div class="home" style="padding: 10px;">
    <el-button type="primary" style="margin-left: 5px;background-color: #3b3d3f" @click="choose">选课</el-button>
    <el-button type="primary" style="margin-left: 5px;background-color: red" @click="choose2">退课</el-button>

    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入课程名" style="width: 20%"/>
      <el-button type="primary" style="margin-left: 5px" @click="find">查询</el-button>
    </div>

    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column property="cnum" label="课程号"  sortable />
      <el-table-column property="cname" label="课程名" sortable />
      <el-table-column property="credit" label="学分" sortable />
      <el-table-column property="tnum" label="教师号" sortable />
      <el-table-column property="openTime" label="上课时间" sortable />
      <el-table-column property="place" label="上课地点" sortable />
      <el-table-column property="curNum" label="当前人数" sortable />
      <el-table-column property="maxNum" label="总人数" sortable show-overflow-tooltip />
      <el-table-column property="semester" label="学期"  />
    </el-table>

    <divider/>

    <div class="demo-pagination-block" style="margin: 10px 0">
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


    <el-dialog
        v-model="dialogVisible"
        title="选课"
        width="30%"
    >
      <el-form :model="form2" label-width="120px">
        <el-form-item label="课程号" style="width: 80%">
          <el-input v-model="form2.cnum" />
        </el-form-item>
        <el-form-item label="教师号" style="width: 80%">
          <el-input v-model="form2.tnum" />
        </el-form-item>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save" v-model="dialogVisible"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>

    <el-dialog
        v-model="dialogVisible2"
        title="退课"
        width="30%"
    >
      <el-form :model="form3" label-width="120px">
        <el-form-item label="课程号" style="width: 80%">
          <el-input v-model="form3.cnum" />
        </el-form-item>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="Delete" v-model="dialogVisible2"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script >

import request from "../../utils/request";


export default {
  name:"selectCourse",
  components:{},
  data(){
    return {
      form2:{
        id:undefined,
        snum:undefined,
        cnum:undefined,
        tnum:undefined,
        semester:undefined,
        usual_grade:0 ,
        final_grade:0,
        gpa:0
      },
      form3:{
        id:undefined,
        snum:undefined,
        cnum:undefined,
        tnum:undefined,
        semester:undefined,
        usual_grade:0 ,
        final_grade:0
      },
      form:{},
      dialogVisible:false,
      dialogVisible2:false,
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 100,

      tableData:[],
      currentid:JSON.parse(sessionStorage.getItem('user')).username,
      semester:sessionStorage.getItem("nextSemester"),
      coursename:undefined

    }
  },
  created() {
    this.load()
  },

  methods:{
    find(){
      console.log(this.search)
      request.get("student_course/search_course", {
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          cname:this.search,
          semester:this.semester
        }
      }).then(res=>{
        this.tableData= res.data.records
        this.total=res.data.total;
      })
    },
    load(){
      request.get("student_course/search_course", {
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          cname:this.search,
          semester:this.semester
        }
      }).then(res=>{
        console.log(this.currentid)
        this.tableData= res.data.records
        this.total=res.data.total;
      })
    },
    choose(){
      this.dialogVisible="true"
      this.form2={}
    },
    choose2(){
      this.dialogVisible2="true"
      this.form3={}
    },
    save(){
      this.form2.snum=this.currentid
      this.form2.semester=this.semester
      console.log(this.form2.semester)
      request.post("student_course/select_course", this.form2).then(res=>{
        console.log(res)
        alert(res.msg)
        this.load()
      })
      this.dialogVisible=false
    },
    Delete(){
      this.form3.snum=this.currentid
      this.form3.semester=this.semester

      request.post("student_course/retreat_course", this.form3).then(res=>{
        console.log(res)
        alert(res.msg)
        this.load()
      })
      this.dialogVisible2=false
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