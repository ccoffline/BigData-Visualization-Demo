<template>
  <div>
    <img :src="$root.getLayoutSrc(this.layout)">
    <div class="inputs">
      <el-input
        placeholder="标题"
        v-model="title"
        clearable
      >
      </el-input>
      <el-select
        v-model="theme"
        placeholder="主题"
      >
        <el-option
          v-for="(theme,index) in themes"
          :key="index"
          :label="theme.title"
          :value="index"
        >
        </el-option>
      </el-select>
      <el-row>
        <el-col :span="8">
          <el-button
            type="warning"
            @click="back"
          >
            返回
          </el-button>
        </el-col>
        <el-col
          :span="8"
          :offset="8"
        >
          <el-button
            type="success"
            @click="submit"
          >
            提交
          </el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "LayoutEditor",
  created() {
    this.layout = this.$route.params.layout;
  },
  data() {
    return {
      layout: null,
      title: "",
      themes: [
        { name: "shine", title: "明亮" },
        //{ name: "dark", title: "幽暗" },
        { name: "macarons", title: "通心粉" }
      ],
      theme: null
    };
  },
  methods: {
    back() {
      this.$router.push({ name: "LayoutSelector" });
    },
    submit() {
      this.$root.postForm(
        "/layout/submit",
        {
          name: this.layout,
          theme: this.themes[this.theme].name,
          title: this.title
        },
        response => {
          this.$router.push({ name: "LayoutManager" });
        }
      );
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
img {
  width: 100%;
}
.el-select,
.el-button,
.el-input {
  width: 100%;
  margin-bottom: 10px;
}
.inputs {
  padding: 10px;
}
</style>
