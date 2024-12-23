import { Link, Outlet } from "react-router-dom";
import { ShoppingCartIcon, UserIcon } from "@heroicons/react/24/outline";
import { useAuthStore } from "../store/authStore";
import SearchBar from "./SearchBar";
import CartIndicator from "./CartIndicator";

export default function Layout() {
  const { isAuthenticated, isAdmin, logout } = useAuthStore();

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center">
              <Link to="/" className="text-xl font-bold text-gray-900">
                EShop
              </Link>
              <div className="ml-10">
                <SearchBar />
              </div>
            </div>

            <nav className="flex items-center gap-6">
              {isAuthenticated ? (
                <>
                  <CartIndicator />
                  <Link
                    to="/orders"
                    className="text-gray-600 hover:text-gray-900"
                  >
                    Orders
                  </Link>
                  {isAdmin && (
                    <Link
                      to="/admin"
                      className="text-gray-600 hover:text-gray-900"
                    >
                      Admin
                    </Link>
                  )}
                  <button
                    onClick={logout}
                    className="text-gray-600 hover:text-gray-900"
                  >
                    Logout
                  </button>
                </>
              ) : (
                <>
                  <Link
                    to="/login"
                    className="text-gray-600 hover:text-gray-900"
                  >
                    <UserIcon className="h-6 w-6" />
                  </Link>
                </>
              )}
            </nav>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <Outlet />
      </main>
    </div>
  );
}

