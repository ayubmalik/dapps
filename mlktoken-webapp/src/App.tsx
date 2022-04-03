import React from 'react';
import './App.css';

function App() {
  const isWeb3Installed: boolean = window.ethereum !== undefined;

  return (
    <div className="App">
      <header className="App-header">
        <p>
          Web 3 (Metamask) installed =
          {isWeb3Installed}
        </p>
      </header>
    </div>
  );
}

export default App;
