import axios from "axios";

const api = axios.create({
  baseURL: `${import.meta.env.BACK_END_URL}/api`,
});

export default api;
