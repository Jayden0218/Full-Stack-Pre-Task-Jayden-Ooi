// Get Note

export  async function getNoteAuth(authUrl, notes,closePopup,token,email) {
  try {
    const response = await fetch(`${authUrl}/${email}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const result = await response.json();
    console.log('Fetched notes from backend:', result);
    result.forEach(note => {
      notes.value.push(note)
      console.log(note);
    });

  } catch (error) {
    console.error('Failed to fetch notes:', error);
  }

  closePopup();
}
