<template>
  <el-container>
    <el-header>
      <el-input
        placeholder="查询数据源 空查询即获取所有"
        v-model="keys"
        prefix-icon="el-icon-search"
        clearable
      >
        <el-button
          slot="append"
          @click="search"
        >查询</el-button>
      </el-input>
    </el-header>
    <el-main>
      <el-table
        :data="charts"
        style="width: 100%"
        @current-change="handleSelect"
      >
        <el-table-column
          prop="name"
          label="请选择一个数据源"
        >
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "ChartSelector",
  created() {},
  data() {
    return {
      charts: [],
      keys: ""
    };
  },
  methods: {
    handleSelect(source) {
      this.$router.push({
        name: "StyleSelector",
        params: { name: source.name }
      });
    },
    search() {
      this.$root.get("/chart/search", { keys: this.keys }, response => {
        this.charts = response.data;
      });
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  padding: 10px;
}
.el-input {
  width: 100%;
}
.el-button {
  padding-top: 15px;
}
</style>
