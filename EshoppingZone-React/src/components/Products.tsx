import React from "react";
import Alert from "@mui/material/Alert";
import ProductCart from "./ProductCard";
import { AlertTitle } from "@mui/material";

export default function Products() {
  const isLoading = false;
  const errorMessage = "";

  const products = [
    {
      id: 1,
      title: "Product 1",
      price: 100,
      specialPrice: 90,
      description: "Product 1 Description",
      category: "Product 1 Category",
      image: "https://www.dummyimage.com/600x400/000/fff",
    },
    {
      id: 2,
      title: "Product 2",
      price: 200,
      specialPrice: 150,
      description: "Product 2 Description",
      category: "Product 2 Category",
      image: "https://www.dummyimage.com/600x400/000/fff",
    },
    {
      id: 3,
      title: "Product 3",
      price: 300,
      specialPrice: 250,
      description: "Product 3 Description",
      category: "Product 3 Category",
      image: "https://www.dummyimage.com/600x400/000/fff",
    },
  ];

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
