// Save Note
export async function createAccount(email, password,name, authUrl,router,identification) {
  const createAccountDetails = {
    email: email,
    password: password,
    name: name,
    identification:identification
  };

  try {
    const response = await fetch(`${authUrl}/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(createAccountDetails)
    });

    if (!response.ok) {
      const errorText = await response.text();
      return `Error ${response.status}: ${errorText}`;
    }

    const result = await response.json();
    console.log('Login successful:', result);

    const token = result.token;

    localStorage.setItem('authToken', token);  
    localStorage.setItem('email', email);
    localStorage.setItem('name',name)

    console.log('Token saved:', token);
    router.push('/home');
    return null;
  } catch (error) {
    console.error('Create account failed:', error);
    return 'Something went wrong! Please try again later.';
  }
}
