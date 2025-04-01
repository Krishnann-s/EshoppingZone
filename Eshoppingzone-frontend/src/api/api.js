import axios from "axios";

const product_api = axios.create({
  baseURL: `${import.meta.env.VITE_BACK_END_URL}/product-service/api`,
});

export { product_api };
