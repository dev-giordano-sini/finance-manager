import api from "./axios";

export function findAllCategories() {
    return api.get("/categories");
}

export function createCategory(category: CreateCategoryRequest) {
    return api.post("/categories", category);
}