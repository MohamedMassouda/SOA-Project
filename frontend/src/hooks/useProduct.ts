import { useQuery } from '@tanstack/react-query';
import api from '../lib/axios';
import { Product } from '../types';

export function useProduct(id: number) {
  return useQuery({
    queryKey: ['product', id],
    queryFn: async () => {
      const { data } = await api.get<Product>(`/products/${id}`);
      return data;
    },
  });
}