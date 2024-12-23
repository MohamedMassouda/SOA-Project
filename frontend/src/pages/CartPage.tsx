import { useState } from "react";
import { Link } from "react-router-dom";
import {
  TrashIcon,
  MinusIcon,
  PlusIcon,
  ShoppingBagIcon,
} from "@heroicons/react/24/outline";
import { useCart } from "../hooks/useCart";
import { useOrders } from "../hooks/useOrders";

export default function CartPage() {
  const { cart, isLoading, updateQuantity, removeFromCart } = useCart();
  const { checkout } = useOrders();
  const [address, setAddress] = useState("");
  const [isCheckingOut, setIsCheckingOut] = useState(false);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (!cart?.items.length) {
    return (
      <div className="flex flex-col items-center justify-center h-64 space-y-4">
        <ShoppingBagIcon className="h-16 w-16 text-gray-400" />
        <p className="text-xl text-gray-600">Your cart is empty</p>
        <Link to="/" className="btn btn-primary">
          Continue Shopping
        </Link>
      </div>
    );
  }

  const handleQuantityChange = (
    itemId: number,
    currentQuantity: number,
    change: number,
  ) => {
    const newQuantity = Math.max(1, currentQuantity + change);
    updateQuantity.mutate({ itemId, quantity: newQuantity });
  };

  const handleCheckout = async () => {
    if (!address.trim()) {
      return;
    }
    await checkout.mutateAsync({ address });
  };

  return (
    <div className="max-w-4xl mx-auto space-y-8">
      <h1 className="text-2xl font-bold text-gray-900">Shopping Cart</h1>

      <div className="bg-white rounded-lg shadow-sm overflow-hidden">
        <div className="divide-y divide-gray-200">
          {cart.items.map((item) => (
            <div key={item.id} className="p-6 flex items-center gap-6">
              <div className="flex-shrink-0">
                <img
                  src={item.product_photo}
                  alt={item.product_name}
                  className="w-24 h-24 object-cover rounded-lg"
                />
              </div>

              <div className="flex-1 min-w-0">
                <h3 className="text-lg font-medium text-gray-900 truncate">
                  {item.product_name}
                </h3>
                <p className="mt-1 text-sm text-gray-500">
                  ${item.price.toFixed(2)} each
                </p>
              </div>

              <div className="flex items-center gap-4">
                <div className="flex items-center border border-gray-300 rounded-lg">
                  <button
                    onClick={() =>
                      handleQuantityChange(item.product_id, item.quantity, -1)
                    }
                    className="p-2 hover:bg-gray-100 transition-colors"
                    aria-label="Decrease quantity"
                  >
                    <MinusIcon className="h-4 w-4 text-gray-600" />
                  </button>
                  <span className="w-12 text-center font-medium">
                    {item.quantity}
                  </span>
                  <button
                    onClick={() =>
                      handleQuantityChange(item.product_id, item.quantity, 1)
                    }
                    className="p-2 hover:bg-gray-100 transition-colors"
                    aria-label="Increase quantity"
                  >
                    <PlusIcon className="h-4 w-4 text-gray-600" />
                  </button>
                </div>

                <button
                  onClick={() => removeFromCart.mutate(item.product_id)}
                  className="p-2 text-gray-400 hover:text-red-600 transition-colors"
                  aria-label="Remove item"
                >
                  <TrashIcon className="h-5 w-5" />
                </button>
              </div>

              <div className="flex-shrink-0 w-24 text-right">
                <p className="text-lg font-medium text-gray-900">
                  ${item.subtotal.toFixed(2)}
                </p>
              </div>
            </div>
          ))}
        </div>

        <div className="bg-gray-50 p-6">
          <div className="flex justify-between text-lg font-medium text-gray-900">
            <span>Total</span>
            <span>${cart.total_price.toFixed(2)}</span>
          </div>

          {isCheckingOut ? (
            <div className="mt-6 space-y-4">
              <input
                type="text"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                placeholder="Enter your shipping address"
                className="input"
              />
              <div className="flex gap-4">
                <button
                  onClick={handleCheckout}
                  disabled={checkout.isPending || !address.trim()}
                  className="btn btn-primary flex-1"
                >
                  {checkout.isPending ? "Processing..." : "Place Order"}
                </button>
                <button
                  onClick={() => setIsCheckingOut(false)}
                  className="btn btn-secondary"
                >
                  Cancel
                </button>
              </div>
            </div>
          ) : (
            <button
              onClick={() => setIsCheckingOut(true)}
              className="btn btn-primary w-full mt-6"
            >
              Proceed to Checkout
            </button>
          )}
        </div>
      </div>
    </div>
  );
}

