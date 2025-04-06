function InputField({
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
  placeHolder,
}) {
  return (
    <div>
      <label htmlFor={id} className={className}>
        {label}
      </label>
      <div className="mt-2">
        <input
          type={type}
          id={id}
          placeholder={placeHolder}
          className={`block w-full rounded-md bg-white px-3 py-1.5 text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-amber-600 sm:text-sm/6 ${
            errors[id]?.message ? "outline-red-500" : "outline-gray-300"
          }`}
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
          <p className="mt-1 text-sm text-red-600">{errors[id]?.message}</p>
        )}
      </div>
    </div>
  );
}

export default InputField;
