// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";

import elementui from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
Vue.use(elementui);

import chart from "./js/myCharts.js";
Vue.use(chart);

Vue.config.productionTip = false;

const SERVER_URL = "localhost:8080";

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>",
  methods: {
    websocketUrl(path) {
      return "ws://" + SERVER_URL + path;
    }
  }
});
