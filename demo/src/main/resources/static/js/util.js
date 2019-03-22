function routeTo(vue, name) {
  vue.$router.push({
    name: name
  });
}

const SERVER_URL = "localhost:8080";

function get(vue, url, params, success) {
  vue.$http
    .get(SERVER_URL + url, params)
    .then(success, vue.$message.error("网络错误"));
}

function post(vue, url, params, success) {
  vue.$http
    .post(SERVER_URL + url, params, { emulateJSON: true })
    .then(success, vue.$message.error("网络错误"));
}

export { get, post };
