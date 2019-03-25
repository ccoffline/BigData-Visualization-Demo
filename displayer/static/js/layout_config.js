export default {
  name: "",
  data() {
    return {
      title: "",
      theme: ""
    };
  },
  created() {
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
      switch (message.cmd) {
        case "draw":
          this.$chart.draw(
            "chart" + message.position,
            JSON.parse(message.chart),
            message.theme
          );
          break;
        case "clear":
          this.$chart.clear("chart" + message.position);
          break;
        case "setTitle":
          this.title = message.title;
          break;
      }
    }
  }
};
