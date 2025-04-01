import { useEffect } from "react";
import Alert from "@mui/material/Alert";
import { AlertTitle, Box, CircularProgress } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../store/action";
import { FaExclamationTriangle } from "react-icons/fa";

export default function Products() {
  const products = useSelector((state) => state.products.products);
  const isLoading = useSelector((state) => state.error.isLoading);
  const errorMessage = useSelector((state) => state.error.errorMessage);

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
      {isLoading ? (
        // Show CircularProgress when loading is true
        <div className="flex items-center justify-center min-h-screen bg-amber-50">
          <Box sx={{ display: "flex" }}>
            <CircularProgress size="6rem" />
          </Box>
        </div>
      ) : errorMessage ? (
        <div className="flex justify-center items-center h-[200px]">
          <FaExclamationTriangle className="text-slate-800 text-3xl mr-2" />
          <span className="text-slate-800 text-lg font-medium">
            {errorMessage}
          </span>
        </div>
      ) : (
        <div className="min-h-[700px]">
          <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
            {products &&
              products.map((item, i) => <ProductCard key={i} {...item} />)}
          </div>
          {/* <div className="flex justify-center pt-10">
            <Paginations
              numberOfPage={pagination?.totalPages}
              totalProducts={pagination?.totalElements}
            />
          </div> */}
        </div>
      )}
    </div>
  );
}
