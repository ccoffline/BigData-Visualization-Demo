<template>
  <div class="hello">
    <el-container>
      <el-main>
        <img src="/static/images/logo.jpg">
        <h1>{{ name }}</h1>
        <h2>{{ title }}</h2>
      </el-main>
      <el-footer>
        <el-button
          type="primary"
          @click="jump"
        >
          {{ button }}
        </el-button>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  data() {
    return {
      name: "海姆达尔",
      title: "大数据可视化模板定制系统",
      button: "点击跳转"
    };
  },
  methods: {
    jump() {
      this.$router.push({ name: "Main" });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: normal;
  font-size: 50px;
  color: #0000e3;
}
h2 {
  font-weight: normal;
  color: #0066cc;
}
</style>
