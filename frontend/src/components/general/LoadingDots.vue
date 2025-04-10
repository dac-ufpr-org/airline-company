<template>
  <!-- Tela com fundo blur e loader centralizado -->
  <div class="loading-overlay fixed flex items-center justify-center bg-black">
    <div class="wrapper w-[200px] h-[60px] relative">
      <div class="circle"></div>
      <div class="circle"></div>
      <div class="circle"></div>
      <div class="shadow"></div>
      <div class="shadow"></div>
      <div class="shadow"></div>
    </div>
  </div>
</template>

<style scoped>
/* Fundo blur e centralização */
.loading-overlay {
  inset: 0;
  backdrop-filter: blur(6px);
  background-color: rgba(0, 0, 0, 0.7); /* fundo escuro com transparência */
  z-index: 9999;
}

/* Loader */
.wrapper {
  z-index: 1;
}

.circle {
  width: 20px;
  height: 20px;
  position: absolute;
  border-radius: 50%;
  background-color: #ffffff; /* cor do loader (clara para contraste) */
  left: 15%;
  transform-origin: 50%;
  animation: circle7124 0.5s alternate infinite ease;
}

@keyframes circle7124 {
  0% {
    top: 60px;
    height: 5px;
    border-radius: 50px 50px 25px 25px;
    transform: scaleX(1.7);
  }
  40% {
    height: 20px;
    border-radius: 50%;
    transform: scaleX(1);
  }
  100% {
    top: 0%;
  }
}

.circle:nth-of-type(2) {
  left: 45%;
  animation-delay: 0.2s;
}

.circle:nth-of-type(3) {
  left: auto;
  right: 15%;
  animation-delay: 0.3s;
}

.shadow {
  width: 20px;
  height: 4px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.2); /* sombra clara para fundo escuro */
  position: absolute;
  top: 62px;
  transform-origin: 50%;
  z-index: -1;
  left: 15%;
  filter: blur(1px);
  animation: shadow046 0.5s alternate infinite ease;
}

@keyframes shadow046 {
  0% {
    transform: scaleX(1.5);
  }
  40% {
    transform: scaleX(1);
    opacity: 0.7;
  }
  100% {
    transform: scaleX(0.2);
    opacity: 0.4;
  }
}

.shadow:nth-of-type(4) {
  left: 45%;
  animation-delay: 0.2s;
}

.shadow:nth-of-type(5) {
  left: auto;
  right: 15%;
  animation-delay: 0.3s;
}
</style>
