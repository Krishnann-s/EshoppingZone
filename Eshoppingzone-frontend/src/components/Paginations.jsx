import { Pagination } from "@mui/material";

const Paginations = () => {
  return (
    <Pagination
      count={30}
      defaultPage={1}
      siblingCount={2}
      boundaryCount={1}
      color="primary"
      size="medium"
    />
  );
};

export default Paginations;
