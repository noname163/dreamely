import config from "../config.json"
import http from "../service/httpService"

const apiEndpoint = config.apiEndpoint;

export async function register(user){
    return http.post(apiEndpoint+"/register",{
        phoneNumber:user.phoneNumber,
        fullName: user.fullName,
        email: user.email,
        password: user.password,
    })
}

export async function updateAccount(account){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorization: "Bearer "+accessToken
        }
    };
    return http.post("http://localhost:8080/admin/accounts/edit-information",{
        email:account.email,
        description:account.description,
        phoneNumber:account.phoneNumber,
        fullName:account.fullName
    },options)
}


export default {
    register,
    updateAccount
}