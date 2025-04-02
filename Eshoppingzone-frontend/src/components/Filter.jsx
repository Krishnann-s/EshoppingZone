import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Tooltip,
} from "@mui/material";
import { useEffect, useState } from "react";
import { FiArrowDown, FiArrowUp, FiRefreshCcw, FiSearch } from "react-icons/fi";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";

function Filter() {
  const categories = [
    { categoryId: 1, categoryName: "Electronics" },
    { categoryId: 2, categoryName: "Clothing" },
    { categoryId: 3, categoryName: "Home Appliances" },
    { categoryId: 4, categoryName: "Books" },
    { categoryId: 5, categoryName: "Sports" },
  ];

  const [searchParams] = useSearchParams();
  const pathName = useLocation().pathname;
  const params = new URLSearchParams(searchParams);
  const navigate = useNavigate();

  const [category, setCategory] = useState("all");
  const [sortOrder, setSortOrder] = useState("asc");
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const currentCategory = searchParams.get("category") || "all";
    const currentSortOrder = searchParams.get("sortOrder") || "asc";
    const currentSearchTerm = searchParams.get("keyword") || "";

    setCategory(currentCategory);
    setSortOrder(currentSortOrder);
    setSearchTerm(currentSearchTerm);
  }, [searchParams]);

  useEffect(() => {
    const handler = setTimeout(() => {
      if (searchTerm) {
        searchParams.set("keyword", searchTerm);
      } else {
        searchParams.delete("keyword");
      }
      navigate(`${pathName}?${searchParams.toString()}`);
    }, 500);

    return () => {
      clearTimeout(handler);
    };
  }, [searchParams, searchTerm, navigate, pathName]);

  const handleCategoryChange = (e) => {
    const selectedCategory = e.target.value;

    if (selectedCategory === "all") {
      params.delete("category");
    } else {
      params.set("category", selectedCategory);
    }
    navigate(`${pathName}?${params}`);
    setCategory(e.target.value);
  };
  const toggleSortOrder = () => {
    setSortOrder((prev) => {
      const newOrder = prev === "asc" ? "desc" : "asc";
      params.set("sortOrder", newOrder);
      navigate(`${pathName}?${params}`);
      return newOrder;
    });
  };
  const handleClearFilter = () => {
    navigate({ pathName: window.location.pathname });
  };

  return (
    <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4 mb-8">
      {/* Search Bar */}
      <div className="relative flex items-center 2xl:w-[450px] sm:w[420px] w-full">
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="Search Products"
          className="border border-gray-300 text-slate-950 rounded-md py-2 pl-10 pr-4 w-full focus:outline-none focus:ring-1 focus:ring-amber-600"
        />
        <FiSearch className="absolute left-3 text-amber-600 size={20}" />
      </div>
      {/* Category Filter */}
      <div className="flex sm:flex-row flex-col gap-4 items-center">
        <FormControl
          variant="outlined"
          size="small"
          className="text-amber-600 border border-amber-600"
        >
          <InputLabel Id="category-select-label">Category</InputLabel>
          <Select
            labelId="category-select-label"
            value={category}
            onChange={handleCategoryChange}
            label="Category"
            className="min-w-[120px] text-slate-900 border-amber-600"
          >
            <MenuItem value="all">All</MenuItem>
            {categories.map((item) => (
              <MenuItem key={item.categoryId} value={item.categoryName}>
                {item.categoryName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        {/* Sort Button  & Clear Filter*/}
        <Tooltip
          title={`Sorted by price: ${
            sortOrder === "asc" ? "ascending" : "descending"
          }`}
        >
          <Button
            onClick={toggleSortOrder}
            variant="contained"
            className="flex items-center gap-2 h-10 bg-amber-600"
          >
            Sort by
            {sortOrder === "asc" ? (
              <FiArrowUp size={17} className="text-white" />
            ) : (
              <FiArrowDown size={17} className="text-white" />
            )}
          </Button>
        </Tooltip>
        <button
          className="flex items-center gap-2 bg-rose-700 text-white px-3 py-2 rounded-sm transition duration-300 ease-in showdow-md focus:outline-none hover:backdrop-blur-2xl hover:cursor-pointer"
          onClick={handleClearFilter}
        >
          <FiRefreshCcw className="font-semibold" size={16} />
          <span className="font-semibold">Clear Filter</span>
        </button>
      </div>
    </div>
  );
}

export default Filter;
