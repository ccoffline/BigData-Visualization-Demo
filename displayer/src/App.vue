<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {};
  },
  created() {
    var websocket = new WebSocket(this.$root.websocketUrl("/layout"));
    websocket.onopen = () => {
      console.log("layout connect");
    };
    websocket.onclose = () => {
      console.log("layout disconnect");
    };
    websocket.onerror = this.networkError;
    websocket.onmessage = this.receiveMessage;
    this.$router.push("hello");
  },
  methods: {
    networkError() {
      this.$message.error("网络错误\n请刷新重试");
    },
    receiveMessage(message) {
      this.$router.push(message.data);
    }
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
html,
body,
#app {
  height: 100%;
  margin: 0px;
  border: hidden;
  overflow: hidden;
}
</style>
