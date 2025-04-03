import { createSelector } from "reselect";

// Base selectors
const getProductsState = (state) => state.products;

// Memoized selectors
export const getProducts = createSelector(
  [getProductsState],
  (productsState) => productsState.products || []
);

export const getCategories = createSelector(
  [getProductsState],
  (productsState) => productsState.categories || []
);

export const getIsLoading = createSelector(
  [(state) => state.error],
  (error) => error.isLoading
);

export const getErrorMessage = createSelector(
  [(state) => state.error],
  (error) => error.errorMessage
);

export const getPagination = createSelector(
  [getProductsState],
  (productsState) => productsState.pagination || {}
);
