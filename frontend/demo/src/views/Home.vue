<template>
  <div>
      <div class="header">
        <el-col :span="16">
          活动管理系统
        </el-col>
        <div class="right-bar" v-if="isLogin" :xs="14" :sm="12" :md="10" :lg="8" :xl="6">
          <div class="system-user">
            你好，{{user.name}}
            <el-button @click="logout" style="font-size:20px" type="text">退出</el-button>
          </div>
        </div>
        <div class="right-bar" v-else :xs="14" :sm="12" :md="10" :lg="8" :xl="6">
          <el-button @click="dialogVisible=true" type="text">
            登录
          </el-button>
          <el-button @click="registerVisible=true" type="text">
            注册
          </el-button>
        </div>
      </div>
      <div class="navbar">
        <nav-menu></nav-menu>
      </div>
      <div class="main">
        <router-view/>
      </div>
      <el-dialog
        title="账号密码登录"
        :visible.sync="dialogVisible"
        width="30%">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="left" class="demo-ruleForm login-container">
          <el-form-item label="用户名" prop="name">
            <el-input type="text" v-model="ruleForm.name" auto-complete="off" placeholder="请输入用户名">
            </el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="请输入密码">
            </el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="login">登 录</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="账号注册"
        :visible.sync="registerVisible"
        width="30%">
        <el-form :model="form" :rules="rules" ref="ruleForm" label-position="left" class="demo-ruleForm login-container">
          <el-form-item label="用户名" prop="name">
            <el-input type="text" v-model="form.name" auto-complete="off" placeholder="请输入用户名">
            </el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="form.password" auto-complete="off" placeholder="请输入密码">
            </el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-input type="text" v-model="form.sex" auto-complete="off" placeholder="请输入性别">
            </el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" prop="email">
            <el-input type="text" v-model="form.email" auto-complete="off" placeholder="请输入电子邮箱">
            </el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="verifyCode">
            <el-input @click="getVerifyCode" type="text" v-model="form.verifycode" placeholder="请输入验证码"></el-input>
            <img @click="getVerifyCode" class="verify" :src="verifyImg" alt="更换验证码">
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="register">注 册</el-button>
          <el-button @click="registerVisible = false">取 消</el-button>
        </span>
      </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Home',
  components:{
    NavMenu:() => import ("@/components/NavMenu")
  },
    data(){
    return{
      isLogin:false,
      dialogVisible:false,
      registerVisible:false,
      verifyImg:"http://localhost:8081/auth/verifyCode",
      user:{
        name:"",
        password:""
      },
      ruleForm: {
        name: "",
        password: ""
      },
      form:{
        name:"",
        password:"",
        sex:"",
        email:"",
        verifycode:"",
      },
      rules: {
        name: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
        sex: [{ required: true, message: "请输入性别", trigger: "blur" }],
        verifyCode: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
    }
  },
  mounted(){
    if(localStorage.getItem('id')){
      this.user.name = localStorage.getItem('name')
      this.isLogin = true
    }
  },
  methods:{
    login(){
      this.$axios({
        url:"/auth/login",
        method:'post',
        data:this.ruleForm
      }).then(res=>{
        console.log(res)
        const user = res.data.user;
        this.user = user;
        if(res.status !== 200){
          this.$message({
            showClose: true,
            message: "用户名或密码错误",
            type: 'error'
          });
        }else{
          this.dialogVisible = false;
          localStorage.setItem("id",user.id)
          localStorage.setItem("name",user.name)
          localStorage.setItem("sex",user.sex)
          localStorage.setItem("role",user.role)
          localStorage.setItem("email",user.email)
          this.isLogin = true
          this.$router.go(0)
        }
      }).catch(() => (this.logining = false))
    },
    logout(){
      localStorage.removeItem('id')
      this.isLogin= false
      this.$router.push("/home/display")
      this.$router.go(0)
    },
    register(){
      this.$axios.post('/user/save', this.form)
        .then(resp => {
          if (resp.data.status == 0){
            this.$message({message:"注册成功", type:"success"})
            this.registerVisible = false
          }else{
            this.$message({message:resp.data.message,type:"error"})
          }
        }).catch(err=>{
          this.$message({message:err,type:"warning"})
          this.form.verifyCode = ""
          this.getVerifyCode()
        })
    },
    getVerifyCode(){
      this.verifyImg ="http://localhost:8081/auth/verifyCode?random="+Math.random();
    },
  }
}
</script>

<style scoped>
.right-bar{
  position: fixed;
  right:4em;
}
.verify{
  width:6em;
  height: 3em;
  cursor: pointer;
}
</style>