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
    <el-button plain @click="open1" style="margin-left: 10px"> 总分 </el-button>
    <el-divider>
      <el-icon>---</el-icon>
    </el-divider>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="cnum" label="课号" width="150" />
      <el-table-column prop="cname" label="课程名" width="150" />
      <el-table-column prop="credit" label="学分" width="150" />
      <el-table-column prop="name" label="教师姓名" width="150" />
      <el-table-column prop="college" label="学院" width="150" />
      <el-table-column prop="openTime" label="上课时间" width="150" />
      <el-table-column prop="place" label="上课地点"/>
    </el-table>

  </div>
</template>

<script >

import request from "../../utils/request";
import { ElNotification } from 'element-plus'

export default {
  name:"searchCourse",
  components:{},
  data(){
    return {
      form2:{
        snum:undefined,
        cnum:undefined,
        cname:undefined,
        credit:undefined,
        name:undefined,
        college:undefined,
        openTime:undefined,
        place:undefined,
        semester:undefined,
      },
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
      request.post("student_course/view_course",this.form3
      ).then(res=>{
        this.tableData= res.data
      })
    },
    open1(){
      this.form3.snum=this.currentid
      this.form3.semester=this.semester
      request.post("student_course/total_credit",this.form3
      ).then(res=>{
        this.credit=res.data
        ElNotification({
          title: 'Success',
          message: '您 '+this.semester+' 学期总学分为 '+this.credit+' 分',
          type: 'success',
        })
      })
    }

  }
}


</script>