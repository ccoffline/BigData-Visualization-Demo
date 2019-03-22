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
        v-for="img in imgs"
        :key="img.name"
        @click="handleSelect(img)"
      >
        <img :src="img.src" />
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
      imgs: [
        {
          src: "/static/images/1.jpg",
          name: "layout12"
        },
        {
          src: "/static/images/2.jpg",
          name: "layout2"
        },
        {
          src: "/static/images/3.jpg",
          name: "layout3"
        }
      ],
      search: "",
      select: ""
    };
  },
  methods: {
    handleSelect(name) {
      this.select = name;
      this.$router.push({
        name: "LayoutEditor",
        params: { layout: this.select }
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
