import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import InputField from "../shared/InputField";

function Login() {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ mode: "onTouched" });

  const loginHandler = async (data) => {
    console.log("login clicked", data);
  };

  return (
    <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-14 text-center text-2xl/9 font-bold tracking-tight text-gray-900">
          Sign in to your account
        </h2>
      </div>
      <hr className="w-50 h-1 mx-auto bg-amber-200 border-0 rounded-md mt-2"></hr>
      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form className="space-y-6" onSubmit={handleSubmit(loginHandler)}>
          <div>
            <InputField
              label="UserName"
              required
              id="userName"
              type="text"
              register={register}
              errors={errors}
            />
          </div>
        </form>
      </div>
    </div>
  );
}

export default Login;
