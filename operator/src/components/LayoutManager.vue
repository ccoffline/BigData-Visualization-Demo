<template>
  <div>
    <div
      v-for="layout in layouts"
      :key="layout.title"
    >
      <h1>{{layout.title}}</h1>
      <img
        :src="layout.src"
        @click="handleSelect(layout)"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "LayoutManager",
  created() {
    this.layouts.push(this.$route.params);
  },
  data() {
    return {
      layouts: []
    };
  },
  methods: {
    handleSelect(layout) {}
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
img {
  width: 100%;
}
/* .el-card,
.el-card__body {
  margin: 0px;
  padding: 0px;
}
.el-row {
  margin: 5px;
  text-align: left;
  color: #adadad;
} */
h1 {
  color: #adadad;
  text-align: left;
  margin: 10px 0;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}
</style>
