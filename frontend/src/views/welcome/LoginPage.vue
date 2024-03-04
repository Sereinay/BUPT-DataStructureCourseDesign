<script setup>
import {Lock, User} from '@element-plus/icons-vue'
import {reactive} from "vue";

const form = reactive({
  username: '',
  password: '',
  remember: false
})
</script>

<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 150px">
      <div style="font-size: 25px;font-weight: bold">登录</div>
      <div style="font-size: 14px;color: rgb(128,128,128);margin-top: 5px">请输入您的用户名和密码进行登录</div>
    </div>
    <div style="margin-top: 50px">
      <el-form v-model="form">
        <el-form-item>
          <el-input v-model="form.username" maxlength="12" type="text" placeholder="用户名/邮箱">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" maxlength="20" placeholder="密码">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12" style="text-align: left">
            <el-form-item>
              <el-checkbox v-model="form.remember" label="记住我"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="text-align: right">
            <el-link>忘记密码？</el-link>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-top: 20px">
      <el-button style="width: 270px" type="primary" plain>立即登录</el-button>
    </div>
    <el-divider>
      <span style="background-color:aliceblue ;color: grey" ></span>
    </el-divider>
    <div>
      <el-button style="width: 270px" type="info" plain>立即注册</el-button>
    </div>
  </div>
</template>

<style scoped>

</style>