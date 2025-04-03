import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchProducts } from "../store/action";

const useProductsFilter = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();

  useEffect(() => {
    const params = new URLSearchParams(searchParams);

    const currectPage = searchParams.get("page")
      ? Number(searchParams.get("page"))
      : 1;

    params.set("pageNumber", currectPage - 1);
    params.set("pageSize", "5");

    const sortOrder = searchParams.get("sortOrder") || "asc";
    const categoryParams = searchParams.get("category") || null;
    const keyword = searchParams.get("keyword") || null;
    params.set("sortBy", "price");
    params.set("sortOrder", sortOrder);

    if (categoryParams) {
      params.set("category", categoryParams);
    }
    if (keyword) {
      params.set("keyword", keyword);
    }

    const queryString = params.toString();
    dispatch(fetchProducts(queryString));
  }, [dispatch, searchParams]);
};

export default useProductsFilter;
