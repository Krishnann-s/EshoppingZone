import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Product() {
  const [products, setProducts] = useState([]);
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    async function getProducts() {
      try {
        setLoading(true);
        const token = localStorage.getItem("token");
        if (!token) {
          navigate("/login");
          return;
        }
        const response = await axios.get(
          "http://localhost:8000/product-service/eshoppingzone/products",
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setProducts(response.data || []);
        setError(null);
      } catch (error) {
        console.error("Error fetching products:", error);
        setError(error.message);
        if (error.response?.status === 401 || error.response?.status === 403) {
          // localStorage.removeItem("token");
          // navigate("/login");
          console.log("403 Forbidden");
        }
      } finally {
        setLoading(false);
      }
    }
    getProducts();
  }, [navigate]);

  useEffect(() => {
    async function fetchCartItems() {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          navigate("/login");
          return;
        }
        const decodedToken = jwtDecode(token);
        const userId = decodedToken.userId;
        const response = await axios.get(
          `http://localhost:8000/cart-service/eshoppingzone/cart/viewCart?userId=${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setCartItems(response.data);
        setError(null);
      } catch (err) {
        console.error("Error fetching cart items:", err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    fetchCartItems();
  }, [navigate]);

  const addToCart = async (product) => {
    try {
      const token = localStorage.getItem("token");
      const decodedToken = jwtDecode(token);
      // const userId = decodedToken.sub;
      const email = decodedToken.email; // Extract email from token
      await axios.post(
        "http://localhost:8000/product-service/eshoppingzone/cart",
        null,
        {
          params: {
            email,
            productId: product.productId,
            quantity: 1,
          },
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      setCartItems((prevItems) => {
        const existingItem = prevItems.find(
          (item) => item.productId === product.productId
        );
        if (existingItem) {
          return prevItems.map((item) =>
            item.productId === product.productId
              ? { ...item, quantity: item.quantity + 1 }
              : item
          );
        } else {
          return [...prevItems, { ...product, quantity: 1 }];
        }
      });
    } catch (error) {
      console.error("Error adding to cart:", error);
    }
  };

  // const removeFromCart = async (product) => {
  //   try {
  //     const token = localStorage.getItem("token");
  //     const decodedToken = jwtDecode(token);
  //     const userId = decodedToken.sub;
  //     await axios.delete(
  //       "http://localhost:8000/cart-service/eshoppingzone/cart/deleteProduct",
  //       {
  //         params: { userId, productId: product.productId },
  //       }
  //     );
  //     setCartItems((prevItems) =>
  //       prevItems.filter((item) => item.productId !== product.productId)
  //     );
  //   } catch (error) {
  //     console.error("Error removing from cart:", error);
  //   }
  // };

  // const clearCart = async () => {
  //   try {
  //     const token = localStorage.getItem("token");
  //     const decodedToken = jwtDecode(token);
  //     const userId = decodedToken.sub;
  //     await axios.delete(
  //       "http://localhost:8000/cart-service/eshoppingzone/cart/emptyCart",
  //       {
  //         params: { userId },
  //       }
  //     );
  //     setCartItems([]);
  //   } catch (error) {
  //     console.error("Error clearing cart:", error);
  //   }
  // };

  if (loading) return <div>Loading...</div>;
  if (error) {
    return <div>Error loading products: {error}</div>;
  }

  return (
    <div className="flex flex-col justify-center bg-gray-100">
      <div className="flex justify-between items-center px-20 py-5">
        <h1 className="text-2xl uppercase font-bold mt-10 text-center mb-10">
          Shop
        </h1>
        <h1 className="text-2xl uppercase font-bold mt-10 text-center mb-10">
          <button
            className="px-4 py-2 bg-gray-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700"
            onClick={() => navigate("/cart")}
          >
            Cart ({cartItems.length})
          </button>
        </h1>
      </div>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 px-10">
        {products.map((product) => (
          <div
            key={product.productId} // Ensure key matches the API response
            className="bg-white shadow-md rounded-lg px-10 py-10"
          >
            <img
              src={product.image}
              alt={product.title}
              className="rounded-md h-48"
            />
            <div className="mt-4">
              <h1 className="text-lg uppercase font-bold">{product.title}</h1>
              <p className="mt-2 text-gray-600 text-sm">
                {product.description?.slice(0, 40)}...
              </p>
              <p className="mt-2 text-gray-600">${product.price}</p>
            </div>
            <div className="mt-6 flex justify-between items-center">
              <button
                onClick={() => addToCart(product)}
                className="px-4 py-2 bg-gray-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700"
              >
                Add to cart
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Product;
