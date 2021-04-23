module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: ['plugin:vue/essential', '@vue/prettier', 'plugin:vue/recommended'],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'vue/no-use-v-if-with-v-for': 'off',
    'no-console': 'off',
    indent: 'off',
    // noInlineConfig: true,
    // allowTemplateLiterals: true,
  },
  parserOptions: {
    parser: 'babel-eslint',
  },
};
