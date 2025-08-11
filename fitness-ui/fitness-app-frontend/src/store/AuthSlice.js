import { createSlice } from '@reduxjs/toolkit'

// --- safe localStorage helpers (no renames, just safer) ---
const sGet = (k) => {
  try { return typeof window !== 'undefined' ? localStorage.getItem(k) : null; } catch { return null; }
};
const sSet = (k, v) => {
  try { if (typeof window !== 'undefined') localStorage.setItem(k, v); } catch {}
};
const sRemove = (k) => {
  try { if (typeof window !== 'undefined') localStorage.removeItem(k); } catch {}
};
const sGetJSON = (k) => {
  const raw = sGet(k);
  if (!raw) return null;
  try { return JSON.parse(raw); } catch { sRemove(k); return null; }
};

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: sGetJSON('user'),
    token: sGet('token') || null,
    userId: sGet('userId') || null
  },
  reducers: {
    setCredential: (state, action) => {
      state.user = action.payload?.user ?? null;
      state.token = action.payload?.token ?? null;

      const sub = action.payload?.tokenData?.sub ?? action.payload?.userId ?? null;
      state.userId = sub;

      // persist safely
      if (state.token != null) sSet('token', state.token); else sRemove('token');
      sSet('user', JSON.stringify(state.user));
      if (sub != null) sSet('userId', String(sub)); else sRemove('userId');
    },

    logout: (state) => {
      state.user = null;
      state.token = null;
      state.userId = null;
      sRemove('user');
      sRemove('token');
      sRemove('userId');
    },
  },
});

export const { setCredential, logout } = authSlice.actions;
export default authSlice.reducer;
