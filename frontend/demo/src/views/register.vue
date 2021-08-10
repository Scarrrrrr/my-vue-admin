<!--  -->
<template>
    <div class="login-page">
        <div class="page-center">
            <div class="info">
                <div class="title">
                    <img src="@/assets/logo-blue.png">
                    <span>Activity Center</span>
                </div>
            </div>  
            <el-form class="login-container" ref="registerForm" label-width="100px" :model="form" status-icon :rules="rules">
                <div class="title">
                    <h3>注册账户</h3>
                </div>
                <el-form-item label="账号" prop="name">
                    <el-input
                    v-model="form.name"
                    placeholder="用户名或邮箱"
                    prefix-icon="el-icon-user-solid"
                    clearable
                    autosize
                    />
                </el-form-item>
                <el-form-item label="密码" prop="password1">
                    <el-input
                    v-model="form.password1"
                    placeholder="密码"
                    show-password
                    prefix-icon="el-icon-s-goods"
                    />
                </el-form-item>
                <el-form-item label="确认密码" prop="password2">
                    <el-input
                    v-model="form.password2"
                    placeholder="确认密码"
                    show-password
                    prefix-icon="el-icon-s-goods"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button style="float: left;" type="primary" @click="register">注册</el-button>
                    <el-button style="float: right;" @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
/* eslint-disable */

export default {
  //import引入的组件需要注入到对象中才能使用
  name: "register",
  components: {},
  data() {
    //这里存放数据
    var validatename = (rule, value, callback) => {
      if (!value)
        callback(new Error('用户名不能为空'))
      if (value.length < 5)
        callback(new Error('用户名不能少于5位'))
      callback();
      
    };
    var checkPassword = (rule, value, callback) => {
      if (!value)
        callback(new Error('请确认密码'))
      if (value !== this.form.password1)
        callback(new Error('两次密码不一致'))
      callback()
    };
    return {
      form: {
        name: "",
        password1: "",
        password2: ""
      },
      rules:{
          name: [{validator: validatename, trigger: 'blur'}],
          password1: [{required: true, message: "请输入密码", trigger: "blur"},
          {min: 5, max: 16, message: '长度在5-16个字符', trigger: 'blur'}],
          password2: [{validator: checkPassword, trigger: "blur"}]
      }
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    register() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            let registerParam ={
              password:this.form.password1,
              role:"",
              title:"",
              name:this.form.name
            }
            this.$axios.post('/user/save', registerParam)
            .then(resp => {
              if (resp.data.status == 0){
                this.$alert('现在将为您跳转到登录界面', '注册成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.replace({path: '/'})
                  }
                });
              }
            })
            .catch(err=>this.$message({message:err,type:"warning"}))
          } else {
            this.$message({message:'注册失败，请重新输入',type:"error"})
          }
        })
    
    },
    reset() {
        this.$refs.registerForm.resetFields()
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {
    
  }, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style lang="less" scoped>
.align-left{
  text-align: left;
}
.register{
  text-align: right;
}
.login-page {
  width: 100%;
  height: 100%;
  background-image: url(../assets/login-back.svg);
  background-repeat: no-repeat;
  background-position: center 110px;
  background-size: 100%;
  position:relative;
  .page-center {
    position: absolute;
    width: 400px;
    height: 540px;
    margin: auto;
    top: 50%;
    transform: translateY(-50%);
    right: 0;
    left: 0;
    .info {
      text-align: center;
      padding-bottom: .5em;
      .title {
        display: flex;
        justify-content: center;
        align-items: Center;
        font-size: 32px;
        color: rgba(0, 0, 0, 0.85);
        font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica,
          sans-serif;
        font-weight: 600;
        img {
          padding-bottom: 3px;
        }
      }
      .detail {
        font-size: 14px;
        color: rgba(0, 0, 0, 0.45);
        margin-top: 12px;
        margin-bottom: 40px;
      }
    }

    .login-container {
      -webkit-border-radius: 5px;
      border-radius: 5px;
      -moz-border-radius: 5px;
      background-clip: padding-box;
      padding: 35px 35px 15px 35px;
      background: #fff;
      border: 1px solid #eaeaea;
      box-shadow: 0 0 25px #cac6c6;
      .title {
        margin: 0px auto 40px auto;
        text-align: center;
        color: #505458;
      }
      .remember {
        margin: 0px 0px 35px 0px;
      }
    }
  }
}
</style>