import config from "../config.json";
import http from "../service/httpService";
import jwtDecode from "jwt-decode";
import { toast } from "react-toastify";

const apiEndpoint = config.apiEndpoint;
const accessToken = "Access-Token";
const refreshToken = "Refresh-Token";

export async function login(user) {
    const { data: jwt } = await http.post(apiEndpoint + "/login", {
        email: user.email,
        password: user.password,
    });
    localStorage.setItem(accessToken, jwt.accessToken);
    localStorage.setItem(refreshToken, jwt.refeshToken);
}

export function logout() {
    localStorage.removeItem(accessToken);
}

export function getCurrentUser() {
    try {
        const token = localStorage.getItem(accessToken);
        const user = jwtDecode(token);
        const currentTime = Date.now() / 1000;
        if (user.exp < currentTime) {
            toast.warning("Your session has expired. Please log in again.");
            logout();
            return null;
        }
        return user;
    } catch (error) {
        return null;
    }
}

export function getUserInformation() {
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorization: "Bearer "+accessToken
        }
    };
    try {
        const user = jwtDecode(accessToken);
        const email = user.sub;
        return http.get("http://localhost:8080/admin/accounts/get-information/" + email, options);
    } catch (error) {
        return null;
    }
}


export default {
    login,
    logout,
    getCurrentUser,
    getUserInformation
};
