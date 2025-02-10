import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import GoogleLogo from "./Logo/GoogleLogo";
import GithubLogo from "./Logo/GithubLogo";

function Register() {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [mobile, setMobile] = useState("");
  const [gender, setGender] = useState("");
  const [dob, setDob] = useState("");
  const [role, setRole] = useState("user");
  const [address, setAddress] = useState([
    { address: "", city: "", state: "", country: "", pincode: "" },
  ]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleAddressChange = (index, event) => {
    const { name, value } = event.target;

    setAddress((prevAddresses) =>
      prevAddresses.map((addr, i) =>
        i === index ? { ...addr, [name]: value } : addr
      )
    );
  };

  const addAddress = () => {
    const lastAddress = address[address.length - 1];
    if (
      !lastAddress.address.trim() ||
      !lastAddress.city.trim() ||
      !lastAddress.state.trim() ||
      !lastAddress.country.trim() ||
      !lastAddress.pincode.trim()
    ) {
      alert("Please fill in the current address before adding another.");
      return;
    }

    setAddress([
      ...address,
      { address: "", city: "", state: "", country: "", pincode: "" },
    ]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formattedDob = new Date(dob).toISOString().split("T")[0];

    const userProfile = {
      fullName,
      email,
      password,
      mobileNumber: mobile,
      gender,
      dob: formattedDob,
      role,
      address: address,
    };

    try {
      await axios.post(
        "http://localhost:8000/profile-service/eshoppingzone/profile/register",
        userProfile
      );
      navigate("/login");
    } catch (err) {
      if (err.response && err.response.status === 409) {
        setError("User already registered. Please login.");
        setTimeout(() => {
          navigate("/login");
        }, 2000);
      } else {
        console.error(err);
      }
    }
  };

  return (
    <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-2xl">
        <h2 className="mt-7 text-center text-2xl font-bold tracking-tight text-gray-900">
          Create an account
        </h2>
      </div>
      <div className="mt-5 sm:mx-auto sm:w-full sm:max-w-2xl">
        <form className="grid grid-cols-2 gap-8" onSubmit={handleSubmit}>
          {/* Input Fields */}
          {[
            {
              label: "Full Name",
              id: "full-name",
              type: "text",
              value: fullName,
              setter: setFullName,
            },
            {
              label: "Email Address",
              id: "email",
              type: "email",
              value: email,
              setter: setEmail,
            },
            {
              label: "Password",
              id: "password",
              type: "password",
              value: password,
              setter: setPassword,
            },
            {
              label: "Mobile Number",
              id: "mobile",
              type: "tel",
              value: mobile,
              setter: setMobile,
            },
            {
              label: "Date of Birth",
              id: "dob",
              type: "date",
              value: dob,
              setter: setDob,
            },
          ].map(({ label, id, type, value, setter }) => (
            <div key={id} className="col-span-1">
              <label
                htmlFor={id}
                className="block text-sm font-medium text-gray-900"
              >
                {label}
              </label>
              <input
                id={id}
                name={id}
                type={type}
                value={value}
                onChange={(e) => setter(e.target.value)}
                placeholder={label}
                required
                className="mt-3 block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:outline-indigo-600 sm:text-sm"
              />
            </div>
          ))}

          {/* Gender */}
          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-900">
              Gender
            </label>
            <div className="mt-3 flex space-x-4">
              {["male", "female", "other"].map((g) => (
                <div key={g} className="flex items-center">
                  <input
                    id={g}
                    type="radio"
                    value={g}
                    name="gender"
                    checked={gender === g}
                    onChange={() => setGender(g)}
                    className="w-4 h-4 text-indigo-600 bg-gray-100 border-gray-300 focus:ring-indigo-500"
                  />
                  <label
                    htmlFor={g}
                    className="ms-2 text-sm font-medium text-gray-900 capitalize"
                  >
                    {g}
                  </label>
                </div>
              ))}
            </div>
          </div>

          {/* Role */}
          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-900">
              Role
            </label>
            <div className="mt-3 flex space-x-4">
              {["user", "admin"].map((r) => (
                <div key={r} className="flex items-center">
                  <input
                    id={r}
                    type="radio"
                    value={r}
                    name="role"
                    checked={role === r}
                    onChange={() => setRole(r)}
                    className="w-4 h-4 text-indigo-600 bg-gray-100 border-gray-300 focus:ring-indigo-500"
                  />
                  <label
                    htmlFor={r}
                    className="ms-2 text-sm font-medium text-gray-900 capitalize"
                  >
                    {r}
                  </label>
                </div>
              ))}
            </div>
          </div>

          {/* Address Fields */}
          <div className="col-span-2">
            <label className="block text-sm font-medium text-gray-900">
              Address
            </label>
            {address.map((addr, index) => (
              <div key={index} className="mt-3 grid grid-cols-2 gap-4">
                {["address", "city", "state", "country", "pincode"].map(
                  (field) => (
                    <input
                      key={field}
                      name={field}
                      type="text"
                      value={addr[field]}
                      onChange={(e) => handleAddressChange(index, e)}
                      placeholder={
                        field.charAt(0).toUpperCase() + field.slice(1)
                      }
                      required
                      className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:outline-indigo-600 sm:text-sm"
                    />
                  )
                )}
              </div>
            ))}

            {/* Button to add another address */}
            <button
              type="button"
              onClick={addAddress}
              className="mt-3 text-indigo-600 hover:text-indigo-500 text-sm font-medium"
            >
              + Add Another Address
            </button>
          </div>

          {/* Submit Button */}
          <div className="col-span-2 flex justify-center">
            <button
              type="submit"
              className="w-full max-w-xs text-white bg-indigo-600 hover:bg-indigo-700 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
            >
              Create an account
            </button>
          </div>
        </form>
        {error && <div className="mt-3 text-center text-red-500">{error}</div>}
        <div className="mt-3 text-center">
          <p className="text-sm text-gray-500">
            Already have an account?{" "}
            <Link
              to="/login"
              className="font-semibold text-indigo-600 hover:text-indigo-500"
            >
              Login here
            </Link>
          </p>
        </div>

        <div className="mt-6">
          <div className="relative">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-gray-300" />
            </div>
            <div className="relative flex justify-center text-sm">
              <span className="bg-white px-2 text-gray-500">
                Or continue with
              </span>
            </div>
          </div>
        </div>
        <div className="mt-6 grid grid-cols-2 gap-3">
          <GoogleLogo />
          <GithubLogo />
        </div>
      </div>
    </div>
  );
}

export default Register;
