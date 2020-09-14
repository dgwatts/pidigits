import React from 'react';
import './App.css';
import PiDigits from './components/PiDigits/PiDigits'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src="pi.png" className="App-logo" alt="logo" />
        <p>
          Pi Digits Webservice UI
        </p>
		  <PiDigits/>
      </header>
    </div>
  );
}

export default App;
