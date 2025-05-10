// Delete a note
export async function deleteNote(deletedId, url, notes) {
  console.log(deletedId)

  try {
    const response = await fetch(`${url}/${deletedId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);

    }
    notes.value = notes.value.filter(n => n.id !== deletedId)
    console.log('Note deleted to backend:', response);
  } catch (error) {
    console.error('Failed to save note:', error);
  }
}