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
        customBackground: "#FFF3E2",
        banner: {
          color1: "#3D3A36",
          color2: "#6E81E7",
          color3: "#B973FF",
          color4: "#F6F8D5",
          color5: "#ff0404",
          color6: "#ff5c5c",
          color7: "#FFF5E4",
        },
      },
      backgroundImage: {
        "custom-gradient": "linear-gradient(to right, #111827, #1f2937)",
        "button-gradient": "linear-gradient(to right, #ff0404, #ff5c5c)",
        "custom-gradient2": "linear-gradient(135deg, #FFF5E4, #F6F0F0)",
      },
    },
  },
  plugins: [],
};
