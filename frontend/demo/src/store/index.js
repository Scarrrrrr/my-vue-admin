import Vue from 'vue'
import Vuex from 'vuex'
import { getItem, setItem, removeItem } from '@/utils/storageUtils'

Vue.use(Vuex)

export default new Vuex.Store({
  namespaced: true,
  state: {
    user: getItem('user'),
    remember: getItem('remember'),
    profile: null
  },
  mutations: {
    profile(state, param) {
      state.profile = param
    },
    login(state, param) {
      state.user = param.user
      state.remember = param.remember
      setItem('user', param.user)
      setItem('remember', param.remember)
    },
    logout(state) {
      state.user = null
      state.remember = false
      // 清除cookie
      window.$cookies.remove('user')
      window.$cookies.remove('token')
      removeItem('user')
      removeItem('remember')
    }
  },
  actions: {
  },
  modules: {

  },
  getters: {
    user: state => state.user,
    remember: state => state.remember,
    profile: state => state.profile
  }
})
