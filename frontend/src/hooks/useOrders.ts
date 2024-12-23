import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { toast } from 'react-hot-toast';
import api from '../lib/axios';
import { Order, CheckoutRequest } from '../types';

export function useOrders() {
  const queryClient = useQueryClient();

  const { data: orders, isLoading } = useQuery({
    queryKey: ['orders'],
    queryFn: async () => {
      const { data } = await api.get<Order[]>('/orders');
      return data;
    },
  });

  const checkout = useMutation({
    mutationFn: async (checkoutData: CheckoutRequest) => {
      await api.post('/orders/checkout', checkoutData);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] });
      queryClient.invalidateQueries({ queryKey: ['cart'] });
      toast.success('Order placed successfully');
    },
    onError: () => {
      toast.error('Failed to place order');
    },
  });

  return {
    orders,
    isLoading,
    checkout,
  };
}