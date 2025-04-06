import * as React from "react";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";
import Settings from "@mui/icons-material/Settings";
import Logout from "@mui/icons-material/Logout";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { logOutUser } from "../store/action";

export default function UserMenu() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    dispatch(logOutUser(navigate));
    handleClose();
  };

  const getInitial = () => {
    if (user && user.email) {
      return user.email.charAt(0).toUpperCase();
    }
    return user?.role?.charAt(0).toUpperCase() || "U";
  };

  return (
    <React.Fragment>
      <Box sx={{ display: "flex", alignItems: "center", textAlign: "center" }}>
        <Tooltip title={`Hello, ${user?.email?.split("@")[0] || "User"}`}>
          <IconButton
            onClick={handleClick}
            size="small"
            sx={{
              ml: 2,
              border: "2px solid white",
              bgcolor: "rgba(255, 255, 255, 0.2)",
              transition: "all 0.3s ease",
              "&:hover": {
                bgcolor: "rgba(255, 255, 255, 0.3)",
                transform: "scale(1.05)",
              },
            }}
            aria-controls={open ? "account-menu" : undefined}
            aria-haspopup="true"
            aria-expanded={open ? "true" : undefined}
          >
            <Avatar
              src={user?.profilePicture} // Use the profile picture URL
              sx={{
                width: 32,
                height: 32,
                bgcolor: "#f59e0b",
                color: "white",
                fontWeight: "bold",
                boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
              }}
            >
              {getInitial()}
            </Avatar>
          </IconButton>
        </Tooltip>
      </Box>
      <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={handleClose}
        onClick={handleClose}
        slotProps={{
          paper: {
            elevation: 0,
            sx: {
              overflow: "visible",
              filter: "drop-shadow(0px 2px 8px rgba(0,0,0,0.32))",
              mt: 1.5,
              borderRadius: "12px",
            },
          },
        }}
        transformOrigin={{ horizontal: "right", vertical: "top" }}
        anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      >
        <MenuItem component={Link} to="/profile">
          <Avatar src={user?.profilePicture} sx={{ bgcolor: "#f59e0b" }} />
          {user?.email?.split("@")[0] || "Profile"}
        </MenuItem>
        <Divider sx={{ my: 1 }} />
        <MenuItem component={Link} to="/settings">
          <Settings fontSize="small" />
          Settings
        </MenuItem>
        <MenuItem onClick={handleLogout}>
          <Logout fontSize="small" />
          Logout
        </MenuItem>
      </Menu>
    </React.Fragment>
  );
}
