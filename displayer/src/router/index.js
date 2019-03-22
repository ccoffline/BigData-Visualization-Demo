import Vue from "vue";
import Router from "vue-router";
import hello from "@/components/hello";
import layout12 from "@/components/layout12";
import layout8 from "@/components/layout8";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/hello",
      name: "hello",
      component: hello
    },
    {
      path: "/layout12",
      name: "layout12",
      component: layout12
    },
    {
      path: "/layout8",
      name: "layout8",
      component: layout8
    }
  ]
});
