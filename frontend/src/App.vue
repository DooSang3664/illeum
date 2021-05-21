<template>
  <v-app id="app">
    <router-view name="header" />
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<style>
@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: 'NEXON Lv1 Gothic OTF';
}
body {
  -ms-overflow-style: none;
  overflow-y: hidden;
}
body::-webkit-scrollbar {
  display: none;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #f9f9f9;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
/* .main-app {
  margin: 0 15% 0 15%;
} */
</style>
