<template>
  <div id="layout8">
    <el-container>
      <el-header>
        <h1 style="margin: 0px;">{{title}}</h1>
      </el-header>
      <el-container>
        <el-aside width="300px">
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart1"
              ></div>
            </el-card>
          </el-row>
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart2"
              ></div>
            </el-card>
          </el-row>
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart3"
              ></div>
            </el-card>
          </el-row>
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart4"
              ></div>
            </el-card>
          </el-row>
        </el-aside>
        <el-aside width="300px">
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart5"
              ></div>
            </el-card>
          </el-row>
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart6"
              ></div>
            </el-card>
          </el-row>
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart7"
              ></div>
            </el-card>
          </el-row>
          <el-row class="row-aside">
            <el-card :body-style="{ padding: '0px' }">
              <div
                class="smallchart"
                id="chart8"
              ></div>
            </el-card>
          </el-row>
        </el-aside>
        <el-main style="overflow-x: hidden;overflow-y: hidden;">
          <el-card :body-style="{ padding: '0px', height: layerheight }">
            <div
              class="bigchart"
              id="chart0"
            ></div>
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "layout9",
  data() {
    return {
      layerheight: null,
      title: "",
      theme: ""
    };
  },
  created() {
    this.layerheight = window.innerHeight - 102 + "px";
    var websocket = new WebSocket(this.$root.websocketUrl("/chart"));
    websocket.onopen = () => {
      console.log("chart connect");
    };
    websocket.onclose = () => {
      console.log("chart disconnect");
    };
    websocket.onerror = this.networkError;
    websocket.onmessage = this.receiveMessage;
  },
  methods: {
    networkError() {
      this.$message.error("网络错误\n请刷新重试");
    },
    receiveMessage(e) {
      var message = JSON.parse(e.data);
      console.log(message);
      switch (message.cmd) {
        case "draw":
          this.$chart.draw(
            "chart" + message.position,
            JSON.parse(message.chart),
            this.theme
          );
          break;
        case "clear":
          this.$chart.clear("chart" + message.position);
          break;
        case "setTitle":
          this.title = message.title;
          break;
        case "setTheme":
          this.theme = message.theme;
          break;
      }
    }
  }
};
</script>

<style scoped>
.smallchart {
  width: 100%;
  height: 165px;
}
.bigchart {
  width: 100%;
  height: 100%;
}
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.el-row {
  margin: 5px;
}
.row-aside {
  height: 24%;
}
.row-main {
  height: 99%;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.el-header {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  /* height: 600px; */
  /* line-height: 100%; */
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  height: 100%;
}
body > .el-container {
  height: 100%;
}
.el-card {
  height: 100%;
}
</style>
