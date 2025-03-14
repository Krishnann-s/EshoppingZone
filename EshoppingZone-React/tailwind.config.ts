/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        montserrat: ["Montserrat", "sans-serif"],
      },
      boxShadow: {
        custom: "0 0 15px rgba(0, 0, 0, 0.3)",
        right: "10px 0px 10px -5px rgba(0, 0, 0, 0.3)",
      },
      colors: {
        customBlue: "rgba(28, 100, 242, 1)",
        banner: {
          color1: "#205781",
          color2: "#4F959D",
          color3: "#98D2C0",
          color4: "#F6F8D5",
        },
      },
      backgroundImage: {
        "custom-gradient": "linear-gradient(to right, #111827, #1f2937)",
        "button-gradient": "linear-gradient(to right, #7e22ce, #ef4444)",
        "custom-gradient2": "linear-gradient(135deg, #FFF5E4, #F6F0F0)",
      },
    },
  },
  plugins: [],
};
