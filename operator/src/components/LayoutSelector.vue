<template>
  <el-container>
    <el-header>
      <el-input
        placeholder="查询布局"
        v-model="search"
        prefix-icon="el-icon-search"
        clearable
      >
        <el-button slot="append">查询</el-button>
      </el-input>
    </el-header>
    <el-main>
      <div
        v-for="(img,index) in imgs"
        :key="index"
        @click="handleSelect(img)"
      >
        <img :src="$root.getLayoutSrc(img)" />
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "LayoutSelector",
  created() {},
  data() {
    return {
      imgs: ["layout9", "layout12"],
      search: "",
    };
  },
  methods: {
    handleSelect(name) {
      this.$router.push({
        name: "LayoutEditor",
        params: { layout: name }
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
img {
  width: 100%;
}
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
