import './App.css';
import React from 'react';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

function App() {
  const [userName, setUserName] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [isLoading, setIsLoading] = React.useState(false);
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState('');
  const [blockedPerson, setBlockedPerson] = React.useState('');
  const [confirmUsername, setConfirmUsername] = React.useState(false);
  const [friendUserName, setFriendUserName] = React.useState('');

  // new state variables for chat box
  const [toId, setToId] = React.useState('');
  const [message, setMessage] = React.useState('');

    async function SearchUsers() {
    const httpSettings = {
      method: 'GET',
      headers: {
        auth: cookies.get('auth'),
      }
    };
    const result = await fetch('/getUsername', httpSettings);
    const apiRes = await result.json();
    console.log(apiRes);
    if (apiRes.status) {
      // worked
      setUserName(apiRes.data); 
    } else {
      setErrorMessage(apiRes.message);
    }
  }
  // new state variable for list of convos
  const [conversations, setConversations] = React.useState([]); // default empty array
   
  async function getConversations() {
    const httpSettings = {
      method: 'GET',
      headers: {
        auth: cookies.get('auth'), // utility to retrive cookie from cookies
      }
    };
    const result = await fetch('/getConversations', httpSettings);
    const apiRes = await result.json();
    console.log(apiRes);
    if (apiRes.status) {
      // worked
      setConversations(apiRes.data); // java side should return list of all convos for this user
    } else {
      setErrorMessage(apiRes.message);
    }
  }

  async function handleSubmit() {
    setIsLoading(true);
    setErrorMessage(''); // fresh error message each time
    const body = {
      userName: userName,
      password: password,
    };
    const httpSettings = {
      body: JSON.stringify(body),
      method: 'POST'
    };
    const result = await fetch('/createUser', httpSettings);
    const apiRes = await result.json();
    console.log(apiRes);
    if (apiRes.status) {
      // user was created
      // todo
    } else {
      // some error message
      setErrorMessage(apiRes.message);
    }
    setIsLoading(false);
  };

  async function handleLogIn() {
    setIsLoading(true);
    setErrorMessage(''); // fresh error message each time
    const body = {
      userName: userName,
      password: password,
    };
    const httpSettings = {
      body: JSON.stringify(body),
      method: 'POST'
    };
    const result = await fetch('/login', httpSettings);
    if (result.status === 200) {
      // login worked
      setIsLoggedIn(true);
      getConversations();
    } else {
      // login did not work
      setErrorMessage(`Username or password incorrect.`);
    }

    setIsLoading(false);
  };

  async function handleLogOut() {
    setIsLoading(true);
  
    // Check if the 'auth' cookie exists
    if (cookies.get('auth')) {
      try {
        const httpSettings = {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'auth': cookies.get('auth') // include the auth token in the request header
          }
        };
  
        const result = await fetch('/logout', httpSettings);
        if (result.status === 200) {
          setIsLoggedIn(false);
          cookies.remove('auth'); // remove the auth token from cookies
        } else {
          // handle error
          console.error('Logout failed:', result.status);
        }
      } catch (error) {
        // handle error
        console.error('Logout failed:', error);
      }
    } else {
      // 'auth' cookie doesn't exist, handle the case when the user is already logged out
      console.log('User is already logged out');
    }
  
    setIsLoading(false);
  }


  async function handleBlockPerson() {
    setIsLoading(true);
    setErrorMessage(''); // fresh error message each time
    const body = {
      fromId: userName,
      blockedId: blockedPerson,
    };
    const httpSettings = {
      body: JSON.stringify(body),
      method: 'POST',
      headers: {
        auth: cookies.get('auth'),
      }
    };
    const result = await fetch('/blockUser', httpSettings);
    const apiRes = await result.json();
    console.log(apiRes);
    if (apiRes.status) {
      setBlockedPerson('');
      getConversations();
    } else {
      setErrorMessage(apiRes.message);
    }
    setIsLoading(false);
  }

  async function handleUnregUser() {
    setIsLoading(true);
    setErrorMessage('');
    if (confirmUsername !== userName) {
      setErrorMessage('Wrong username');
      setIsLoading(false);
      return;
    }
    const body = {
      username: userName,
    };
    const httpSettings = {
      body: JSON.stringify(body),
      method: 'POST',
      headers: {
        auth: cookies.get('auth'),
      },
    };
    try {
      const result = await fetch('/unregUser', httpSettings);
      const apiRes = await result.json();
      console.log(apiRes);
      if (apiRes.status) {
        setIsLoggedIn(false);
      } else {
        setErrorMessage(apiRes.message);
      }
    } catch (error) {
      console.error('Failed to unregister user:', error);
      setErrorMessage('Failed to unregister user');
    } finally {
      setIsLoading(false);
    }
  }

  async function handleAddFriend() {
    setIsLoading(true);
    setErrorMessage('');
    const body = {
      userName: userName,
      friendUserName: friendUserName,
    };
    const httpSettings = {
      body: JSON.stringify(body),
      method: 'POST',
      headers: {
        auth: cookies.get('auth'),
      }
    };
    try {
      const result = await fetch('/addFriend', httpSettings);
      const apiRes = await result.json();
      console.log(apiRes);
      if (apiRes.status) {
        // Friend added successfully
        // Handle success case, if needed
      } else {
        setErrorMessage(apiRes.message);
      }
    } catch (error) {
      console.error('Failed to add friend:', error);
      setErrorMessage('Failed to add friend');
    } finally {
      setIsLoading(false);
    }
  }

  async function handleSendMessage() {
    setIsLoading(true);
    setErrorMessage(''); // fresh error message each time
    const body = {
      fromId: userName,
      toId: toId,
      message: message,
    };
    const httpSettings = {
      body: JSON.stringify(body),
      method: 'POST',
      headers: {
        auth: cookies.get('auth'), // utility to retrive cookie from cookies
      }
    };
    const result = await fetch('/createMessage', httpSettings);
    const apiRes = await result.json();
    console.log(apiRes);
    if (apiRes.status) {
      // worked
      setMessage('');
      getConversations();
    } else {
      setErrorMessage(apiRes.message);
    }
    setIsLoading(false);
  };

  if (isLoggedIn) {
    return (
      <div className="App" class="profileContain">
        <h1 class="profileTitle">Welcome {userName}</h1>
        <div>
          To: <input value={toId} onChange={e => setToId(e.target.value)} />
        </div>
        
        <textarea value={message} onChange={e => setMessage(e.target.value)} />
        <div>
          <button class="LoginButton send" onClick={handleSendMessage}>Send Message</button>
        </div>
        <div class="addUser">
        Add Friend: 
        <input class="addBox addTop" value={friendUserName} onChange={e => setFriendUserName(e.target.value)} />
        <button class="endpointButton profileAdd" onClick={handleAddFriend}>Add</button>
        </div>
        <div class="blockUser">
        Block person: <input value={blockedPerson} onChange={e => setBlockedPerson(e.target.value)} />
        <button class="endpointButton block"onClick={handleBlockPerson}>Confirm</button>
        </div>
        <div>{errorMessage}</div>
        <div>{conversations.map(conversation => <div>Convo: {conversation.conversationId}</div>)}</div>
        <div>
          Confirm Username:
          <input value={confirmUsername} onChange={e => setConfirmUsername(e.target.value)} />
          <button onClick={handleUnregUser}>Confirm Unregister</button>
          {errorMessage && <div>{errorMessage}</div>}
        </div>
        SearchUser:
        <div class="search-container">
          <input type="search" placeholder="Search..">
          <button type = "Submit" onSubmit={SearchUsers}> Search</button>
          </input>
    </div>
       
       
        <div>
        <button class="logoutButton" onClick={handleLogOut}>Log Out</button>
        </div>
      </div>
    );
  }

  return (
    <div className="App" class="Contain">
      <h3 class="loginTitle">Login Here</h3>

      <div class="formGroup">
      <label class="userLabel">Username </label>
      <input value={userName} onChange={e => setUserName(e.target.value)} />
      </div>

      <div class="formGroup">
      <label class="passLabel" >Password </label>
      <input class="ProfileBox" value={password} onChange={e => setPassword(e.target.value)} type="password" />
      </div>

      <button class="ProfileButton profileLogin" onClick={handleLogIn} disabled={isLoading}>Log in</button>
      <button class="ProfileButton profileRegister" onClick={handleSubmit} disabled={isLoading}>Register here</button>

      <div>
        {isLoading ? 'Loading ...' : null}
      </div>
      <div>{errorMessage}</div>
    </div>
  );
}

export default App;