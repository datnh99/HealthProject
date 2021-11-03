import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";

import Dashboard from "@/pages/Dashboard.vue";
import Login from "@/pages/Login.vue";
import ClassManagement from "@/pages/ClassManagement.vue";
import UserManagement from "@/pages/UserManagement.vue";
import TeacherManagement from "@/pages/TeacherManagement.vue";
import Icons from "@/pages/Icons.vue";
import Notifications from "@/pages/Notifications.vue";
import UserProfile from "@/pages/UserProfile.vue";
import TableList from "@/pages/TableList.vue";
import Typography from "@/pages/Typography.vue";
import NotFound from "@/pages/404.vue";
import Forbidden from "@/pages/403.vue";
import CONFIG from "./config/index";
import { checkLogin } from "./api/processLogin";
import Declaration from "@/pages/Declaration.vue";

const router = [
  {
    path: "/",
    component: DashboardLayout,
    redirect: "dashboard",
    children: [
      {
        path: "declare",
        name: "Declaration",
        component: Declaration
      },
      {
        path: "login",
        name: "Login",
        component: Login
      },
      {
        path: "dashboard",
        name: "Dashboard",
        component: Dashboard
      },
      {
        path: "forbidden",
        name: "Forbidden",
        component: Forbidden
      },
      {
        path: "class-management",
        name: "ClassManagement",
        component: ClassManagement,
        beforeEnter: async (to, from, next) => {
          await checkLogin(next, CONFIG.SCREEN_CODE.CLASS_MANAGEMENT);
        },
      },
      {
        path: "user-management",
        name: "UserManagement",
        component: UserManagement,
        beforeEnter: async (to, from, next) => {
          await checkLogin(next, CONFIG.SCREEN_CODE.USER_MANAGEMENT);
        },
      },
      {
        path: "teacher-management",
        name: "TeacherManagement",
        component: TeacherManagement
      },
      {
        path: "icons",
        name: "Icons",
        component: Icons
      },
      {
        path: "notifications",
        name: "Notifications",
        component: Notifications
      },
      {
        path: "user",
        name: "User Profile",
        component: UserProfile
      },
      {
        path: "table",
        name: "Table List",
        component: TableList
      },
      {
        path: "typography",
        name: "Typography",
        component: Typography
      },
      {
        path: ":catchAll(.*)",
        name: "NotFound",
        component: NotFound
      }
    ]
  }
];

export default router;
