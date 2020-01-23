module.exports = {
  "devServer": {
    "proxy": {
      "/api": {
        "target": "http://localhost:8098",
        "ws": true,
        "changeOrigin": true
      }
    }
  },

  "outputDir": "target/dist",
  "assetsDir": "static",

  "transpileDependencies": [
    "vuetify"
  ],

  "pluginOptions": {
    "i18n": {
      "locale": 'en',
      "fallbackLocale": 'en',
      "localeDir": 'locales',
      "enableInSFC": true
    }
  },
  
  "css": {
    "extract": { "ignoreOrder": true },
},
}
