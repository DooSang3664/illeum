<template>
  <div id="container">
    <h1>해당페이지 XXXXXXX</h1>
    <video id="localVideo" playsinline autoplay muted></video>
    <video id="remoteVideo" playsinline autoplay></video>

    <div class="box">
      <button id="startButton" ref="startButton">Start</button>
      <button id="callButton" ref="callButton">Call</button>
      <button id="hangupButton" ref="hangupButton">Hang Up</button>
    </div>
  </div>
</template>
