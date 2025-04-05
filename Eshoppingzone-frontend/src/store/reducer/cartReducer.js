const initialState = {
  cart: [],
  totalPrice: 0,
  cartId: null,
};

export const cartReducer = (state = initialState, action) => {
  switch (action.type) {
    case "ADD_TO_CART": {
      const productToAdd = action.payload;
      const existingProduct = state.cart.find(
        (item) => item.productId === productToAdd.productId
      );
      if (existingProduct) {
        const updatedCart = state.cart.map((item) => {
          if (item.productId === productToAdd.productId) {
            return productToAdd;
          } else {
            return item;
          }
        });
        return {
          ...state,
          cart: updatedCart,
        };
      } else {
        const newCart = [...state.cart, productToAdd];
        return {
          ...state,
          cart: newCart,
        };
      }
    }
    case "REMOVE_FROM_CART":
      return {
        ...state,
        cart: state.cart.filter((item) => item.id !== action.payload),
      };
    case "SET_CART_ID":
      return {
        ...state,
        cartId: action.payload,
      };
    default:
      return state;
  }
};
