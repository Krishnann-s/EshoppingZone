import React from "react";
import InputField from "../shared/InputField";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import Spinner from "../shared/Spinner";
import { FaAddressCard } from "react-icons/fa";
import { addUpdateUserAddress } from "../../store/action";

function AddAddressForm({ setOpenAddressModal }) {
  const btnLoader = useSelector((state) => state.error);
  const dispatch = useDispatch();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({ mode: "onTouched" });

  const onSaveAddressHandler = async (data) => {
    await dispatch(addUpdateUserAddress(data)); // Dispatch the action to save the address
    reset(); // Reset the form after successful submission
    setOpenAddressModal(false); // Close the modal
  };

  return (
    <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form className="space-y-6" onSubmit={handleSubmit(onSaveAddressHandler)}>
        <div className="flex items-center justify-center mb-4 font-semibold text-2xl text-slate-800">
          <FaAddressCard size={30} className="mr-2 text-2xl" />
          <h1>Add New Address</h1>
        </div>
        <div className="flex flex-col gap-4">
          <InputField
            label="Street"
            required={true}
            message="Street is required"
            id="street"
            type="text"
            register={register}
            errors={errors}
            className="block text-md font-semibold leading-6 text-gray-900"
            placeHolder="Enter street address"
          />
          <InputField
            label="City"
            required={true}
            message="*City is required"
            id="city"
            type="text"
            register={register}
            errors={errors}
            placeHolder="Enter your City"
          />
          <InputField
            label="State"
            required={true}
            message="*state is required"
            id="state"
            type="text"
            register={register}
            errors={errors}
            placeHolder="Enter your State"
          />
          <InputField
            label="Country"
            required={true}
            message="*Country is required"
            id="country"
            type="text"
            register={register}
            errors={errors}
            placeHolder="Enter your Country"
          />
          <InputField
            label="Pincode"
            required={true}
            message="*Pincode is required"
            id="pincode"
            type="text"
            register={register}
            errors={errors}
            placeHolder="Enter your Pincode"
          />
        </div>

        <div>
          <button
            disabled={btnLoader}
            type="submit"
            className="flex w-full justify-center rounded-md bg-amber-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-amber-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          >
            {btnLoader ? (
              <>
                <Spinner /> Loading...
              </>
            ) : (
              <>Add Address</>
            )}
          </button>
        </div>
      </form>
    </div>
  );
}

export default AddAddressForm;
