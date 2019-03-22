<template>
  <div id="layout8">
    <el-container>
      <el-header>
        <h1 style="margin: 0px;">{{this.$route.params.title}}</h1>
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
          <el-card :body-style="{ padding: '0px',height:layerheight}">
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
  name: 'layout8',
  data () {
    return {
      layerheight: '',
      websocket: null,
      charts:[],
    };
  },
  created () {
    this.setlayerheight();
    this.initWebpack();
  },

  mounted () {
    for (var i = 0; i < this.chartData.length; i++) {
      var tempid = 'chart' + i;
      this.$chart.line1(tempid, this.chartData[i]);
    }

  },
  methods: {
    // websocket
    initWebpack () {//初始化websocket
      const wsuri = "ws://172.20.10.8:8080/websocket";
      this.websocket = new WebSocket(wsuri);//这里面的this都指向vue
      this.websocket.onopen = this.websocketopen;
      this.websocket.onmessage = this.websocketonmessage;
      this.websocket.onclose = this.websocketclose;
      this.websocket.onerror = this.websocketerror;
    },
    websocketopen () {//打开
      console.log("WebSocket连接成功");
      this.websocket.send('get');
    },
    websocketonmessage (e) { //数据接收
      console.log(e);
      var data = JSON.parse(e.data); 
     // alert(e.data);
      switch (data.function) {
        case 'add': {
          var num = data.number;
          this.tempData.title.text = data.title;
          this.tempData.xAxis.data = data.data.x;
          this.tempData.series[0].data = data.data.y;
          this.tempData.series[0].type = data.type;
          this.setSingleData(num);
          return;
        }
        case 'delete': {
          var num = data.number;
          this.deleteSingleData(num);
          return;
        }
        case 'changeMain': {
          var num = data.number;
          this.changeMain(num);
          return;
        }
        case 'exchange': {
          var num1 = data.number1;
          var num2 = data.number2;
          this.exchange(num1, num2);
          return;
        }
        case 'edit': {
          if (data.title != '***') {
            this.editTitle(data.title);
          }
          if (data.color != '***') {
            this.editColor(data.color);
          }
          return;
        }
        case 'removeall': {
          this.clearAllData();
        }
        default: {
          return;
        }
      }
    },
    websocketclose () {  //关闭
      console.log("WebSocket关闭");
    },
    websocketerror () {  //失败
      console.log("WebSocket连接失败");
    },
    // websocket
    //替换主图
    changeMain (num) {
      this.chartData[0] = this.chartData[num];
      this.$chart.line1('chart0', this.chartData[0]);
      return;
    },
    //添加图
    setSingleData (num) {
      this.chartData[num] = this.tempData;
      var tempid = 'chart' + num;
      this.$chart.line1(tempid, this.chartData[num]);
      return;
    },
    //删除图
    deleteSingleData (num) {
      this.chartData[num] = {};
      this.$chart.line1(tempid, this.chartData[num]);
      return;
    },
    //清空大屏
    clearAllData () {
      for (var i = 0; i < this.chartData.length; i++) {
        var tempid = 'chart' + i;
        this.chartData[i] = {};
        this.$chart.line1(tempid, this.chartData[i]);
      }
      return;
    },
    //交换两个图的位位置
    exchange (num1, num2) {
      this.tempData = this.chartData[num1];
      this.chartData[num1] = this.chartData[num2];
      this.chartData[num2] = this.tempData;
      return;
    },
    setlayerheight () {
      this.layerheight = window.innerHeight - 102 + 'px';
    },
  }
}
</script>

<style>
.smallchart {
  width: 100%;
  height: 190px;
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
