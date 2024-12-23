import { useQuery } from '@tanstack/react-query';
import api from '../lib/axios';
import { Category } from '../types';

export function useCategories() {
  return useQuery({
    queryKey: ['categories'],
    queryFn: async () => {
      const { data } = await api.get<Category[]>('/categories');
      return data;
    },
  });
}