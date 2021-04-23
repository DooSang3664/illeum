<template>
  <v-app id="app">
    <div id="nav">
      <router-link to="/">Home</router-link>
      |
      <router-link to="/about">About</router-link>
      |
      <router-link to="/webrtcTest">webrtcTest</router-link>
      |
      <router-link to="/webrtcTest2">webrtcTest2</router-link>
      |
      <router-link to="/webrtcTest3">webrtcTest3</router-link>
      |
      <router-link to="/webrtcTest4">webrtcTest4</router-link>
      |
      <router-link to="/componentTest">componentTest</router-link>
      |
      <router-link to="/chartTest">chartTest</router-link>
    </div>
    <router-view />
  </v-app>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
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
</style>
