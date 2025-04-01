// import axios from "axios";
import { Dispatch } from "@reduxjs/toolkit";
import { product_api } from "../../api/api";
import { ProductAction } from "../../entity/types";

export const fetchProducts =
  () => async (dispatch: Dispatch<ProductAction>) => {
    try {
      const { data } = await product_api.get("/public/products");
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
