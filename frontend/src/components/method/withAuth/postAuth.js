// Save Note
import { formatToLocalDateTime } from '@/utils/dateUtils'

export async function createNoteAuth(noteTemplate, selectedColor, notes,closePopup,authUrl,token,email) {
  const currentTime = Date.now()
  const noteData = {
    ...noteTemplate,
    color: selectedColor,
    createdAt: formatToLocalDateTime(currentTime).toString(),
    updatedAt: formatToLocalDateTime(currentTime).toString(),
    identification: email
  }

  try {
    const response = await fetch(authUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
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