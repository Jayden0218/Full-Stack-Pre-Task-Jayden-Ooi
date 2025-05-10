<template>
  <div class="fixed bottom-4 right-4 z-50">
    <button
      @click="$emit('click')"
      class="w-14 h-14 rounded-full bg-blue-600 text-white flex items-center justify-center shadow-lg hover:bg-blue-700 text-2xl"
    >
      +
    </button>
  </div>
</template>

<script setup>
  defineEmits(['click'])
</script>
