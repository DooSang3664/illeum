module.exports = {
  lintOnSave: false,
  productionSourceMap: false, 
  filenameHashing: true, 
  publicPath: '', 

  devServer: {
    hot: true, 
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
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
