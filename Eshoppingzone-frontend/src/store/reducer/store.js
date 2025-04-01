import { configureStore } from "@reduxjs/toolkit";
import { errorReducer } from "./errorReducer";
import { productsReducer } from "./productsReducer";

export const store = configureStore({
  reducer: { productsReducer, errorReducer },
  preloadedState: {},
});

export default store;
