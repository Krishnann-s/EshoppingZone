/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        banner: {
          DEFAULT: "#E4EFE7",
          light: "#F0F7F3",
          dark: "#C5D6C9",
        },
      },
    },
  },
  plugins: [],
};
