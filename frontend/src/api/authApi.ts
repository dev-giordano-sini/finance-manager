import api from "./axios";

export function login(data: LoginRequest) {
    return api.post("/auth/login", data);
}

export function register(data: RegisterRequest) {
    return api.post("/auth/register", data);
}