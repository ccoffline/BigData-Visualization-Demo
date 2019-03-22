var category = {
  type: "category",
  boundaryGap: false,
  data: [],
  axisLabel: {
    formatter: "{value}"
  }
};

var value = {
  type: "value",
  axisLabel: {
    formatter: "{value}"
  }
};

var time = {
  type: "time"
};

export { category, value, time };
