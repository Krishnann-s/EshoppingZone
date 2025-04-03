// Paginations.jsx
import { Pagination } from "@mui/material";
import { useSearchParams, useNavigate, useLocation } from "react-router-dom";

const Paginations = ({ numberOfPage, totalProducts }) => {
  const [searchParams] = useSearchParams();
  const location = useLocation();
  const navigate = useNavigate();

  // Get current page, default to 1
  const paramValue = searchParams.get("page")
    ? Number(searchParams.get("page"))
    : 1;

  const onChangeHandler = (event, value) => {
    const newParams = new URLSearchParams(searchParams.toString());
    newParams.set("page", value.toString());
    navigate(`${location.pathname}?${newParams.toString()}`);
  };

  return (
    <Pagination
      count={numberOfPage || 1}
      page={paramValue}
      defaultPage={1}
      siblingCount={1}
      boundaryCount={2}
      color="primary"
      size="medium"
      onChange={onChangeHandler}
    />
  );
};

export default Paginations;
