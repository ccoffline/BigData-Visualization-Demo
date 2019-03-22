import echarts from "echarts";
import "./theme/macarons.js";
const install = function(Vue) {
  Object.defineProperties(Vue.prototype, {
    $chart: {
      get() {
        return {
          draw: function(id, option) {
            var chart = document.getElementById(id);
            var width = window.innerWidth - 20;
            chart.style.margin = "10px";
            chart.style.width = width + "px";
            chart.style.height = width * 0.5 + 60 + "px";
            this.chart = echarts.init(chart, "macarons");
            this.chart.clear();
            this.chart.setOption(option);
          }
        };
      }
    }
  });
};

export default {
  install
};
