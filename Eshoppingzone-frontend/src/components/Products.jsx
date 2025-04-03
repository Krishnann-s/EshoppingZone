import { useDispatch, useSelector } from "react-redux";
import { fetchCategories } from "../store/action";
import { FaExclamationTriangle } from "react-icons/fa";
import ProductCard from "./ProductCard";
import Filter from "./Filter";
import useProductsFilter from "./useProductsFilter";
import { useEffect } from "react";
import {
  getCategories,
  getErrorMessage,
  getIsLoading,
  getPagination,
  getProducts,
} from "../store/selector";
import Loader from "./Loader";
import Paginations from "./Paginations";

export default function Products() {
  const products = useSelector(getProducts);
  const categories = useSelector(getCategories);
  const isLoading = useSelector(getIsLoading);
  const errorMessage = useSelector(getErrorMessage);
  const pagination = useSelector(getPagination);

  const dispatch = useDispatch();
  useProductsFilter();

  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);

  return (
    <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto bg-amber-50">
      <Filter categories={categories} />
      {isLoading ? (
        <Loader text={"Loading Products ..."} />
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
          <div className="flex justify-center pt-10">
            <Paginations
              numberOfPage={pagination?.totalPages}
              totalProducts={pagination?.totalElements}
            />
          </div>
        </div>
      )}
    </div>
  );
}
