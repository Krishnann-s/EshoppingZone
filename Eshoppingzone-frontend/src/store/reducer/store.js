// store.js
import { configureStore } from "@reduxjs/toolkit";
import { productsReducer } from "./productsReducer";
import { errorReducer } from "./errorReducer";
import { cartReducer } from "./cartReducer";
import { authReducer } from "./authReducer";
import { parseJwt } from "../../utils/jwtDecoder";

const cartItems = localStorage.getItem("cartItems")
  ? JSON.parse(localStorage.getItem("cartItems"))
  : [];

const userFromStorage = localStorage.getItem("auth")
  ? JSON.parse(localStorage.getItem("auth"))
  : null; // Changed from [] to null

let decodedUser = null;
if (userFromStorage && userFromStorage.token) {
  const decoded = parseJwt(userFromStorage.token);
  decodedUser = {
    ...userFromStorage,
    userId: decoded?.userId,
    email: decoded?.email,
    // Add any other properties from the decoded token
  };
}

const initialState = {
  auth: { user: decodedUser },
  carts: { cart: cartItems },
  // Don't need to initialize other slices - their reducers handle it
};

// Create the store with explicit DevTools configuration
const store = configureStore({
  reducer: {
    products: productsReducer,
    error: errorReducer,
    carts: cartReducer,
    auth: authReducer,
  },
  preloadedState: initialState,
});

export default store;
