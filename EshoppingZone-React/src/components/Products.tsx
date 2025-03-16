import React, { useEffect } from "react";
import Alert from "@mui/material/Alert";
import ProductCart from "./ProductCard";
import { AlertTitle } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../store/action";

export default function Products() {
  const isLoading = false;
  const errorMessage = "";
  const { products } = useSelector((state) => state.products);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto bg-custom-background">
      {isLoading ? (
        <p>It's Loading</p>
      ) : errorMessage ? (
        <div className="justify-center flex mt-14">
          <Alert severity="error">
            <AlertTitle>Error</AlertTitle>
            {errorMessage}
          </Alert>
        </div>
      ) : (
        <div className="min-h-[700px]">
          <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
            {products &&
              products.map((item, i) => <ProductCart key={i} {...item} />)}
          </div>
        </div>
      )}
    </div>
  );
}
