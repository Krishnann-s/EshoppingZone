import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import InputField from "../shared/InputField";
import { useDispatch } from "react-redux";
import { authenticateSignInUser } from "../../store/action";
import { toast } from "react-toastify";
import Spinner from "../shared/Spinner";

function Login() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [loader, setLoader] = useState(false);
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({ mode: "onTouched" });

  const loginHandler = async (data) => {
    dispatch(authenticateSignInUser(data, toast, reset, navigate, setLoader));
  };

  return (
    <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-28 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
          Sign in to your account
        </h2>
      </div>
      <hr className="w-50 h-1 mx-auto bg-amber-200 border-0 rounded-md mt-2"></hr>

      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form className="space-y-6" onSubmit={handleSubmit(loginHandler)}>
          <div>
            <InputField
              label="Email address"
              required={true}
              message="Email is required"
              id="email"
              type="email"
              register={register}
              errors={errors}
              className="block text-md font-semibold leading-6 text-gray-900"
              placeHolder="Enter your email"
            />
          </div>

          <div>
            <div className="flex items-center justify-between mb-2">
              <label className="block text-md font-semibold leading-6 text-gray-900">
                Password
              </label>
              <div className="text-sm">
                <a
                  href="#"
                  className="font-semibold text-amber-600 hover:text-indigo-500"
                >
                  Forgot password?
                </a>
              </div>
            </div>
            <InputField
              required={true}
              message="Password is required"
              id="password"
              type="password"
              register={register}
              errors={errors}
              className="hidden" // Hide the default label since we're using custom label above
              placeHolder="Enter your password"
            />
          </div>

          <div>
            <button
              disabled={loader}
              type="submit"
              className="flex w-full justify-center rounded-md bg-amber-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-amber-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              {loader ? (
                <>
                  <Spinner /> Loading...
                </>
              ) : (
                <>Sign in</>
              )}
            </button>
          </div>
          <p className="text-center text-sm text-slate-700 mt-6">
            Don't have an account?
            <Link
              className="font-semibold text-amber-600 hover:text-amber-500"
              to="/signup"
            >
              {" "}
              <span>SignUp</span>
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}

export default Login;
