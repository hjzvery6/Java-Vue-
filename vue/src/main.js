// 前端程序的入口文件
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'

// 这几个css样式都是为全局设置的
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/main.css'
import '@/assets/fonts/font-awesome.css'

Vue.config.productionTip = false
Vue.prototype.$bus = new Vue();
Vue.use(ElementUI, { size: "small"})

// 这是一个Vue实例
new Vue({
  router, //从router文件中导入路由实例
  render: h => h(App) //这是一个渲染函数，告诉vue如何渲染根组件app
}).$mount('#app') //将vue实例挂在到页面上的ID为app的元素上
