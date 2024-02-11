module.exports ={
  outputDir: "../resources/static",

  devServer: {
      proxy: {
          '/': {
              "target": 'http://localhost:8086/',
              "pathRewrite": { '^/': '' },
              "changeOrigin": true,
              "secure": false
          }
      }
  }
};