import { configureStore } from "@reduxjs/toolkit";
import { productsReducer } from "./productsReducer";
import { errorReducer } from "./errorReducer";
import { cartReducer } from "./cartReducer";

const cartItems = localStorage.getItem("cartItems")
  ? JSON.parse(localStorage.getItem("cartItems"))
  : [];

const initialState = {
  carts: { cart: cartItems },
};

// Create the store
const store = configureStore({
  reducer: {
    products: productsReducer,
    error: errorReducer,
    carts: cartReducer,
  },
  preloadedState: initialState,
});

export default store;
