<template>
  <!-- Header -->
  <div class="flex flex-row justify-between items-center w-full p-4">

    <!-- Left = Logo + Name -->
    <div class="flex flex-row justify-center items-center space-x-4">
      <img :src="signBoard2" alt="signBoard" class="w-32 h-32" />
      <h1
        class="text-4xl font-bold bg-gradient-to-r from-blue-600 via-blue-500 to-blue-300 bg-clip-text text-transparent oswald-font">
        File Management System
      </h1>
    </div>
    
    <!-- Right = User + Image -->
    <div class="flex flex-row justify-center items-center space-x-4 pr-15">
      <h1 class="oswald-font text-3xl pr-5">Jayden Ooi</h1>
      <div class="w-15 h-15 bg-blue-500 rounded-full"></div>
    </div>

  </div>
</template>

<script setup>
    import signBoard2 from '@/assets/note3.png' 
</script>