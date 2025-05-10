// Save Note
export async function logInAccount(email, password,url,router,identifcaiton) {
  const logInAccountDetails = {
    email: email,
    password: password,
    identifcaiton: identifcaiton
  };

  try {
    const response = await fetch(`${url}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(logInAccountDetails)
    });

    if (!response.ok) {
      const errorText = await response.text();
      return `Error ${response.status}: ${errorText}`;
    }

    const result = await response.json();
    console.log('Login successful:', result);

    // Assuming the token is in the 'token' field in the response
    const token = result.token;
    const name = result.name;

    // Save the token to localStorage (or sessionStorage)
    localStorage.setItem('authToken', token);  
    localStorage.setItem('email', email);
    localStorage.setItem('name', name);

    console.log('Token saved:', token);
    router.push('/home');
    return null;
  } catch (error) {
    console.error('Create account failed:', error);
    return 'Something went wrong! Please try again later.';
  }
}
