import Cart from "./components/Cart";
import Login from "./components/Login";
import Product from "./components/Product";
import Register from "./components/Register";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Product />}></Route>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/register" element={<Register />}></Route>
      <Route path="/products" element={<Product />}></Route>
      <Route path="/cart" element={<Cart />}></Route>
    </Routes>
  );
}

export default App;
