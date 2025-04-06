import { Badge } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import { FaCartShopping } from "react-icons/fa6";
import { useState, useEffect, Fragment } from "react";
import Hamburger from "hamburger-react";
import { useSelector } from "react-redux";

function NavBar() {
  const path = useLocation().pathname;
  const [navBarOpen, setNavBarOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Toggle this based on user authentication state
  const [profileMenuOpen, setProfileMenuOpen] = useState(false);
  const { cart } = useSelector((state) => state.carts);

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

  // Close profile menu when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (profileMenuOpen && !event.target.closest(".profile-menu-container")) {
        setProfileMenuOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [profileMenuOpen]);

  // Toggle for demo purposes - remove in production
  const toggleLogin = () => {
    setIsLoggedIn(!isLoggedIn);
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

          {/* Conditional rendering based on login status */}
          {isLoggedIn ? (
            <li className="ml-4 relative profile-menu-container">
              <button
                onClick={() => setProfileMenuOpen(!profileMenuOpen)}
                className="flex rounded-full focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-amber-600"
                aria-expanded={profileMenuOpen}
                aria-haspopup="true"
              >
                <span className="sr-only">Open user menu</span>
                <img
                  className="h-10 w-10 rounded-full object-cover border-2 border-white"
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                  alt="User profile"
                />
              </button>

              {/* Profile dropdown menu */}
              {profileMenuOpen && (
                <div className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                  <Link
                    to="/profile"
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    onClick={() => setProfileMenuOpen(false)}
                  >
                    Your Profile
                  </Link>
                  <Link
                    to="/settings"
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    onClick={() => setProfileMenuOpen(false)}
                  >
                    Settings
                  </Link>
                  <button
                    onClick={() => {
                      setIsLoggedIn(false);
                      setProfileMenuOpen(false);
                    }}
                    className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                  >
                    Sign out
                  </button>
                </div>
              )}
            </li>
          ) : (
            <li className="ml-4">
              <Link
                className="flex items-center space-x-2 px-5 py-2 text-amber-800 rounded-full font-semibold bg-white hover:bg-amber-50 transition-all duration-300 shadow-sm"
                to="/login"
                onClick={toggleLogin} // For demo only - remove in production
              >
                <span>Login</span>
              </Link>
            </li>
          )}
        </ul>

        {/* Mobile hamburger button */}
        <button
          onClick={() => setNavBarOpen(!navBarOpen)}
          className="md:hidden flex items-center"
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

      {/* Mobile Navigation */}
      <div
        className={`fixed left-0 right-0 top-[70px] bg-amber-500 md:hidden transition-all duration-300 ease-in-out shadow-lg ${
          navBarOpen
            ? "max-h-[500px] opacity-100"
            : "max-h-0 opacity-0 pointer-events-none"
        } overflow-hidden z-40`}
      >
        <ul className="container mx-auto py-4 px-6 flex flex-col space-y-4">
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
          <li className="font-medium">
            <Link
              className={`flex items-center py-2 px-3 rounded-lg ${
                path === "/cart"
                  ? "bg-amber-400 text-white"
                  : "text-white hover:bg-amber-400/30"
              }`}
              to="/cart"
            >
              Cart
              <Badge
                showZero
                badgeContent={cart?.length || 0}
                color="error"
                overlap="circular"
                className="ml-2"
              />
            </Link>
          </li>

          {/* Mobile login/profile section */}
          {isLoggedIn ? (
            <>
              <li className="pt-2 flex items-center">
                <img
                  className="h-10 w-10 rounded-full object-cover border-2 border-white mr-4"
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                  alt="User profile"
                />
                <span className="text-white font-medium">John Doe</span>
              </li>
              <li>
                <Link
                  to="/profile"
                  className="block py-2 px-3 rounded-lg text-white hover:bg-amber-400/30"
                >
                  Your Profile
                </Link>
              </li>
              <li>
                <Link
                  to="/settings"
                  className="block py-2 px-3 rounded-lg text-white hover:bg-amber-400/30"
                >
                  Settings
                </Link>
              </li>
              <li>
                <button
                  onClick={() => setIsLoggedIn(false)}
                  className="block w-full text-left py-2 px-3 rounded-lg text-white hover:bg-amber-400/30"
                >
                  Sign out
                </button>
              </li>
            </>
          ) : (
            <li className="pt-2">
              <Link
                className="block w-full text-center py-3 text-amber-800 rounded-lg font-semibold bg-white hover:bg-amber-50 transition-all duration-300"
                to="/login"
                onClick={toggleLogin} // For demo only - remove in production
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
