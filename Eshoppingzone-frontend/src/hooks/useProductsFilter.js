// useProductsFilter.js
import { useEffect, useRef } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchProducts } from "../store/action";

const useProductsFilter = () => {
  const dispatch = useDispatch();
  const [searchParams] = useSearchParams();
  const previousQueryRef = useRef("");

  useEffect(() => {
    // Get all search parameters
    const page = searchParams.get("page") || "1";
    const category = searchParams.get("category") || "";
    const keyword = searchParams.get("keyword") || "";
    const sortOrder = searchParams.get("sortOrder") || "asc";

    // Build query string with all parameters
    const queryParams = new URLSearchParams();

    // Convert page to pageNumber (0-based for backend)
    queryParams.set("pageNumber", String(parseInt(page) - 1));

    // Always set pageSize to 4 for products
    queryParams.set("pageSize", "4");

    // Add other parameters
    if (category) queryParams.set("category", category);
    if (keyword) queryParams.set("keyword", keyword);
    if (sortOrder) queryParams.set("sortOrder", sortOrder);

    // Add sortBy parameter (required by backend)
    queryParams.set("sortBy", "price");

    const queryString = queryParams.toString();

    // Only fetch if the query has changed
    if (queryString !== previousQueryRef.current) {
      previousQueryRef.current = queryString;
      dispatch(fetchProducts(queryString));
    }
  }, [searchParams, dispatch]);
};

export default useProductsFilter;
