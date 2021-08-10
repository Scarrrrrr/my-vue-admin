<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
    // Header:() => import ("@/components/Header"),
  },

}
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100%;
  height: 100%;
}
body {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  overflow: hidden;
}
.header {
    position: absolute;
    line-height: 50px;
    font-size: 20px;
    font-weight: bolder;
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    top: 0px;
    color:white;
    left: 0px;
    right: 0px;
    background-color: #2d3a4b;
    padding:20px 40px;
  }
  
  /* 左侧样式 */
  .navbar {
    position: absolute;
    width: 230px;
    top: 90px;  /* 距离上面50像素 */
    left: 0px;
    bottom: 0px;
    overflow-y: auto; /* 当内容过多时y轴出现滚动条 */
    background-color: #ebedf0;
  }
  
  /* 主区域 */
  .main {
    position: absolute;
    top: 90px;
    left: 230px;
    bottom: 0px;
    right: 0px;  /* 距离右边0像素 */
    padding: 10px;
    overflow-y: auto; /* 当内容过多时y轴出现滚动条 */
    /* background-color: red; */
  }
</style>
