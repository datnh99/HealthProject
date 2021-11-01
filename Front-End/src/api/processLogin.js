import axios from "axios";
import CONFIG from "../config/index";
import Vue from "vue";
const API_LOGIN = `${CONFIG.apiUrl}`;

export function checkPermission(objectTypeCode, view) {
  if (objectTypeCode) {
    const accessRole = CONFIG.DISPLAY_SCREEN_MAP[objectTypeCode];
    const userRole = Vue.$cookies.get("role");
    if (userRole && accessRole.includes(userRole)) {
      return true;
    }
  }
  return false;
}

export async function processGoogleToken(userLogin) {
  let result = "";
  try {
    let res = await axios.post(`${API_LOGIN}/authenticate`, userLogin);
    console.log('userData ====>', res);
    let userData = res.data.data;
    if(!res.data.success){
      userData = false
    }
    if (userData) {
      // const expiredTime = userData.expiredTime;
      // Vue.$cookies.set("accessToken", userData.token, expiredTime);
      // Vue.$cookies.set("account", userData.account, expiredTime);
      //  Vue.$cookies.set("roleCode", userData.roleCode, expiredTime);
      // Vue.$cookies.set("google.token", userLogin.idToken, expiredTime);
      // axios.defaults.headers.common["Authorization"] =
      //   "Bearer " + userData.token;
      userData.idToken = userLogin.idToken;
      return (result = userData);
    }
  } catch (e) {
    console.error(e);
  }
  return result;
}
export async function verifyToken(token) {
  try {
    let res = await axios.get(
      `${API_LOGIN}/verifyToken/{verify-token}?token=${token}`
    );
    return res.data.success === true;
  } catch (e) {
    return false;
  }
}

export async function checkToken() {
  let check = Vue.$cookies.get("accessToken");
  if (check) {
    let accessToken = Vue.$cookies.get("accessToken");
    // check = await verifyToken(accessToken);
    return true;
  }
  return false;
}

export async function checkLogin(next, objectTypeCode) {
  let check = await checkToken();
  if (check) {
    if (objectTypeCode) {
      check = checkPermission(objectTypeCode, "VIEW");
      if (!check) {
        next("/forbidden");
      } else {
        next();
      }
    } else {
      next();
    }
  } else {
    next("/login");
  }
}
export async function processLogout() {
  let res = await axios.post(
    `${API_LOGIN}/processLogout`
  );
  if(res.data.success){
    Vue.$cookies.remove("accessToken");
    Vue.$cookies.remove("account");
    Vue.$cookies.remove("roleCode");
    Vue.$cookies.remove("displayName");
    Vue.$cookies.remove("dateOfBirth");
    Vue.$cookies.remove("avatar");
  }
}
export async function basicProcessLogins(username, password) {
  const formData = JSON.stringify({
    username: username,
    password: password
  })
  let res = await axios.post( `${API_LOGIN}/api/login`, formData, {
    headers: {
      // Overwrite Axios's automatically set Content-Type
      'Content-Type': 'application/json'
    }
  })
  return res.data;
}
