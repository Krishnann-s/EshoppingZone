import { Alert, Skeleton } from "@mui/material";
import { FaAddressBook } from "react-icons/fa";

function AddressInfo() {
  const noAddress = true;
  const isLoading = false;
  return (
    <div className="pt-4">
      {noAddress ? (
        <div className="items-center flex flex-col justify-center">
          <FaAddressBook
            size={50}
            className="flex flex-row text-gray-700 mb-8"
          />
          <Alert variant="filled" severity="error" className="font-bold">
            No Address available
          </Alert>
          <p className="text-gray-600 mt-4">
            Please add an Address to proceed.
          </p>
        </div>
      ) : (
        <div className="relative p-6 rounded-lg max-w-md mx-auto">
          <h1 className="text-slate-800 font-bold text-center text-2xl">
            Select Address
          </h1>
          {isLoading ? (
            <Skeleton variant="text" sx={{ fontSize: "1rem" }} />
          ) : (
            <div className="pt-6 space-y-6 ">Address List</div>
          )}
        </div>
      )}
    </div>
  );
}

export default AddressInfo;
