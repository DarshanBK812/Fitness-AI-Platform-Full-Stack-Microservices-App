import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: { "Content-Type": "application/json" },
});

api.interceptors.request.use((config) => {
  const token  = localStorage.getItem("token");
  const userId = localStorage.getItem("userId");

  if (token)  config.headers.Authorization = `Bearer ${token}`;
  if (userId) config.headers["X-User-ID"] = userId;   // <— use X-User-ID (not userId)
  return config;
});
// helper
export const unwrap = (res) => res?.data?.data ?? res?.data ?? null;



export const addActivity     = (activity) => api.post("/activities", activity);
export const getActivities   = () => api.get("/activities");
export const getActivityRecommendation = (activityId) =>
  api.get(`/ai/activity/recommendation/${activityId}`);


export default api;
