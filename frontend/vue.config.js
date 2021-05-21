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
