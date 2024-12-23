import { Link } from "react-router-dom";
import { ShoppingCartIcon } from "@heroicons/react/24/outline";
import { useCart } from "../hooks/useCart";

export default function CartIndicator() {
  const { cart } = useCart();
  const itemCount =
    cart?.items.reduce((total, item) => total + item.quantity, 0) || 0;

  return (
    <Link to="/cart" className="relative text-gray-600 hover:text-gray-900">
      <ShoppingCartIcon className="h-6 w-6" />
      {itemCount > 0 && (
        <span className="absolute -top-2 -right-2 bg-blue-600 text-white text-xs font-bold rounded-full h-5 w-5 flex items-center justify-center">
          {itemCount > 99 ? "99+" : itemCount}
        </span>
      )}
    </Link>
  );
}
