import { Link } from 'react-router-dom';
import { Product } from '../types';

interface ProductCardProps {
  product: Product;
}

export default function ProductCard({ product }: ProductCardProps) {
  return (
    <Link to={`/products/${product.id}`} className="group">
      <div className="bg-white rounded-lg shadow-sm overflow-hidden">
        <div className="aspect-w-1 aspect-h-1 w-full overflow-hidden">
          <img
            src={product.photo}
            alt={product.name}
            className="w-full h-full object-center object-cover group-hover:opacity-75"
          />
        </div>
        <div className="p-4">
          <h3 className="text-sm font-medium text-gray-900">{product.name}</h3>
          <p className="mt-1 text-lg font-medium text-gray-900">
            ${product.price.toFixed(2)}
          </p>
          <p className="mt-1 text-sm text-gray-500 line-clamp-2">
            {product.description}
          </p>
        </div>
      </div>
    </Link>
  );
}