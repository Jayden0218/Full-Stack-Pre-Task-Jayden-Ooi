<template>
  <div class="flex flex-wrap justify-center gap-4 w-full">
    <div
      v-for="note in notes"
      :key="note.id"
      @click="handleNoteClick(note)"
      class="w-1/4 p-4 border border-gray-300 rounded-lg shadow-lg cursor-pointer hover:shadow-2xl transition min-w-40 max-w-70 flex flex-col justify-between"
      :style="{ backgroundColor: note.color || '#ffffff' }"
    >
    <div>
      <!-- Title -->
      <div class="font-semibold text-lg mb-2 break-words whitespace-normal">
        {{ note.title }}
      </div>

      <!-- Content -->
      <div class="text-gray-600 mb-2 break-words whitespace-normal">
        {{ note.content }}
      </div>
    </div>
    <div>

      <!-- Bottom row -->
      <div class="flex flex-row justify-between">
        <!-- Created / Edited at message -->
        <div class="text-xs text-gray-500 mt-2">
          {{ note.updatedAt ? `Edited at ${formatDate(note.updatedAt)}` : `Created at ${formatDate(note.createdAt)}` }}
        </div>

        <!-- Delete Icon -->
        <button
          @click.stop="handleDelete(note.id)"
          class="px-2 py-2 rounded flex items-center justify-center"
        >
          <DeleteIcon />
        </button>
      </div>
        </div>
    </div>
  </div>
</template>

<script setup>
  import DeleteIcon from '@/components/icons/DeleteIcon.vue'

  // Props
  defineProps({
    notes: Array,
    formatDate: Function
  })

  // Emits
  const emit = defineEmits(['edit', 'delete'])

  // Handlers
  const handleNoteClick = (note) => emit('edit', note)
  const handleDelete = (id) => emit('delete', id)
</script>
