const initialState = {
  cart: [],
  totalPrice: 0,
  cartId: null,
};

export const cartReducer = (state = initialState, action) => {
  switch (action.type) {
    case "ADD_CART": {
      const productToAdd = action.payload;

      // Check if the product already exists in the cart
      const existingProduct = state.cart.find(
        (item) => item.productId === productToAdd.productId
      );

      if (existingProduct) {
        // If the product exists, update its quantity
        const updatedCart = state.cart.map((item) => {
          if (item.productId === productToAdd.productId) {
            return {
              ...item,
              quantity: item.quantity + productToAdd.quantity, // Increment quantity
            };
          }
          return item;
        });

        return {
          ...state,
          cart: updatedCart,
        };
      } else {
        // If the product doesn't exist, add it to the cart
        const newCart = [...state.cart, { ...productToAdd }];
        return {
          ...state,
          cart: newCart,
        };
      }
    }

    case "REMOVE_CART":
      return {
        ...state,
        cart: state.cart.filter(
          (item) => item.productId !== action.payload.productId
        ),
      };
    case "GET_USER_CART_PRODUCTS":
      return {
        ...state,
        cart: action.payload,
        totalPrice: action.totalPrice,
        cartId: action.cartId,
      };
    case "CLEAR_CART":
      return { cart: [], totalPrice: 0, cartId: null };
    default:
      return state;
  }
};
