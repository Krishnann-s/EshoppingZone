import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./productReducer";
import { RootState } from "../../entity/types";

export const store = configureStore({
  reducer: { products: productReducer },
  preloadedState: {} as RootState,
});

export type AppDispatch = typeof store.dispatch;
export default store;
