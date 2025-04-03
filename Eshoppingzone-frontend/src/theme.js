import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      main: "#EA7300",
    },
    secondary: {
      main: "#c0efff",
    },
    error: {
      main: "#f44336",
    },
    success: {
      main: "#4caf50",
    },
    warning: {
      main: "#ff9800",
    },
    info: {
      main: "#2196f3",
    },
    background: {
      default: "#FFFBEB",
      paper: "#FFFBEB",
    },
    text: {
      primary: "#000000",
      secondary: "#757575",
      disabled: "#bdbdbd",
    },
    action: {
      active: "#000000",
      hover: "#f5f5f5",
      selected: "#e0e0e0",
      disabled: "#bdbdbd",
      disabledBackground: "#e0e0e0",
    },
  },
});

export default theme;
