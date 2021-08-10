<!-- 左侧菜单栏 -->
<template>
    <div>
    <el-menu :collapse="false" router>
      <el-submenu index="user">
        <template slot="title">
          <span>首页</span>
        </template>
        <el-menu-item index="/home/display">活动展示</el-menu-item>
        <el-menu-item index="/home/search">搜索活动</el-menu-item>
      </el-submenu>
      <el-submenu v-if="isLogin" index="info">
        <template slot="title">
          <span>个人中心</span>
        </template>
      <el-menu-item index="/home/personal">个人信息</el-menu-item>
      <el-menu-item index="/home/attend">参与活动</el-menu-item>
      </el-submenu>
      <el-submenu v-if="admin" index="admin">
        <template slot="title">
          <span>管理权限</span>
        </template>
        <el-menu-item index="/home/activities">管理活动</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
  <!-- <div class="scroll-container" :style="{width:width}" ref="scrollContainer">
    
  </div> -->
</template>

<script>

export default {
  name: "sidebar",
  components: {  },
  data() {
    return {
      top: 0,
      width: "200px",
      isLogin:localStorage.getItem('id')?true:false,
      admin:false
    };
  },
  mounted(){
    if(localStorage.getItem('id')){
      this.admin = localStorage.getItem('role') == 'admin'? true:false
    }else{
      this.admin = false
    }
  },
  computed: {

  },
  methods: {}
};
</script>
<style lang="less" scoped>
.el-scrollbar {
  height: 100%;
  background-color: #fff;
}
</style>