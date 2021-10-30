import axios from "axios";
const API_LOCATION = `https://provinces.open-api.vn/api`;
const config = {
  Authorization: null,
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Methods": "POST, GET, OPTIONS",
  "Content-Type": "application/json",
  "X-Requested-With": "XMLHttpRequest",
};

function getAllProvince() {
  return axios.get(`${API_LOCATION}/p/`, config);
}

function getDistrictInProvince(provinceCode) {
  return axios.get(`${API_LOCATION}/p/${provinceCode}?depth=2`, config);
}

function getWardInDistrict(districtCode) {
  return axios.get(`${API_LOCATION}/w/${districtCode}?depth=2`, config);
}

export default {
  getAllProvince,
  getDistrictInProvince,
  getWardInDistrict,
};
