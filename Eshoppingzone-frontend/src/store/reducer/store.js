import { configureStore } from "@reduxjs/toolkit";
import { errorReducer } from "./errorReducer";
import { productsReducer } from "./productsReducer";
import { cartReducer } from "./cartReducer";

const cartItems = localStorage.getItem("cartItems")
  ? JSON.parse(localStorage.getItem("cartItems"))
  : [];

const initialState = { carts: { cart: cartItems } };

export const store = configureStore({
  reducer: {
    products: productsReducer,
    error: errorReducer,
    carts: cartReducer,
  },
  preloadedState: initialState,
});

export default store;
