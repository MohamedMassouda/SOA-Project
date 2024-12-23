import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useProducts } from '../hooks/useProducts';
import ProductCard from '../components/ProductCard';
import CategoryFilter from '../components/CategoryFilter';

export default function HomePage() {
  const [searchParams] = useSearchParams();
  const [selectedCategory, setSelectedCategory] = useState<number>();
  
  const search = searchParams.get('search') || undefined;
  const { data: products, isLoading } = useProducts(selectedCategory, search);

  return (
    <div className="flex gap-8">
      <aside className="w-64 flex-shrink-0">
        <CategoryFilter
          selectedCategory={selectedCategory}
          onSelectCategory={setSelectedCategory}
        />
      </aside>

      <div className="flex-1">
        {isLoading ? (
          <div>Loading products...</div>
        ) : products?.length === 0 ? (
          <div>No products found</div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {products?.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}