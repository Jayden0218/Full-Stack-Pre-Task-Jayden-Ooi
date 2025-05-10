// Delete a note
export async function deleteNoteAuth(deletedId, authUrl, notes,token) {
  console.log(deletedId)

  try {
    const response = await fetch(`${authUrl}/${deletedId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`

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