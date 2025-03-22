// import axios from "axios";
import { product_api } from "../../api/api";

export const fetchProducts = () => async (dispatch) => {
  try {
    const { data } = await product_api.get("/public/products?sortBy=productId");
    dispatch({
      type: "FETCH_PRODUCTS",
      payload: data.content,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });
  } catch (error) {
    console.log(error);
  }
};
