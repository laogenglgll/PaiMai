// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import Axios from 'axios'

Vue.prototype.$axios = Axios;
Vue.use(ElementUI)

Vue.config.productionTip = false ;

Axios.defaults.baseURL="http://localhost:8080/";

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
