module.exports = {
  env: {
    browser: true,
    es2021: true,
  },
  extends: ["eslint:recommended", "plugin:vue/vue3-recommended"],
  parserOptions: {
    ecmaVersion: 12,
    sourceType: "module",
    parser: "@babel/eslint-parser",
    plugins: ["vue"],
    rules: {
      "vue/multi-word-component-names": "off",
      "no-const-assign": "off",
      "vue/valid-attribute-name": "error",
    },
  },
};
