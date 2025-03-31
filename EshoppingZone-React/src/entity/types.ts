// types.ts
export interface Product {
  productName: string;
  price: number;
  description: string;
  category: string;
  imageUrl: string;
  specialPrice?: number;
}

export interface ProductState {
  products: Product[] | null;
  categories: string[] | null;
  loading: boolean;
  error: string | null;
  pagination: {
    pageNumber?: number;
    pageSize?: number;
    totalElements?: number;
    totalPages?: number;
    lastPage?: boolean;
  };
}

export interface RootState {
  products: ProductState;
}

export interface ProductAction {
  type: string;
  payload?: Product[] | string | null | ProductState;
  pageNumber?: number;
  pageSize?: number;
  totalElements?: number;
  totalPages?: number;
  lastPage?: boolean;
}
