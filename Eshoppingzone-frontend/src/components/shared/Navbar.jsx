// src/components/Navbar.jsx
import { Badge } from "@mui/material";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { FaCartShopping } from "react-icons/fa6";
import { useState, useEffect } from "react";
import Hamburger from "hamburger-react";
import { useSelector, useDispatch } from "react-redux";
import Avatar from "@mui/material/Avatar";
import Person from "@mui/icons-material/Person";
import Settings from "@mui/icons-material/Settings";
import Logout from "@mui/icons-material/Logout";
import UserMenu from "../UserMenu";

function NavBar() {
  const path = useLocation().pathname;
  const [navBarOpen, setNavBarOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // Get cart and user from Redux state
  const { cart } = useSelector((state) => state.carts);
  const { user } = useSelector((state) => state.auth);

  // Handle scroll effect
  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 20) {
        setScrolled(true);
      } else {
        setScrolled(false);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  // Close mobile menu when route changes
  useEffect(() => {
    setNavBarOpen(false);
  }, [path]);

  // Handle logout
  const handleLogout = () => {
    dispatch({ type: "LOG_OUT" });
    localStorage.removeItem("auth");
    navigate("/login");
  };

  // Get first letter of email for avatar
  const getInitial = () => {
    if (user && user.email) {
      return user.email.charAt(0).toUpperCase();
    }
    return user?.role?.charAt(0).toUpperCase() || "U";
  };

  return (
    <div
      className={`h-[70px] ${
        scrolled ? "bg-amber-600 shadow-md" : "bg-amber-500"
      } 
      text-white flex items-center sticky top-0 z-50 transition-all duration-300`}
    >
      <div className="container mx-auto lg:px-8 px-4 w-full flex justify-between items-center">
        <Link to="/" className="flex items-center text-2xl font-bold">
          <img
            src="/ecommerce.png"
            className="h-[36px] w-[36px] mr-3"
            alt="Logo"
          />
          <span className="text-2xl font-[Poppins] tracking-tight">
            EShoppingZone
          </span>
        </Link>

        {/* Desktop Navigation */}
        <ul className="hidden md:flex items-center space-x-8">
          <li className="font-medium transition-all duration-150">
            <Link
              className={`py-2 px-1 border-b-2 ${
                path === "/"
                  ? "border-white text-white"
                  : "border-transparent text-gray-100 hover:border-amber-200"
              } transition-all duration-200`}
              to="/"
            >
              Home
            </Link>
          </li>
          <li className="font-medium transition-all duration-150">
            <Link
              className={`py-2 px-1 border-b-2 ${
                path === "/products"
                  ? "border-white text-white"
                  : "border-transparent text-gray-100 hover:border-amber-200"
              } transition-all duration-200`}
              to="/products"
            >
              Products
            </Link>
          </li>
          <li className="font-medium transition-all duration-150">
            <Link
              className={`py-2 px-1 border-b-2 ${
                path === "/about"
                  ? "border-white text-white"
                  : "border-transparent text-gray-100 hover:border-amber-200"
              } transition-all duration-200`}
              to="/about"
            >
              About
            </Link>
          </li>
          <li className="font-medium transition-all duration-150">
            <Link
              className={`py-2 px-1 border-b-2 ${
                path === "/contact"
                  ? "border-white text-white"
                  : "border-transparent text-gray-100 hover:border-amber-200"
              } transition-all duration-200`}
              to="/contact"
            >
              Contact
            </Link>
          </li>
          <li className="ml-4 flex items-center">
            <Link
              className={`relative p-2 rounded-full hover:bg-amber-400 transition-all duration-200 ${
                path === "/cart" ? "bg-amber-400" : ""
              }`}
              to="/cart"
              aria-label="Shopping Cart"
            >
              <Badge
                showZero
                badgeContent={cart?.length || 0}
                color="error"
                overlap="circular"
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
              >
                <FaCartShopping size={24} />
              </Badge>
            </Link>
          </li>
          {user && user.userId ? (
            <li className="ml-4 relative profile-menu-container">
              <UserMenu />
            </li>
          ) : (
            <li className="ml-4">
              <Link
                className="flex items-center space-x-2 px-5 py-2 text-amber-800 rounded-full font-semibold bg-white hover:bg-amber-50 transition-all duration-300 shadow-sm"
                to="/login"
              >
                <span>Login</span>
              </Link>
            </li>
          )}
        </ul>

        {/* Mobile hamburger button */}
        <div className="md:hidden flex items-center space-x-4">
          {/* Add cart icon for mobile */}
          <Link
            to="/cart"
            className="relative p-2 rounded-full hover:bg-amber-400 transition-all duration-200"
            aria-label="Shopping Cart"
          >
            <Badge
              showZero
              badgeContent={cart?.length || 0}
              color="error"
              overlap="circular"
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
            >
              <FaCartShopping size={20} />
            </Badge>
          </Link>

          <button
            onClick={() => setNavBarOpen(!navBarOpen)}
            className="flex items-center"
            aria-label="Toggle menu"
          >
            <Hamburger
              toggled={navBarOpen}
              toggle={setNavBarOpen}
              size={20}
              color="#fff"
            />
          </button>
        </div>
      </div>

      {/* Mobile Navigation */}
      <div
        className={`fixed left-0 right-0 top-[70px] bg-amber-500 md:hidden transition-all duration-300 ease-in-out shadow-lg ${
          navBarOpen
            ? "max-h-[500px] opacity-100"
            : "max-h-0 opacity-0 pointer-events-none"
        } overflow-hidden z-40`}
      >
        <ul className="container mx-auto py-4 px-6 flex flex-col space-y-4">
          {/* User profile section at the top for mobile */}
          {user && user.userId && (
            <li className="pt-2 pb-4 border-b border-amber-400">
              <div className="flex items-center">
                <Avatar
                  sx={{
                    width: 40,
                    height: 40,
                    bgcolor: "#f59e0b",
                    color: "white",
                    border: "2px solid white",
                    boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
                  }}
                >
                  {getInitial()}
                </Avatar>
                <div className="ml-3">
                  <div className="text-white font-medium">
                    {user.email?.split("@")[0] || user.role}
                  </div>
                  <div className="text-amber-100 text-sm">{user.email}</div>
                </div>
              </div>
            </li>
          )}

          <li className="font-medium">
            <Link
              className={`block py-2 px-3 rounded-lg ${
                path === "/"
                  ? "bg-amber-400 text-white"
                  : "text-white hover:bg-amber-400/30"
              }`}
              to="/"
            >
              Home
            </Link>
          </li>
          <li className="font-medium">
            <Link
              className={`block py-2 px-3 rounded-lg ${
                path === "/products"
                  ? "bg-amber-400 text-white"
                  : "text-white hover:bg-amber-400/30"
              }`}
              to="/products"
            >
              Products
            </Link>
          </li>
          <li className="font-medium">
            <Link
              className={`block py-2 px-3 rounded-lg ${
                path === "/about"
                  ? "bg-amber-400 text-white"
                  : "text-white hover:bg-amber-400/30"
              }`}
              to="/about"
            >
              About
            </Link>
          </li>
          <li className="font-medium">
            <Link
              className={`block py-2 px-3 rounded-lg ${
                path === "/contact"
                  ? "bg-amber-400 text-white"
                  : "text-white hover:bg-amber-400/30"
              }`}
              to="/contact"
            >
              Contact
            </Link>
          </li>

          {/* User account options for mobile */}
          {user && user.userId ? (
            <>
              <li className="font-medium">
                <Link
                  to="/profile"
                  className="flex items-center py-2 px-3 rounded-lg text-white hover:bg-amber-400/30"
                >
                  <Person className="mr-2" fontSize="small" />
                  Your Profile
                </Link>
              </li>
              <li className="font-medium">
                <Link
                  to="/settings"
                  className="flex items-center py-2 px-3 rounded-lg text-white hover:bg-amber-400/30"
                >
                  <Settings className="mr-2" fontSize="small" />
                  Settings
                </Link>
              </li>
              <li className="font-medium">
                <button
                  onClick={handleLogout}
                  className="flex items-center w-full text-left py-2 px-3 rounded-lg text-white hover:bg-amber-400/30"
                >
                  <Logout className="mr-2" fontSize="small" />
                  Sign out
                </button>
              </li>
            </>
          ) : (
            <li className="pt-2">
              <Link
                className="block w-full text-center py-3 text-amber-800 rounded-lg font-semibold bg-white hover:bg-amber-50 transition-all duration-300"
                to="/login"
              >
                Login
              </Link>
            </li>
          )}
        </ul>
      </div>
    </div>
  );
}

export default NavBar;
