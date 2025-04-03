import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import "./index.css";
import App from "./App.jsx";
import store from "./store/reducer/store.js";
import { ThemeProvider } from "@emotion/react";
import theme from "./theme.js";
import { CssBaseline } from "@mui/material";

createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <StrictMode>
      <ThemeProvider theme={theme}>
        <CssBaseline />

        <App />
      </ThemeProvider>
    </StrictMode>
  </Provider>
);
