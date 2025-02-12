import { useNavigate } from "react-router-dom";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useEffect, useState } from "react";

export default function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        setLoading(true);
        const token = localStorage.getItem("token");
        if (!token) {
          navigate("/login");
          return;
        }
        const decodeToken = jwt_decode(token);
        const userId = decodeToken.sub;
        const response = await axios.get(
          `http://localhost:8000/cart-service/eshoppingzone/cart/viewCart?userId=${userId}`
        );
        setCartItems(response.data);
        setError(null);
      } catch (err) {
        console.error("Error fetching cart items:", err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchCartItems();
  }, [navigate]);

  const addToCart = async (item) => {
    try {
      const token = localStorage.getItem("token");
      const decodedToken = jwt_decode(token);
      const userId = decodedToken.sub; // Extract user ID from token
      await axios.post(
        "http://localhost:8000/product-service/eshoppingzone/cart/addProducts",
        {
          userId,
          productId: item.productId,
          productName: item.productName,
          productImage: item.productImage,
          price: item.productPrice,
          quantity: 1,
        }
      );
      setCartItems((prevItems) =>
        prevItems.map((cartItem) =>
          cartItem.productId === item.productId
            ? { ...cartItem, quantity: cartItem.quantity + 1 }
            : cartItem
        )
      );
    } catch (error) {
      console.error("Error adding to cart:", error);
    }
  };

  const removeFromCart = async (item) => {
    try {
      const token = localStorage.getItem("token");
      const decodedToken = jwt_decode(token);
      const userId = decodedToken.sub; // Extract user ID from token
      await axios.delete("/eshoppingzone/cart/deleteProduct", {
        params: { userId, productId: item.productId },
      });
      setCartItems((prevItems) =>
        prevItems.filter((cartItem) => cartItem.productId !== item.productId)
      );
    } catch (error) {
      console.error("Error removing from cart:", error);
    }
  };

  const clearCart = async () => {
    try {
      const token = localStorage.getItem("token");
      const decodedToken = jwt_decode(token);
      const userId = decodedToken.sub; // Extract user ID from token
      await axios.delete("/eshoppingzone/cart/emptyCart", {
        params: { userId },
      });
      setCartItems([]);
    } catch (error) {
      console.error("Error clearing cart:", error);
    }
  };

  const getCartTotal = () => {
    return cartItems.reduce(
      (total, item) => total + item.productPrice * item.quantity,
      0
    );
  };

  if (loading) return <div>Loading...</div>;
  if (error) {
    return <div>Error loading cart: {error}</div>;
  }

  return (
    <div className="flex-col flex items-center fixed inset-0 gap-8 p-10 text-black font-normal uppercase text-sm">
      <h1 className="text-2xl font-bold">Cart</h1>
      <div className="absolute right-16 top-10">
        <button
          className="px-4 py-2 bg-blue-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700"
          onClick={() => navigate("/")}
        >
          Close
        </button>
      </div>
      <div className="flex flex-col gap-4">
        {cartItems.map((item) => (
          <div
            className="flex justify-between items-center"
            key={item.productId}
          >
            <div className="flex gap-4">
              <img
                src={item.productImage}
                alt={item.productName}
                className="rounded-md h-24"
              />
              <div className="flex flex-col">
                <h1 className="text-lg font-bold">{item.productName}</h1>
                <p className="text-gray-600">${item.productPrice}</p>
              </div>
            </div>
            <div className="flex gap-4">
              <button
                className="px-4 py-2 bg-blue-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700"
                onClick={() => addToCart(item)}
              >
                +
              </button>
              <p>{item.quantity}</p>
              <button
                className="px-4 py-2 bg-blue-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700"
                onClick={() => removeFromCart(item)}
              >
                -
              </button>
            </div>
          </div>
        ))}
      </div>
      {cartItems.length > 0 ? (
        <div className="flex flex-col justify-between items-center">
          <h1 className="text-lg font-bold">Total: ${getCartTotal()}</h1>
          <button
            className="px-4 py-2 bg-blue-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700"
            onClick={clearCart}
          >
            Clear cart
          </button>
        </div>
      ) : (
        <h1 className="text-lg font-bold">Your cart is empty</h1>
      )}
    </div>
  );
}
