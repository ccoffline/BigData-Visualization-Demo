import echarts from "echarts";
const install = function(Vue) {
  Object.defineProperties(Vue.prototype, {
    $chart: {
      get() {
        return {
          // 画一条简单的线
          draw: function(id, option) {
            this.chart = echarts.init(document.getElementById(id));
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

export default {
  install
};
