import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import InputField from "../shared/InputField";
import GoogleLogo from "../../utils/GoogleLogo";
import GithubLogo from "../../utils/GithubLogo";
import { useDispatch } from "react-redux";
import { registerNewUser } from "../../store/action";
import { toast } from "react-toastify";
import Spinner from "../shared/Spinner";

export default function Register() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [loader, setLoader] = useState(false);
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({ mode: "onTouched" });

  const registerHandler = async (data) => {
    dispatch(registerNewUser(data, toast, reset, navigate, setLoader));
  };

  return (
    <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <h2 className="mt-4 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
          Create a new account
        </h2>
      </div>
      <hr className="w-50 h-1 mx-auto bg-amber-200 border-0 rounded-md mt-2" />

      {/* Social Sign Up Buttons */}
      <div className="mt-6 mb-4 flex justify-center space-x-4">
        <GoogleLogo />
        <GithubLogo />
      </div>

      <div className="flex justify-center items-center my-4">
        <hr className="w-50 h-1 bg-amber-200 border-0 rounded-md" />
        <span className="mx-4 text-gray-600">or</span>
        <hr className="w-50 h-1 bg-amber-200 border-0 rounded-md" />
      </div>

      <div className="mt-4 sm:mx-auto sm:w-full sm:max-w-lg border border-gray-300 shadow-lg rounded-lg p-6">
        <form className="space-y-4" onSubmit={handleSubmit(registerHandler)}>
          <div>
            <InputField
              label="Username"
              required={true}
              message="Username is required"
              id="userName"
              type="text"
              register={register}
              errors={errors}
              className="block w-full text-md font-semibold leading-6 text-gray-900"
              placeHolder="Enter your username"
            />
          </div>

          <div>
            <InputField
              label="Email address"
              required={true}
              message="Email is required"
              id="email"
              type="email"
              register={register}
              errors={errors}
              className="block w-full text-md font-semibold leading-6 text-gray-900"
              placeHolder="Enter your email"
            />
          </div>

          <div>
            <InputField
              label="Password"
              required={true}
              message="Password is required"
              id="password"
              type="password"
              register={register}
              errors={errors}
              placeHolder="Enter your password"
            />
          </div>

          <div>
            <InputField
              label="Mobile Number"
              required={true}
              message="Mobile number is required"
              id="mobileNumber"
              type="tel"
              register={register}
              errors={errors}
              className="block w-full text-md font-semibold leading-6 text-gray-900"
              placeHolder="Enter your mobile number"
            />
          </div>

          <div>
            <label className="block text-md font-semibold leading-6 text-gray-900">
              Date of Birth
            </label>
            <input
              type="date"
              {...register("dob", { required: true })}
              className="block w-full mt-2 p-2 border border-gray-300 rounded-md"
            />
            {errors.dob && (
              <span className="text-red-500">Date of birth is required</span>
            )}
          </div>

          <div>
            <label className="block text-md font-semibold leading-6 text-gray-900">
              Gender
            </label>
            <select
              {...register("gender", { required: true })}
              className="block w-full mt-2 p-2 border border-gray-300 rounded-md"
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
            {errors.gender && (
              <span className="text-red-500">Gender is required</span>
            )}
          </div>

          <div>
            <label className="block text-md font-semibold leading-6 text-gray-900">
              Role
            </label>
            <div className="flex items-center mt-2">
              <label className="mr-4">
                <input
                  type="radio"
                  value="User"
                  {...register("role", { required: true })}
                />
                user
              </label>
              <label>
                <input
                  type="radio"
                  value="Admin"
                  {...register("role", { required: true })}
                />
                admin
              </label>
            </div>
            {errors.role && (
              <span className="text-red-500">Role is required</span>
            )}
          </div>

          <div>
            <button
              disabled={loader}
              type="submit"
              className="flex w-full justify-center rounded-md bg-amber-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-amber-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              {loader ? (
                <>
                  {" "}
                  <Spinner /> Loading...
                </>
              ) : (
                <>Register</>
              )}
            </button>
          </div>
          <p className="text-center text-sm text-slate-700 mt-6">
            Already have an account?
            <Link
              className="font-semibold text-amber-600 hover:text-amber-500"
              to="/login"
            >
              {" "}
              <span>Login</span>
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}
