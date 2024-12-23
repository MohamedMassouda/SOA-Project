import { create } from "zustand";
import api from "../lib/axios";
import { AuthResponse, ErrorResponse } from "../types";

interface AuthState {
  token: string | null;
  isAuthenticated: boolean;
  isAdmin: boolean;
  login: (username: string, password: string) => Promise<void>;
  signup: (username: string, email: string, password: string) => Promise<void>;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  token: localStorage.getItem("token"),
  isAuthenticated: !!localStorage.getItem("token"),
  isAdmin: false,

  login: async (username: string, password: string) => {
    try {
      const response = await api.post<AuthResponse>("/auth/login", {
        username,
        password,
      });
      localStorage.setItem("token", response.data.token);
      set({ token: response.data.token, isAuthenticated: true });
    } catch (error) {
      const err = error as { response: { data: ErrorResponse } };
      throw new Error(err.response.data.message);
    }
  },

  signup: async (username: string, email: string, password: string) => {
    try {
      const response = await api.post<AuthResponse>("/auth/signup", {
        username,
        email,
        password,
      });
      localStorage.setItem("token", response.data.token);
      set({ token: response.data.token, isAuthenticated: true });
    } catch (error) {
      const err = error as { response: { data: ErrorResponse } };
      throw new Error(err.response.data.message);
    }
  },

  logout: () => {
    localStorage.removeItem("token");
    set({ token: null, isAuthenticated: false, isAdmin: false });
  },
}));
