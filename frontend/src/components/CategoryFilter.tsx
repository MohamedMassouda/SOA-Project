import { useCategories } from '../hooks/useCategories';

interface CategoryFilterProps {
  selectedCategory?: number;
  onSelectCategory: (categoryId?: number) => void;
}

export default function CategoryFilter({ selectedCategory, onSelectCategory }: CategoryFilterProps) {
  const { data: categories, isLoading } = useCategories();

  if (isLoading) return <div>Loading categories...</div>;

  return (
    <div className="space-y-4">
      <h2 className="text-lg font-medium text-gray-900">Categories</h2>
      <div className="space-y-2">
        <button
          onClick={() => onSelectCategory(undefined)}
          className={`block w-full text-left px-3 py-2 rounded-md ${
            !selectedCategory ? 'bg-blue-50 text-blue-700' : 'text-gray-600 hover:bg-gray-50'
          }`}
        >
          All Products
        </button>
        {categories?.map((category) => (
          <button
            key={category.id}
            onClick={() => onSelectCategory(category.id)}
            className={`block w-full text-left px-3 py-2 rounded-md ${
              selectedCategory === category.id
                ? 'bg-blue-50 text-blue-700'
                : 'text-gray-600 hover:bg-gray-50'
            }`}
          >
            {category.name}
          </button>
        ))}
      </div>
    </div>
  );
}