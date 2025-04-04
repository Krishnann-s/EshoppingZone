import "./App.css";
import Home from "./components/home/Home";
import Products from "./components/products/Products";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import NavBar from "./components/shared/NavBar";
import About from "./components/About";
import Contact from "./components/Contact";

function App() {
  return (
    <div className="bg-amber-50">
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/products" element={<Products />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
