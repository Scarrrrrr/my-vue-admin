import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false

var axios = require('axios')
const baseURL = "http://localhost:8081"

// cookies相关
const $cookies = require('vue-cookies')
Vue.use($cookies)
// 后台地址
// axios.defaults.baseURL = 'http://121.36.26.71:8989/'
axios.defaults.baseURL = baseURL
// 允许跨域携带数据
axios.defaults.withCredentials = true
// 全局注册，这样在其他组件中就能通过 this.$axios 来调用http服务
Vue.prototype.$axios = axios

Vue.config.productionTip = false
Vue.config.devtools = true

Vue.use(Element)

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')


