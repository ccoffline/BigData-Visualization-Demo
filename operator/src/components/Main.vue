<template>
  <el-container>
    <el-main>
      <router-view />
    </el-main>
    <el-footer>
      <el-menu
        :default-active="activedPage($route.path)"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-row>
          <el-col :span="6">
            <el-menu-item index="0">定制布局</el-menu-item>
          </el-col>
          <el-col :span="6">
            <el-menu-item index="1">布局管理</el-menu-item>
          </el-col>
          <el-col :span="6">
            <el-menu-item index="2">定制图表</el-menu-item>
          </el-col>
          <el-col :span="6">
            <el-menu-item index="3">图表管理</el-menu-item>
          </el-col>
        </el-row>
      </el-menu>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "Main",
  created() {
    this.handleSelect("0");
  },
  data() {
    return {
      paths: new Map([
        ["0", "LayoutSelector"],
        ["1", "LayoutManager"],
        [
          "2",
          "ChartSelector"
          // "ChartEditor"
        ],
        ["3", "ChartManager"]
      ])
    };
  },
  methods: {
    handleSelect(index) {
      this.$router.push({ name: this.paths.get(index) });
    },
    activedPage(path) {
      path = path.substring(path.lastIndexOf("/") + 1);
      switch (path) {
        case "LayoutSelector":
        case "LayoutEditor":
          return "0";
        case "LayoutManager":
          return "1";
        case "ChartSelector":
        case "StyleSelector":
        case "ChartEditor":
          return "2";
        case "ChartManager":
          return "3";
      }
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
