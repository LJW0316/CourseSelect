<template>
  <div class="login">
    <div style="width: 400px; margin: 150px auto">
      <div style="color: #8c7777; font-size: 30px; text-align: center; padding: 30px 0">
        欢迎登录
      </div>
      <el-form ref="loginForm" :model="form" :rules="rules" @submit.native.prevent>
        <el-form-item prop="username" style="padding: 10px 0">
          <el-input :prefix-icon="UserFilled" v-model="form.username"/>
        </el-form-item>
        <el-form-item prop="password" style="padding: 10px 0">
          <el-input :prefix-icon="Lock" v-model="form.password" show-password/>
        </el-form-item>
        <el-form-item style="padding-top: 5px">
          <el-button style="width: 100%" type="primary" @click="login" native-type="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {UserFilled, Lock} from '@element-plus/icons-vue'
import request from "../../utils/request";
import router from "@/router";

export default {
  setup() {
    return {
      UserFilled,
      Lock
    }
  },
  name: "Login",
  data() {
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          request.post("user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))
              if (res.data.role === "student") {
                router.push("/select")
              } else if (res.data.role === "teacher") {
                router.push("/score")
              } else {
                router.push("/student")
              }
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })

    }
  }
}
</script>

<style scoped>
.login {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  background-image: linear-gradient(to right, #74ebd5 0%, #9face6 100%);
}
</style>