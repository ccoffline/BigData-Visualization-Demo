<template>
  <div>
    <div id="chart"></div>
    <div class="inputs">
      <el-collapse>
        <el-collapse-item title="标题栏">
          <el-autocomplete
            placeholder="主标题"
            v-model="chart.title.text"
            :fetch-suggestions="titleTip"
            clearable
          />
          <el-input
            placeholder="副标题"
            v-model="chart.title.subtext"
            clearable
          />
          <el-row :gutter="10">
            <el-col :span="12">
              <el-autocomplete
                placeholder="水平位置"
                v-model="chart.title.x"
                :fetch-suggestions="xTip"
                clearable
              />
            </el-col>
            <el-col :span="12">
              <el-autocomplete
                placeholder="垂直位置"
                v-model="chart.title.y"
                :fetch-suggestions="yTip"
                clearable
              />
            </el-col>
          </el-row>
        </el-collapse-item>
        <el-collapse-item title="绘图样式">
          <el-row :gutter="10">
            <el-col :span="12">
              <el-input
                placeholder="左边界"
                v-model="chart.grid.x"
                clearable
              />
            </el-col>
            <el-col :span="12">
              <el-input
                placeholder="右边界"
                v-model="chart.grid.x2"
                clearable
              />
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-input
                placeholder="上边界"
                v-model="chart.grid.y"
                clearable
              />
            </el-col>
            <el-col :span="12">
              <el-input
                placeholder="下边界"
                v-model="chart.grid.y2"
                clearable
              />
            </el-col>
          </el-row>
        </el-collapse-item>
        <el-collapse-item title="数据渲染格式">
          <el-select
            v-model="categoryAxis"
            placeholder="选择类目轴"
          >
            <el-option
              label="横坐标轴"
              value="horizontal"
            />
            <el-option
              label="纵坐标轴"
              value="vertical"
            />
          </el-select>
          <el-row :gutter="10">
            <el-col :span="8">
              <el-select
                v-model="format.position"
                placeholder="单位位置"
              >
                <el-option
                  label="前缀"
                  value="prefix"
                ></el-option>
                <el-option
                  label="后缀"
                  value="suffix"
                ></el-option>
              </el-select>
            </el-col>
            <el-col :span="16">
              <el-input
                placeholder="单位格式"
                v-model="format.text"
              >
              </el-input>
            </el-col>
          </el-row>
        </el-collapse-item>
        <el-collapse-item title="图例样式">
          <el-select
            v-model="chart.legend.orient"
            placeholder="排列方式"
          >
            <el-option
              label="水平排列"
              value="horizontal"
            />
            <el-option
              label="垂直排列"
              value="vertical"
            />
          </el-select>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-autocomplete
                placeholder="水平位置"
                v-model="chart.legend.x"
                :fetch-suggestions="xTip"
                clearable
              />
            </el-col>
            <el-col :span="12">
              <el-autocomplete
                placeholder="垂直位置"
                v-model="chart.legend.y"
                :fetch-suggestions="yTip"
                clearable
              />
            </el-col>
          </el-row>
        </el-collapse-item>
      </el-collapse>
      <el-row style="margin-top:10px">
        <el-col :span="8">
          <el-button
            type="warning"
            @click="clear"
          >
            重置
          </el-button>
        </el-col>
        <el-col
          :span="8"
          :offset="8"
        >
          <el-button
            type="success"
            @click="submit"
          >
            提交
          </el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChartEditor",
  created() {
    var type = this.$route.params.type;
    var name = this.$route.params.name;
    this.chart.title.text = name;
    this.$root.getChart("/data", { name: name }, response => {
      for (const column of response.data.columns) {
        if (column.type == "category") this.category.data = column.data;
        else {
          column.type = type;
          this.chart.series.push(column);
          this.chart.legend.data.push(column.name);
        }
      }
    });
    this.chart.xAxis = this.category;
    this.chart.yAxis = this.value;
  },
  mounted() {
    this.$chart.draw("chart", this.chart);
  },
  data() {
    return {
      chart: {
        title: {
          text: "",
          subtext: "",
          x: "",
          y: ""
        },
        legend: {
          orient: "horizontal",
          x: "center",
          y: "30",
          data: []
        },
        xAxis: null,
        yAxis: null,
        grid: {
          x: "0%",
          x2: "5%",
          y: "60",
          y2: "5",
          containLabel: true
        },
        series: []
      },
      format: { text: "", position: null },
      category: {
        type: "category",
        boundaryGap: false,
        data: []
      },
      value: {
        type: "value",
        axisLabel: {
          formatter: "{value}"
        }
      },
      categoryAxis: ""
    };
  },
  methods: {
    clear() {},
    submit() {
      this.$root.postJson("/chart/submit", this.chart, response => {
        this.$message.success("提交成功");
        this.$router.push({ name: "ChartManager" });
      });
    },
    xTip(str, cb) {
      cb(
        str ? [] : [{ value: "left" }, { value: "center" }, { value: "right" }]
      );
    },
    yTip(str, cb) {
      cb(
        str ? [] : [{ value: "top" }, { value: "center" }, { value: "bottom" }]
      );
    },
    titleTip(str, cb) {
      cb(str ? [] : []);
    }
  },
  watch: {
    chart: {
      handler: function(val, oldval) {
        this.$chart.draw("chart", this.chart);
      },
      deep: true
    },
    categoryAxis(value) {
      if (this.categoryAxis == "horizontal") {
        this.chart.xAxis = this.category;
        this.chart.yAxis = this.value;
      } else {
        this.chart.yAxis = this.category;
        this.chart.xAxis = this.value;
      }
    },
    format(value) {
      var formatter = "{value}";
      if (this.format.position == "prefix")
        formatter = this.format.text + formatter;
      else formatter = formatter + this.format.text;
      value.axisLabel.formatter = formatter;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-autocomplete,
.el-select,
.el-button,
.el-input {
  width: 100%;
  margin-bottom: 10px;
}
.inputs {
  padding: 10px;
}
</style>
