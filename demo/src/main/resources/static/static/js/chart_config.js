import echarts from "echarts";
import "./theme/macarons";
import "./theme/shine";
import "./theme/dark";
const install = function(Vue) {
  Object.defineProperties(Vue.prototype, {
    $chart: {
      get() {
        return {
          draw: function(id, option, theme) {
            this.chart = echarts.init(document.getElementById(id), theme);
            this.chart.clear();
            this.chart.setOption(option);
          },
          clear: function(id) {
            echarts.init(document.getElementById(id)).clear();
          }
        };
      }
    }
  });
};

export default { install };
