import axios from "axios";

export const product_api = axios.create({
  baseURL: `${
    import.meta.env.VITE_BACK_END_URL
  }/eshoppingzone/product-service/api`,
});

export const cart_api = axios.create({
  baseURL: `${
    import.meta.env.VITE_BACK_END_URL
  }/eshoppingzone/cart-service/api`,
});

export const profile_api = axios.create({
  baseURL: `${
    import.meta.env.VITE_BACK_END_URL
  }/eshoppinzone/profile-service/api`,
});

export const image_api = axios.create({
  baseURL: `${
    import.meta.env.VITE_BACK_END_URL
  }/eshoppinzone/image-service/api`,
});
