import React from 'react';
import { ConnectButton } from 'web3uikit';
import './App.css';
import EnsWatcher from './EnsWatcher';

function App() {
  return (
    <>
      <header>
        <nav>
          <div className="logo">&#128373;</div>
          <span className="logo">ENS Watcher</span>
          <ConnectButton />
        </nav>
      </header>
      <main>
        <EnsWatcher />
      </main>
    </>
  );
}

export default App;
