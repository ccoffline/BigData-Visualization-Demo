<template>
  <div class="full-screen">
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
    receiveMessage(e) {
      this.$router.push(e.data);
    }
  }
};
</script>

<style>
body {
  margin: 0;
  padding: 0;
  border: 0;
}
.full-screen {
  margin: 0px;
  width: 100%;
  height: 100%;
}
</style>
