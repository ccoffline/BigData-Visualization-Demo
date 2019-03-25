<template>
  <div>
    <div
      v-for="(layout,index) in layouts"
      :key="index"
    >
      <h1>{{layout.title}}</h1>
      <img
        :src="$root.getLayoutSrc(layout.name)"
        @click="setSelected(index)"
      />
      <transition name="el-fade-in-linear">
        <el-row
          v-if="selected==index"
          class="inputs"
        >
          <el-col :span="8">
            <el-button
              type="warning"
              @click="clear(index)"
            >
              清空
            </el-button>
          </el-col>
          <el-col
            :span="8"
            :offset="8"
          >
            <el-button
              type="primary"
              @click="select(index)"
            >
              选择
            </el-button>
          </el-col>
        </el-row>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: "LayoutManager",
  created() {
    this.$root.get("/layout/", null, response => {
      this.layouts = response.data;
    });
  },
  data() {
    return {
      layouts: [],
      selected: null
    };
  },
  methods: {
    setSelected(index) {
      if (this.selected == index) this.selected = null;
      else this.selected = index;
    },
    select(index) {
      this.$root.postForm("/layout/select", { index: index }, response => {
        this.$message.success("选择成功");
      });
    },
    clear(index) {
      this.$root.postForm("/layout/clear", { index: index }, response => {
        this.$message.success("清空成功");
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
.el-select,
.el-button,
.el-input {
  width: 100%;
  margin-bottom: 10px;
}
.inputs {
  padding: 10px;
}
h1 {
  color: #adadad;
  text-align: left;
  margin: 10px 0;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}
</style>
