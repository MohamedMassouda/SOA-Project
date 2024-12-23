import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useProduct } from '../hooks/useProduct';
import { useCart } from '../hooks/useCart';
import { useAuthStore } from '../store/authStore';

export default function ProductPage() {
  const { id } = useParams();
  const { isAuthenticated } = useAuthStore();
  const { data: product, isLoading } = useProduct(Number(id));
  const { addToCart } = useCart();
  const [quantity, setQuantity] = useState(1);

  if (isLoading || !product) {
    return <div>Loading...</div>;
  }

  return (
    <div className="grid md:grid-cols-2 gap-8">
      <div>
        <img
          src={product.photo}
          alt={product.name}
          className="w-full rounded-lg"
        />
      </div>
      
      <div className="space-y-4">
        <h1 className="text-3xl font-bold text-gray-900">{product.name}</h1>
        <p className="text-2xl font-bold text-gray-900">
          ${product.price.toFixed(2)}
        </p>
        <p className="text-gray-600">{product.description}</p>
        
        {isAuthenticated && (
          <div className="space-y-4">
            <div className="flex items-center gap-4">
              <label htmlFor="quantity" className="text-gray-700">
                Quantity:
              </label>
              <input
                type="number"
                id="quantity"
                min="1"
                max={product.quantity}
                value={quantity}
                onChange={(e) => setQuantity(Number(e.target.value))}
                className="w-20 input"
              />
            </div>
            
            <button
              onClick={() => addToCart.mutate({ productId: product.id, quantity })}
              disabled={addToCart.isPending}
              className="btn btn-primary w-full"
            >
              Add to Cart
            </button>
          </div>
        )}
      </div>
    </div>
  );
}