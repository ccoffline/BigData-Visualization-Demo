import Vue from "vue";
import Router from "vue-router";
import hello from "@/components/hello";
import layout12 from "@/components/layout12";
import layout9 from "@/components/layout9";

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
      path: "/layout9",
      name: "layout9",
      component: layout9
    }
  ]
});
