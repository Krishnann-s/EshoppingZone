import { useDispatch, useSelector } from "react-redux";
import HeroBanner from "./HeroBanner";
import { useEffect } from "react";
import { fetchProducts } from "../../store/action";
import {
  getErrorMessage,
  getIsLoading,
  getProducts,
} from "../../store/selector";
import ProductCard from "../shared/ProductCard";
import Loader from "../shared/Loader";
import { FaExclamationTriangle } from "react-icons/fa";

const Home = () => {
  const dispatch = useDispatch();
  const isLoading = useSelector(getIsLoading);
  const errorMessage = useSelector(getErrorMessage);
  const products = useSelector(getProducts);

  useEffect(() => {
    dispatch(fetchProducts("sortBy=productId"));
  }, [dispatch]);

  return (
    <div className="lg:px-14 sm:px-8 px-4 py-4">
      <div className="py-6">
        <HeroBanner />
      </div>
      <div className="py-5">
        <div className="flex flex-col justify-center items-center space-y-2">
          <h1 className="text-3xl text-slate-900 font-bold text-center">
            Products
          </h1>
          <span className="text-slate-700">
            Discover our handpicked products for you!
          </span>
        </div>
        {isLoading ? (
          <Loader />
        ) : errorMessage ? (
          <div className="flex justify-center items-center h-[200px]">
            <FaExclamationTriangle className="text-slate-800 text-3xl mr-2" />
            <span className="text-slate-800 text-lg font-medium">
              {errorMessage}
            </span>
          </div>
        ) : (
          <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
            {products &&
              products
                ?.slice(0, 8)
                .map((item, i) => <ProductCard key={i} {...item} />)}
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
