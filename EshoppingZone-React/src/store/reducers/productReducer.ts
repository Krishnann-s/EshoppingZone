import { ProductAction, ProductState } from "../../entity/types";

const initialState: ProductState = {
  products: null,
  categories: null,
  loading: false,
  error: null,
  pagination: {},
};

export const productReducer = (state = initialState, action: ProductAction) => {
  switch (action.type) {
    case "FETCH_PRODUCTS_REQUEST":
      return {
        ...state,
        loading: true,
        error: null,
      };
    case "FETCH_PRODUCTS":
      return {
        ...state,
        loading: false,
        error: null,
        products: action.payload,
        pagination: {
          ...state.pagination,
          pageNumber: action.pageNumber,
          pageSize: action.pageSize,
          totalElements: action.totalElements,
          totalPages: action.totalPages,
          lastPage: action.lastPage,
        },
      };
    case "FETCH_PRODUCTS_ERROR":
      return {
        ...state,
        loading: false,
        error: action.payload,
      };
    default:
      return state;
  }
};
