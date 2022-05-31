import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { MoralisProvider } from 'react-moralis';
import App from './App';
import 'typeface-roboto';
// import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement,
);
root.render(
  <React.StrictMode>
    <MoralisProvider appId="FvDKrxhvfGlDUdR4qoYl4umY3mEjeExJLgpetEAU" serverUrl="https://8zcwr9zz52jt.usemoralis.com:2053/server">
      <App />
    </MoralisProvider>
  </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
