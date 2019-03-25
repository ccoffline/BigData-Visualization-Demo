<template>
  <div>
    <div
      v-for="(chart,index) in charts"
      :key="index"
    >
      <div
        :id="'chart'+index"
        @click="setPos(index)"
      />
      <transition name="el-fade-in-linear">
        <el-row
          class="inputs"
          :gutter="10"
          v-if="input_pos==index"
        >
          <el-col :span="16">
            <el-input
              placeholder="设置位置"
              v-model="chart.position"
              clearable
            >
            </el-input>
          </el-col>
          <el-col :span="8">
            <el-button
              type="success"
              @click="submit(index)"
            >
              提交
            </el-button>
          </el-col>
        </el-row>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChartManager",
  created() {
    this.$root.get("/chart/", {}, response => {
      this.setCharts(response.data[0], response.data[1]);
    });
  },
  data() {
    return {
      input_pos: null,
      charts: []
    };
  },
  methods: {
    setPos(index) {
      if (this.input_pos == index) this.input_pos = null;
      else this.input_pos = index;
    },
    setCharts(charts, positions) {
      for (const json of charts)
        this.charts.push({
          chart: JSON.parse(json),
          position: null
        });
      for (const index in positions)
        this.charts[index].position = positions[index];
      this.$nextTick(function() {
        for (var i = 0; i < this.charts.length; ++i) {
          this.$chart.draw("chart" + i, this.charts[i].chart);
        }
      });
    },
    submit(index) {
      console.log(index)
      this.$root.postForm(
        "/chart/set",
        { index: index, position: this.charts[index].position },
        response => {
          if (response.data == "设置成功")
            this.$message.success(response.data);
          else this.$message.error(response.data);
        }
      );
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-select,
.el-button,
.el-input {
  width: 100%;
  margin-bottom: 10px;
}
.inputs {
  padding: 10px;
}
.el-row {
  margin: 10px;
  text-align: left;
  color: #adadad;
}
</style>
