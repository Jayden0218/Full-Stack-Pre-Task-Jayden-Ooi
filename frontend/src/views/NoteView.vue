<template>

  <!-- Main div to set up everything -->
  <div class="flex flex-col w-full max-w-7xl mx-auto justify-center items-center">

    <HeaderView/>

    <SearchView v-model="searchQuery"/>

    <!-- Show info to user -->
    <h1 class="oswald-font text-gray-500" v-if="notes.length === 0">
      Welcome to File Management System! Try Adding your first File!
    </h1>
    <h1 class="oswald-font text-gray-500" v-else-if="!isLoggedIn">
      Please log in to make sure your file didn't disappear!
    </h1>
    <h1 class="oswald-font text-gray-500" v-else>
      Here are your files. Start managing them efficiently!
    </h1>

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
                selectedColor === color ? 'ring-3 ring-gray-400' : '',
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
  import SearchView from '../components/SearchComponent.vue'
  import XIcon from '@/components/icons/xIcon.vue';
  import HeaderView from '@/components/HeaderComponent.vue'
  import NoteGridView from '@/components/NoteGridComponent.vue'
  import ButtonComponent from '@/components/ButtonComponent.vue'
  import { deleteNote as deleteNoteAPI } from '@/components/method/noAuth/delete.js'
  import { createNote as createNoteAPI } from '@/components/method/noAuth/post.js'
  import { updateNote as updateNoteAPI } from '@/components/method/noAuth/put.js'
  import { getNote as getNoteAPI } from '@/components/method/noAuth/get.js'
  import { getNoteAuth as getNoteAuthAPI } from '@/components/method/withAuth/getAuth.js'
  import { createNoteAuth as createNoteAuthAPI } from '@/components/method/withAuth/postAuth.js'
  import { updateNoteAuth as updateNoteAuthAPI } from '@/components/method/withAuth/putAuth.js'
  import { deleteNoteAuth as deleteNoteAuthAPI } from '@/components/method/withAuth/deleteAuth.js'

  // url for normal connection
  const url = 'http://localhost:4000/notes';
  // url when user already authenticate
  const authUrl = 'http://localhost:4000/auth/notes';
  // Check user logIn status to show text info
  const isLoggedIn = computed(() => !!localStorage.getItem('authToken'));

  // Use to save all note inserted
  const notes = ref([])
  // Use to save search query
  const searchQuery = ref('')
  // Check pop up window
  const showPopup = ref(false)
  // Check the word typed
  const editing = ref(false)
  // Template for easy reuse
  const noteTemplate = ref({ title: '', content: '' })
  // Color selection available
  const colors = ['#ACDDDE', '#CAF1DE', '#E1F8DC', '#FEF8DD', '#FFE7C7', '#F7D8BA']
  // Save the selected color user selected
  const selectedColor = ref('') 
  
  // Use to filter out in the search
  const filteredNotes = computed(() =>
    notes.value.filter(n =>
      n.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      n.content.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  )

  // Check form text
  const currentForm = computed({
    get: () => (editing.value ? noteTemplate.value : noteTemplate.value),
    set: v => {
      if (editing.value) noteTemplate.value = v
      else noteTemplate.value = v
    }
  })

  // --------------------------------------
  // Pop up window function
  // --------------------------------------

  // Run when user start create a note
  function openAddPopup() {
    noteTemplate.value = { title: '', content: ''}
    selectedColor.value = colors[0]
    editing.value = false
    showPopup.value = true
  }

  // Run when user edit a note
  function openEditPopup(note) {
    noteTemplate.value = { ...note }
    selectedColor.value = note.color || colors[0]
    editing.value = true
    showPopup.value = true
  }

  // Close pop up
  function closePopup() {
    showPopup.value = false
  }

  // Select color function
  function selectColor(color) {
    selectedColor.value = color
    currentForm.value.color = color
  }

  // --------------------------------------
  // CRUD Method
  // --------------------------------------

  // Create note
  function createNote() {
    const token = localStorage.getItem('authToken')
    if (token) {
      console.log("auth")
      const email = localStorage.getItem('email');
      createNoteAuthAPI(noteTemplate.value, selectedColor.value, notes, closePopup, authUrl,token,email)
    }
    else {
      const identification = localStorage.getItem('identification')
      createNoteAPI(noteTemplate.value, selectedColor.value, notes, closePopup,url,identification)
      console.log("no auth createnote")
    }
  }

  // Get note
  function getNote() {
    const token = localStorage.getItem('authToken')
    if (token) {
      const email = localStorage.getItem('email');
      getNoteAuthAPI(authUrl, notes, closePopup, token,email)
      console.log("auth")
    }
    else {
      const identification = localStorage.getItem('identification')
      getNoteAPI(url, notes, closePopup,identification)
      console.log("no auth")
    }
  }
  // Update note
  function updateNote() {
    const token = localStorage.getItem('authToken')
    if (token) {
      console.log("auth")
      const email = localStorage.getItem('email');
      updateNoteAuthAPI(noteTemplate.value, notes, selectedColor.value, authUrl, closePopup,token,email)
    }
    else {
      const identification = localStorage.getItem('identification')
      updateNoteAPI(noteTemplate.value, notes,selectedColor.value,url,closePopup,identification)
      console.log("no auth update")
    }
  }

  // Delete note
  function deleteNote(deletedId) {
    const token = localStorage.getItem('authToken')
    if (token) {
      deleteNoteAuthAPI(deletedId, authUrl, notes,token)
      console.log("auth")
    }
    else {
      deleteNoteAPI(deletedId, url, notes)
      console.log("no auth")
    }
  }
  // Update the latest note
  onMounted(() => {
    getNote(); 
  });
</script>


