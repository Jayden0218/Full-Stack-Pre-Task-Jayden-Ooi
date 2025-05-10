
import { formatToLocalDateTime } from '@/utils/dateUtils'

// Update an existing note
export async function updateNote(noteTemplate, notes,selectedColor,url,closePopup,identification) {
  const currentTime = Date.now();
 

  const index = notes.value.findIndex(n => n.id === noteTemplate.id)

  let noteUpdated;
  if (index !== -1) {
    noteUpdated = {
    ...noteTemplate,
    color: selectedColor,
    createdAt: notes.value[index].createdAt,
      updatedAt: formatToLocalDateTime(currentTime).toString(),
      identification:identification
  };
    // notes.value[index] = {
    //   ...noteTemplate.value,
    //   color: selectedColor.value,
    //   createdAt: notes.value[index].createdAt,
    //   updatedAt: formatToLocalDateTime(currentTime).toString()
    // }
  }

  console.log(noteUpdated)

  // Save to backend
  try {
    const response = await fetch(`${url}/${noteUpdated.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(noteUpdated)
    });

    

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);

    }

    const result = await response.json();
    notes.value[index] = noteUpdated
    
    // Add to local state
    // notes.value.push(result);
    console.log('Note updated to backend:', result);
  } catch (error) {
    console.error('Failed to save note:', error);
  }

  closePopup()
}