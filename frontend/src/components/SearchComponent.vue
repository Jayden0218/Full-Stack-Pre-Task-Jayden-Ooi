<template>
  <!-- Search Bar -->
  <div class="py-4 relative w-full px-40">

    <!-- Search Icon -->
    <div class="absolute inset-y-0 left-45 flex items-center pointer-events-none">
      <SearchIcon />
    </div>

    <!-- Border Input -->
    <input
      v-model="inputValue"
      type="text"
      placeholder="Search notes"
      class="w-full p-3 pl-16 pr-4 rounded-full border-2 border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500 oswald-font"
      @input="$emit('update:modelValue', modelValue)"
    />

  </div>
</template>


<script setup>
  import {watch, ref } from 'vue'
  import SearchIcon from '@/components/icons/SearchIcon.vue'

  const props = defineProps({
    modelValue: String
  })

  const emit = defineEmits(['update:modelValue'])

  // Create a local ref to use with v-model
  const inputValue = ref(props.modelValue)

  // Watch for external changes
  watch(() => props.modelValue, newVal => {
    inputValue.value = newVal
  })

  // Emit on input
  watch(inputValue, (newVal) => {
    emit('update:modelValue', newVal)
  })
</script>
