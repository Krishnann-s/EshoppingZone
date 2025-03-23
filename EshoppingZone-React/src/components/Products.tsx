import React, { useEffect } from "react";
import Alert from "@mui/material/Alert";
import ProductCart from "./ProductCard";
import { AlertTitle } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../store/action";

export default function Products() {
  const { products, loading, error } = useSelector((state) => state.products);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
      {loading ? (
        <div className="flex justify-center mt-14">
          <p>Loading products...</p>
        </div>
      ) : error ? (
        <div className="justify-center flex mt-14">
          <Alert severity="error">
            <AlertTitle>Error</AlertTitle>
            {error}
          </Alert>
        </div>
      ) : (
        <div className="min-h-[700px]">
          {products && products.length > 0 ? (
            <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
              {products.map((item, i) => (
                <ProductCart key={i} {...item} />
              ))}
            </div>
          ) : (
            <div className="justify-center flex mt-14">
              <Alert severity="info">
                <AlertTitle>No Products</AlertTitle>
                No products available at this time.
              </Alert>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
