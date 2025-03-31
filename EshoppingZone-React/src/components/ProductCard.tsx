// Project: e-commerce-frontend
import { useState } from "react";
import { FaCartShopping } from "react-icons/fa6";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import ProductViewModel from "./ProductViewModal";
import { Product } from "../entity/types";

const ProductCard = ({
  productName,
  price,
  description,
  category,
  imageUrl,
  specialPrice,
}: Product) => {
  const [openProductViewModal, setOpenProductViewModal] = useState(false);
  const [selectedViewProduct, setSelectedViewProduct] =
    useState<Product | null>(null);
  const [loading, setLoading] = useState(false);

  const handleClick = () => {
    setLoading(true);
    // Simulate a network request
    setTimeout(() => {
      setLoading(false);
    }, 2000);
  };

  const handleProductView = (products: Product) => {
    console.log("Product being passed to modal:", products);
    setSelectedViewProduct(products);
    setOpenProductViewModal(true);
  };

  return (
    <div className="rounded-lg shadow-xl overflow-hidden transition-shadow duration-300 bg-white">
      <div
        onClick={() => {
          handleProductView({
            productName,
            price,
            description,
            category,
            imageUrl,
            specialPrice, // Use imageUrl here
          });
        }}
        className="w-full overflow-hidden aspect-[3/2]"
      >
        <img
          className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
          src={`${
            import.meta.env.VITE_BACK_END_URL
          }/image-service/api/${imageUrl.split("/").pop()}`}
          alt={productName}
        />
      </div>
      <div className="p-4">
        <h2
          className="text-lg mb-2 font-semibold cursor-pointer"
          onClick={() => {
            handleProductView({
              productName,
              price,
              description,
              category,
              imageUrl, // Use imageUrl here
              specialPrice,
            });
          }}
        >
          {productName}
        </h2>
        <div className="min-h-20 max-h-20">
          <p className="text-gray-600 text-sm">{description}</p>
        </div>

        <div className="flex items-center justify-between mt-4">
          {specialPrice ? (
            <div className="flex flex-col">
              <span className="text-banner-color1 line-through">
                ${Number(price).toFixed(2)}
              </span>
              <span className="font-bold text-banner-color1 text-xl">
                ${Number(specialPrice).toFixed(2)}
              </span>
            </div>
          ) : (
            <span className="font-bold text-banner-color1 text-xl">
              {"  "}${Number(price).toFixed(2)}
            </span>
          )}
          <Box sx={{ "& > button": { m: 1 } }}>
            <Button
              size="small"
              onClick={handleClick}
              loading={loading}
              loadingIndicator="Adding…"
              variant="contained"
              startIcon={<FaCartShopping />}
              sx={{
                my: 4,
                borderRadius: "2rem", // equivalent to rounded-4xl
                padding: "0.5rem 1rem", // equivalent to px-4 py-2
                backgroundColor: "#ff5c5c",
                "&:hover": {
                  backgroundColor: "#BE3144",
                },
              }}
            >
              Add to Cart
            </Button>
          </Box>
        </div>
      </div>
      <ProductViewModel
        open={openProductViewModal}
        setOpen={setOpenProductViewModal}
        product={
          selectedViewProduct || {
            productName: "",
            price: 0,
            description: "",
            category: "",
            imageUrl: "",
            specialPrice: undefined,
          }
        }
      />
    </div>
  );
};

export default ProductCard;
