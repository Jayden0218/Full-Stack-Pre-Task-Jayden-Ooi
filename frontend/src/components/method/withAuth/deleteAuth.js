// Delete a note
export async function deleteNoteAuth(deletedId, authUrl, notes,token) {
  //  const index = notes.value.findIndex(n => n.id === deletedId)
  console.log(deletedId)

  try {

    const response = await fetch(`${authUrl}/${deletedId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`

      },
      // body: JSON.stringify(noteUpdated)
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);

    }

    // const result = await response;
    notes.value = notes.value.filter(n => n.id !== deletedId)
    // notes.value[index] = noteUpdated
    
    // Add to local state
    // notes.value.push(result);
    console.log('Note deleted to backend:', response);
  } catch (error) {
    console.error('Failed to save note:', error);
  }
}