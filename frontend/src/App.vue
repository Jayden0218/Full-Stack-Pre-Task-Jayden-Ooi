<template>
  <!-- When application start, go to the page route -->
  <RouterView />
</template>

<script setup>
  import { RouterView, useRouter } from 'vue-router'
  import { onMounted } from 'vue';

  const router = useRouter();
  const url = 'http://localhost:4000';
  const token = localStorage.getItem('authToken')

  // Validate the token to see user have token
  async function validateToken() {
    try {
      const response = await fetch(`${url}/validate`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        },
      });

      if(response.status == 401){
        const now = new Date();

        let identification = localStorage.getItem('identification');
        console.log(identification)

        if(!identification){
          localStorage.setItem('identification',now.toString())
        }

        router.push('/home');
        return
      }

      if (!response.ok) {
        console.log('Error during token validation:', response.status);
        return;
        
      } 
      console.log('Token is still useful',);
      router.push('/home');

    } catch (error) {
      console.error('Failed token:', error);
    }
  }

  // Every time application start, run this function
  onMounted(() => {
    validateToken();
  });



</script>




