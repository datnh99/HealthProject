const config = {
  apiUrl: 'http://localhost:8181',
  CLIENT_URL :'http://localhost:8080',
  SCREEN_CODE : {
    ROLE_MANAGEMENT : 'ROLE_MANAGEMENT',
    ADMIN_ACTION : 'ADMIN_ACTION',
    SYSTEM_CONFIG: 'SYSTEM_CONFIG'
  },
  PERMISSION_SCREEN_MAP : {
    'UserRoleManagerment' : 'ROLE_MANAGEMENT',
  },
  LOGIN_URL : 'http://localhost:8080/authenticate',
  DISPLAY_SCREEN_MAP:{
    ADMIN_ACTION: "super_admin,teacher",
    ROLE_MANAGEMENT: "super_admin,teacher",
    SYSTEM_CONFIG: "super_admin"
  }
};
export default config
