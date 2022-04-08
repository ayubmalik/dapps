import './App.css';
import Alert from './components/Alert/Alert';
import CartSuccess from './components/CartSuccess/CartSuccess';

function App() {
  return (
    <div className="wrapper">
      <Alert title="Items Not Added" type="error">
        <div>Your items are out of stock.</div>
      </Alert>
      <CartSuccess />
    </div>
  );
}

export default App;
