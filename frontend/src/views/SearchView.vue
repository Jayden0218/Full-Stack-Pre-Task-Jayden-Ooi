<template>
  <!-- Search Bar -->
  <div class="flex justify-center items-center py-4 relative mx-50">

    <!-- Search Icon -->
    <div class="absolute inset-y-0 left-5 flex items-center pointer-events-none">
      <SearchIcon/>
    </div>

    <!-- Border Input -->
    <input
      v-model="inputValue"
      type="text"
      placeholder="Search notes"
      class="w-full p-3 pl-15 rounded-full border-2 border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500 oswald-font"
      @input="$emit('update:modelValue', modelValue)"
    />

  </div>
</template>

<script setup>
import { defineProps, defineEmits, watch, ref } from 'vue'
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
