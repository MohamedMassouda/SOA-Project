export interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
  photo: string;
  quantity: number;
  category_name: string;
}

export interface Category {
  id: number;
  name: string;
}

export interface AuthResponse {
  token: string;
}

export interface ErrorResponse {
  message: string;
}

export interface CartItem {
  id: number;
  product_id: number;
  product_name: string;
  product_photo: string;
  price: number;
  quantity: number;
  subtotal: number;
}

export interface Cart {
  id: number;
  client_id: number;
  items: CartItem[];
  total_price: number;
}

export interface CartContextType {
  cart: Cart | null;
  loading: boolean;
  addToCart: (productId: number, quantity: number) => Promise<void>;
  removeFromCart: (itemId: number) => Promise<void>;
  updateQuantity: (itemId: number, quantity: number) => Promise<void>;
}

export interface CheckoutRequest {
  address: string;
}

export interface OrderItem {
  id: number;
  product_id: number;
  product_name: string;
  product_photo: string;
  price_at_time: number;
  quantity: number;
  subtotal: number;
}

export interface Order {
  id: number;
  client_id: number;
  items: OrderItem[];
  total_amount: number;
  address: string;
  order_date: string;
  status: "pending" | "confirmed" | "shipped" | "delivered" | "cancelled";
}