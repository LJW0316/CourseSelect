<template>
  <div style="padding:10px ">
      <el-select v-model="this.semester" placeholder="请选择学期" style="background-color: mediumslateblue">
        <el-option
            v-for="item in orgOptions"
            :key="item.semester"
            :label="item.semester"
            :value="item.semester"
            @click="dealWithSemester"
        />
      </el-select>
    <el-divider>
      <el-icon>---</el-icon>
    </el-divider>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="cname" label="课程名" width="300" />
      <el-table-column prop="usualGrade" label="平时成绩" width="300" />
      <el-table-column prop="finalGrade" label="考试成绩" width="300" />
      <el-table-column prop="gpa" label="绩点" />
    </el-table>
    <el-card class="box-card" style="margin: 20px auto" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>成绩报告</span>
        </div>
      </template>
      <div class="text item">总学分： {{this.credit}}</div>
      <div class="text item">平均绩点： {{this.grade}}</div>
      <div class="text item">成绩排名： {{this.rank}}</div>
    </el-card>

  </div>
</template>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 480px;
}
</style>

<script >


import request from "../../utils/request";

export default {
  name:"searchGrade",
  components:{},
  data(){
    return {
      form3:{
        snum:undefined,
        semester:undefined,
      },
      form:{},
      dialogVisible:false,
      dialogVisible2:false,
      search:'',
      currentPage:1,
      pageSize:10,
      total:0,
      tableData:[],
      credit:undefined,
      currentid:JSON.parse(sessionStorage.getItem('user')).username,
      semester:'',
      grade:undefined,
      rank:undefined,
      orgOptions:[]
    }
  },
  created() {
    this.load()
  },

  methods:{
    load(){
      this.getOrg()
    },
    getOrg(){
      request.get("student_course/getSemester").then(res=>{
        this.orgOptions= res
      })
    },
    dealWithSemester(){
      this.getCourse()
    },
    getCourse(){
      this.form3.snum=this.currentid
      this.form3.semester=this.semester
      request.post("student_course/select_grade",this.form3
      ).then(res=>{
        this.tableData= res.data
      })
      request.post("student_course/select_rank",this.form3
      ).then(res=>{
        this.grade=res.data[0]
        this.credit=res.data[1]
        this.rank=res.data[2]
      })


    }
  }
}


</script>