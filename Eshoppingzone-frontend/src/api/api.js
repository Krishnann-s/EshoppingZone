import axios from "axios";

export const product_api = axios.create({
  baseURL: `${import.meta.env.VITE_BACK_END_URL}/product-service/api`,
});

export const cart_api = axios.create({
  baseURL: `${import.meta.env.VITE_BACK_END_URL}/cart-service/api`,
});

export const profile_api = axios.create({
  baseURL: `${import.meta.env.VITE_BACK_END_URL}/profile-service/api`,
});
