const initialState = {
  products: null,
  categories: [],
  pagination: {
    pageNumber: 0,
    pageSize: 4,
    totalElements: 0,
    totalPages: 1,
    lastPage: true,
  },
  categoriesPagination: {
    pageNumber: 0,
    pageSize: 50,
    totalElements: 0,
    totalPages: 1,
    lastPage: true,
  },
};

export const productsReducer = (state = initialState, action) => {
  switch (action.type) {
    case "FETCH_PRODUCTS":
      return {
        ...state,
        products: action.payload,
        pagination: {
          pageNumber: action.pageNumber,
          pageSize: action.pageSize,
          totalElements: action.totalElements,
          totalPages: action.totalPages,
          lastPage: action.lastPage,
        },
      };

    case "FETCH_CATEGORIES":
      return {
        ...state,
        categories: action.payload,
        categoriesPagination: {
          // Store categories pagination separately
          pageNumber: action.pageNumber,
          pageSize: action.pageSize,
          totalElements: action.totalElements,
          totalPages: action.totalPages,
          lastPage: action.lastPage,
        },
      };

    default:
      return state;
  }
};
