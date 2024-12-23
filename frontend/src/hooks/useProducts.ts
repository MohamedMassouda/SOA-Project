import { useQuery } from "@tanstack/react-query";
import api from "../lib/axios";
import { Product } from "../types";

export function useProducts(categoryId?: number, search?: string) {
  return useQuery({
    queryKey: ["products", { categoryId, search }],
    queryFn: async () => {
      const params = new URLSearchParams();
      if (categoryId) params.append("categoryId", categoryId.toString());
      if (search) params.append("search", search);

      console.log("params", params.toString());

      const { data } = await api.get<Product[]>(
        `/products?${params.toString()}`,
      );
      return data;
    },
  });
}

