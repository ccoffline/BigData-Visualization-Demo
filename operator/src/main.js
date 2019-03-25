// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
Vue.use(ElementUI);

import axios from "axios";

import chart from "../static/js/chart_config";
Vue.use(chart);

import qs from "qs";

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>",
  data() {
    return {
      SERVER_URL: "/api/operator"
    };
  },
  methods: {
    getLayoutSrc(name) {
      return "../static/images/" + name + ".png";
    },
    get(url, params, success) {
      axios
        .get(this.SERVER_URL + url, { params: params })
        .then(success)
        .catch(error => {
          this.$message.error("网络错误");
          console.log(error);
        });
    },
    postJson(url, params, success) {
      axios
        .post(this.SERVER_URL + url, params)
        .then(success)
        .catch(error => {
          this.$message.error("网络错误");
          console.log(error);
        });
    },
    postForm(url, params, success) {
      axios
        .post(this.SERVER_URL + url, qs.stringify(params))
        .then(success)
        .catch(error => {
          this.$message.error("网络错误");
          console.log(error);
        });
    }
  }
});
