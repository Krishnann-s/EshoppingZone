import { Pagination } from "@mui/material";
import { useSearchParams, useNavigate, useLocation } from "react-router-dom";

const Paginations = ({ numberOfPage, totalProducts }) => {
  const [searchParams] = useSearchParams();
  const pathName = useLocation();
  const params = new URLSearchParams(searchParams);
  const navigate = useNavigate();
  const paramValue = searchParams.get("page")
    ? Number(searchParams.get("page"))
    : 1;

  const onChangeHandler = (event, value) => {
    params.set("page", value);
    navigate(`${pathName.pathname}?${params}`);
  };

  return (
    <Pagination
      count={numberOfPage}
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
