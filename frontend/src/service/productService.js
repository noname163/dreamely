import http from "../service/httpService";

let accessToken = localStorage.getItem("Access-Token");
const options = {
    headers: {
        Authorization: "Bearer "+accessToken
    }
};

export async function addNewProduct(product){
    return http.post("http://localhost:8080/admin/product",{
        name: product.name,
        price:product.price,
        categories: product.categories,
        description: product.des,
        images:product.images,
        image:product.image
    },options)
}
export async function updateProduct(product){
    return http.put("http://localhost:8080/admin/product/update",{
        id:product.id,
        name: product.name,
        price:product.price,
        categories: product.categories,
        description: product.description,
        image:product.image,
        images:product.images
    },options)
}
export async function newCategory(category){
    const options = {
        headers: {
            Authorization: "Bearer "+accessToken
        }
    };
    return http.post("http://localhost:8080/admin/categories",{
        name: category.name,
        description: category.description
    },options)
}

export async function deleteProduct(id){
    const options = {
        headers: {
            Authorization: "Bearer "+accessToken
        }
    };
    return http.delete("http://localhost:8080/admin/product/"+id,options)
}


export default{
    addNewProduct,updateProduct, newCategory
}