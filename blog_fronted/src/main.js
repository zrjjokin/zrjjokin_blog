// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import elementUI from "element-ui"
import axios from "axios"
import qs from 'qs';
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import hljs from 'highlight.js'
import 'highlight.js/styles/solarized-dark.css' //样式文件
Vue.use(hljs);
//自定义语法高亮指令
Vue.directive('hljs', el => {
  let blocks = el.querySelectorAll('pre code');
  blocks.forEach((block)=>{
    hljs.highlightBlock(block)
  })
});

Vue.config.productionTip = false
Vue.use(elementUI)
axios.defaults.withCredentials=true;
Vue.prototype.$http = axios

Vue.prototype.$qs = qs;
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
