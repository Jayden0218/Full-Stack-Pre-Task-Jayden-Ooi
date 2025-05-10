<template>
  <!-- Header -->
  <div class="flex flex-row justify-between items-center w-full p-4">

    <!-- Left = Logo + Name -->
    <div class="flex flex-row justify-center items-center space-x-4">
      <img :src="signBoard2" alt="signBoard" class="w-32 h-32" />
      <h1
        class="text-4xl font-bold bg-gradient-to-r from-blue-600 via-blue-500 to-blue-300 bg-clip-text text-transparent oswald-font md:block hidden">
        File Management System
      </h1>
    </div>
    
    <!-- Right = User + Image -->
    <div class="flex flex-row justify-center items-center space-x-4 pr-15">
      <!-- Display userName or Log In if there's no userName -->
      <h1 class="oswald-font text-3xl pr-5">{{ userName || "" }}</h1>
      
       <button v-if="userName"  @click="logout">
        <h1 class="oswald-font text-2xl pr-5">Log Out</h1>
       </button>
      <RouterLink v-else to="/account">
        <h1 class="oswald-font text-2xl pr-5">Log In</h1>
      </RouterLink>
      
      <!-- User avatar or default -->
      <div class="w-15 h-15 bg-blue-400 rounded-full flex justify-center items-center border-4 border-blue-600">
        <span class="text-white font-bold text-xl">{{ userName ? userName[0] : "F" }}</span>
      </div> 
    </div>

  </div>
</template>

<script setup>
    import signBoard2 from '@/assets/note3.png'

    // Get the userName from localStorage
    let userName = localStorage.getItem('name')

    function logout() {
        localStorage.removeItem('authToken');
        localStorage.removeItem('email');
        localStorage.removeItem('name');
        console.log('User logged out');

        // Optional: Redirect to login or home page
        window.location.href = '/';
      }
</script>
