module.exports = {
  lintOnSave: false,
  productionSourceMap: false,
  filenameHashing: true,
  publicPath: '',

  devServer: {
    hot: true,
    proxy: {
      '/api': {
        target: 'https://k4d106.p.ssafy.io',
        // target: 'http://k4d106.p.ssafy.io:8080',
      },
      '/api/face': {
        target: 'http://k4d106.p.ssafy.io:8000',
        // changeOrigin: true,
      },
      '/profile': {
        target: 'https://k4d106.p.ssafy.io',
      },
    },
  },
  css: {
    loaderOptions: {
      css: {
        sourceMap: process.env.NODE_ENV !== 'production' ? true : false,
      },
    },
  },
};
