// Get Note

export  async function getNote(url, notes,closePopup) {
  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    // ✅ Extract and use the JSON data
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
