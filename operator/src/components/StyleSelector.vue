<template>
  <div>
    <img
      v-for="type in types"
      :key="type.id"
      :src="getImageSrc(type.name)"
      @click="handleSelect(type.id)"
    />
  </div>
</template>

<script>
export default {
  name: "StyleSelector",
  created() {
    // var types = this.$route.params;
    this.name = this.$route.params.name;
    this.all.set("line", "标准折线图");
    this.$root.get("/chart/types", { name: this.name }, response => {
      for (var entry of this.all)
        if (response.data.indexOf(entry[0]) >= 0)
          this.types.push({ id: entry[0], name: entry[1] });
    });
  },
  data() {
    return {
      all: new Map([
        ["line", "标准折线图"],
        ["scatter", "标准散点图"],
        ["bar", "标准柱状图"],
        ["pie", "标准饼图"]
      ]),
      types: [],
      name: ""
    };
  },
  methods: {
    getImageSrc(name) {
      return "/static/images/" + name + ".jpg";
    },
    handleSelect(type) {
      this.$router.push({
        name: "ChartEditor",
        params: {
          name: this.name,
          type: type
        }
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
</style>
