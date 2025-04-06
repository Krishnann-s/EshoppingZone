import { parseJwt } from "../../utils/jwtDecoder";

const initialState = {
  user: null,
  address: [],
};

export const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case "LOGIN_USER": {
      // Extract token from payload
      const { token, role, message } = action.payload;

      // Decode the token to get user info
      let decodedUser = null;
      if (token) {
        const decoded = parseJwt(token);
        decodedUser = {
          token,
          role,
          message,
          userId: decoded?.userId,
          email: decoded?.email,
          // Add any other properties from the decoded token
        };
      }
      return {
        ...state,
        user: decodedUser,
      };
    }
    case "LOG_OUT":
      return {
        user: null,
        address: null,
      };
    default:
      return state;
  }
};
