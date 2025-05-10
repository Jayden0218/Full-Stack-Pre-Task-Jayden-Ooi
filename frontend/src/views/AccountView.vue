<template>

  <!-- Whole screen div -->
  <div class="flex flex-row h-screen">

    <!-- Show 3D component - use w-1/2 -->
    <div class="hidden sm:flex w-1/2 items-center justify-center">
      <threeDView/>
    </div>

    <!-- Another w-1/2 -->
    <div class="w-full sm:w-1/2 flex items-center justify-center">

      <!-- Whole box -->
      <div class="relative
            border border-gray-300 rounded-lg p-6 shadow-lg bg-white w-80">

        <!-- Header -->
        <div class="flex flex-row justify-between">
          <div class="w-4"/>
          <h1 class="text-2xl font-bold mb-4 text-center">{{ isLogin ? 'Log in' : 'Create Account' }}</h1>
          <RouterLink to="/home">
            <XIcon/>
          </RouterLink>
        </div>

        <!-- Form input -->
        <form
          @submit.prevent="isLogin ? logInAccount() : createAccount()"
          class="space-y-2 flex flex-col items-center w-full px-4"
        >

          <!-- Name input -->
          <div v-if="!isLogin" class="mb-4 w-full">
            <label for="name" class="block text-sm font-medium mb-1">Name:</label>
            <input
              v-model="name"
              type="text"
              id="name"
              name="name"
              required
              class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
              placeholder="Enter your name"
            />
          </div>

          <!-- Email input -->
          <div class="mb-4 w-full">
            <label for="email" class="block text-sm font-medium mb-1">Email:</label>
            <input
              v-model="email"
              type="email"
              id="email"
              name="email"
              required
              class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
              placeholder="Enter your email"
            />
          </div>

          <!-- password input -->
          <div class="mb-6 w-full">
            <label for="password" class="block text-sm font-medium mb-1">Password:</label>
            <input
              v-model="password"
              type="password"
              id="password"
              name="password"
              required
              class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
              placeholder="Enter your password"
            />
            <p v-if="errorMessage" class="text-sm text-red-500 mt-1">{{ errorMessage }}</p>
          </div>

          <!-- Submit Button -->
          <button type="submit" class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600">
            {{ isLogin ? 'Login' : 'Create' }}
          </button>

          <!-- Change to another selection -->
          <p class="text-sm mt-2 text-gray-600 cursor-pointer hover:underline" @click="isLogin = !isLogin">
            {{ isLogin ? 'Don’t have an account? Create one' : 'Already have an account? Log in' }}
          </p>

        </form>

      </div>

    </div>

  </div>

</template>



<script setup>
  import XIcon from '@/components/icons/xIcon.vue';
  import { createAccount as createAccountAPI } from '@/components/method/withAuth/createAccount.js';
  import { logInAccount as logInAccountAPI } from '@/components/method/withAuth/logInAccount.js';
  import threeDView from '@/components/threeDComponent.vue';
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  
  // Router for route
  const router = useRouter();

  // Name, email and password ref
  const name = ref('');
  const email = ref('');
  const password = ref('');

  // Check user login in or sign up
  const isLogin = ref(true)

  // URL for your API 
  const url = 'http://localhost:4000';

  //Show error message
  const errorMessage = ref('');

  // Function to create the account
  async function createAccount() {
    if (password.value.length < 8) {
      errorMessage.value = 'Password must be at least 8 characters long.'
      return
    }
      const identification = localStorage.getItem('identification')
      const error = await createAccountAPI(email.value, password.value, name.value, url, router, identification);

      if (error) {
        errorMessage.value = error;
      }
    }

  // Function to login in account
  async function logInAccount() {
    if (password.value.length < 8) {
      errorMessage.value = 'Password must be at least 8 characters long.'
      return
    }
    const identification = localStorage.getItem('identification')
  const error = await logInAccountAPI(email.value, password.value,url,router,identification);

  if (error) {
        errorMessage.value = error;
      }

  }
  
</script>
