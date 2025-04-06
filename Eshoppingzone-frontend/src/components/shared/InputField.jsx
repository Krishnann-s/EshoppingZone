function InputField(
  label,
  id,
  type,
  errors,
  register,
  required,
  message,
  className,
  min,
  value,
  placeHolder
) {
  return (
    <div className="flex flex-col gap-1 w-full">
      <label
        htmlFor={id}
        className={`${
          className ? className : ""
        } font-semibold text-md text-slate-700`}
      >
        {label}
      </label>
      <input
        type={type}
        id={id}
        placeholder={placeHolder}
        className={`${
          className ? className : ""
        } px-2 py-2 border outline-none bg-transparent text-amber-800 rounded-xl ${
          errors[id]?.message ? "border-red-500" : "border-slate-300"
        } `}
        {...register(id, {
          required: { value: required, message },
          minLength: min
            ? { value: min, message: `Minimum length is ${min}` }
            : null,
          pattern:
            type === "email"
              ? {
                  value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                  message: "Invalid email address",
                }
              : type === "url"
              ? {
                  value: /^(https?:\/\/)?([^\s@]+\.)+[^\s@]+(\/[^\s@]*)*$/,
                  message: "Invalid URL",
                }
              : null,
        })}
      />
      {errors[id]?.message && (
        <p className="text-sm font-semibold text-red-600 mt-0">
          {errors[id]?.message}
        </p>
      )}
    </div>
  );
}

export default InputField;
