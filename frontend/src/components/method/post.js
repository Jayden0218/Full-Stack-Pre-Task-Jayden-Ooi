// Save Note
import { formatToLocalDateTime } from '@/utils/dateUtils'

export async function createNote(noteTemplate, selectedColor, notes,closePopup,url) {
  const currentTime = Date.now()
  const noteData = {
    ...noteTemplate,
    color: selectedColor,
    createdAt: formatToLocalDateTime(currentTime).toString(),
    updatedAt: formatToLocalDateTime(currentTime).toString()
  }

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(noteData)
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    const result = await response.json()
    notes.value.push(result)
    console.log('Note saved to backend:', result)
  } catch (error) {
    console.error('Failed to save note:', error)
  }

  closePopup(closePopup)
}