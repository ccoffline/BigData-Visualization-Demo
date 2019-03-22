import Vue from "vue";
import Router from "vue-router";
import HelloWorld from "@/components/HelloWorld";
import Main from "@/components/Main";
import LayoutManager from "@/components/LayoutManager";
import ChartManager from "@/components/ChartManager";
import LayoutSelector from "@/components/LayoutSelector";
import LayoutEditor from "@/components/LayoutEditor";
import ChartSelector from "@/components/ChartSelector";
import ChartEditor from "@/components/ChartEditor";
import StyleSelector from "@/components/StyleSelector";
// import  from '@/components/'
// import  from '@/components/'
// import  from '@/components/'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "index",
      component: HelloWorld
    },
    {
      path: "/HelloWorld",
      name: "HelloWorld",
      component: HelloWorld
    },
    {
      path: "/Main",
      name: "Main",
      component: Main,
      children: [
        {
          path: "LayoutSelector",
          name: "LayoutSelector",
          component: LayoutSelector
        },
        {
          path: "LayoutEditor",
          name: "LayoutEditor",
          component: LayoutEditor
        },
        {
          path: "LayoutManager",
          name: "LayoutManager",
          component: LayoutManager
        },
        {
          path: "ChartSelector",
          name: "ChartSelector",
          component: ChartSelector
        },
        {
          path: "StyleSelector",
          name: "StyleSelector",
          component: StyleSelector
        },
        {
          path: "ChartEditor",
          name: "ChartEditor",
          component: ChartEditor
        },
        {
          path: "ChartManager",
          name: "ChartManager",
          component: ChartManager
        }
      ]
    }
  ]
});
