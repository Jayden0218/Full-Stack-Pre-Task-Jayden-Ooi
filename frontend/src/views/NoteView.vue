<template>

  <!-- Main div to set up everything -->
  <div class="flex flex-col w-full">

    <HeaderView/>

    <SearchView v-model="searchQuery"/>

    <!-- Height Spacing -->
    <div class="h-10" />

    <NoteGridView
      :notes="filteredNotes"
      :formatDate="formatDate"
      @edit="openEditPopup"
      @delete="deleteNote"
    />

    <ButtonComponent @click="openAddPopup"/>

    <!-- Blur Effect-->
    <div
      v-if="showPopup"
      class="fixed inset-0 bg-opacity-40 backdrop-blur-xs flex items-center justify-center z-50"
    >

      <!-- PopUp Window -->
      <div class="w-96 p-4 bg-white border border-gray-300 rounded-lg shadow-lg relative">

        <!-- Top Row -->
        <div class="flex justify-between items-center mb-4">

          <!-- Header -->
          <h1 class="oswald-font text-center flex-1">
            {{ editing ? 'Edit File' : 'Add a new File' }}
          </h1>

          <!-- Close Button -->
          <button @click="closePopup" class="text-gray-500 hover:text-gray-700">
            <XIcon/>
          </button>

        </div>

        <!-- Form for submission -->
        <form
          @submit.prevent="editing ? updateNote() : createNote()"
          class="space-y-2 flex flex-col items-center w-full px-4"
        >

          <!-- Title Input -->
          <input
            v-model="currentForm.title"
            placeholder="Write an attractive Title"
            required
            class="outline-none border-none focus:ring-0 px-3 py-3 bg-gray-100 rounded w-full"
          />

          <!-- Description Input -->
          <textarea
            v-model="currentForm.content"
            placeholder="Write a never forget description"
            required
            class="ml-1 outline-none border-none focus:ring-0 px-3 py-3 bg-gray-100 rounded w-full h-40 resize-none"
          />

          <!-- Row for Color Selction -->
          <div class="flex space-x-4 py-4">

            <!-- Color Selction -->
            <div
              v-for="(color, index) in colors"
              :key="index"
              :class="[
                'w-10 h-10 rounded-full cursor-pointer transition',
                selectedColor === color ? 'ring-4 ring-gray-800' : '',
              ]"
              :style="{ backgroundColor: color }"
              @click="selectColor(color)"
            />

          </div>

          <!-- Button for submit -->
          <button
            type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 oswald-font w-full"
          >
            {{ editing ? 'Update File' : 'Save File' }}
          </button>

        </form>

      </div>

    </div>

  </div>

</template>

<script setup>
import { ref, computed,onMounted } from 'vue'
import { formatDate } from '@/utils/dateUtils'
import SearchView from './SearchView.vue'
import XIcon from '@/components/icons/xIcon.vue';
import HeaderView from '@/components/HeaderComponent.vue'
import NoteGridView from '@/components/NoteGridComponent.vue'
import ButtonComponent from '@/components/ButtonComponent.vue'
import { deleteNote as deleteNoteAPI } from '@/components/method/delete.js'
import { createNote as createNoteAPI } from '@/components/method/post.js'
import { updateNote as updateNoteAPI } from '@/components/method/put.js'
import { getNote as getNoteAPI } from '@/components/method/get.js'

const url = 'http://localhost:4000/notes';

const notes = ref([])
const searchQuery = ref('')
const showPopup = ref(false)
const editing = ref(false)
const noteTemplate = ref({ title: '', content: ''})
const colors = ['#ef4444', '#3b82f6', '#22c55e', '#facc15', '#a855f7']
const selectedColor = ref('#ef4444') // Start with red

// Use to filter out in the search
const filteredNotes = computed(() =>
  notes.value.filter(n =>
    n.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    n.content.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
)

// Text Editing Controller
const currentForm = computed({
  get: () => (editing.value ? noteTemplate.value : noteTemplate.value),
  set: v => {
    if (editing.value) noteTemplate.value = v
    else noteTemplate.value = v
  }
})

// First save window for note
function openAddPopup() {
  noteTemplate.value = { title: '', content: ''}
  selectedColor.value = colors[0] // Always set the first color to be selected
  editing.value = false
  showPopup.value = true
}

// Edit window for note
function openEditPopup(note) {
  noteTemplate.value = { ...note }
  selectedColor.value = note.color || '#ef4444'
  editing.value = true
  showPopup.value = true
}

function closePopup() {
  showPopup.value = false
}

// Select color function
function selectColor(color) {
  selectedColor.value = color
  currentForm.value.color = color
}

function createNote() {
  createNoteAPI(noteTemplate.value, selectedColor.value, notes, closePopup,url)
}

function getNote() {
  getNoteAPI(url, notes,closePopup)
}

function updateNote() {
  updateNoteAPI(noteTemplate, notes,selectedColor,url,closePopup)
}

function deleteNote(deletedId) {
  deleteNoteAPI(deletedId, url, notes)
}

onMounted(() => {
  getNote(); 
});
</script>


