import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./AuthSlice";

export const Store = configureStore({
  reducer: {
    auth: authReducer,
  },
});
