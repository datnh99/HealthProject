import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";

import Dashboard from "@/pages/Dashboard.vue";
import Login from "@/pages/Login.vue";
import ClassManagement from "@/pages/ClassManagement.vue";
import UserManagement from "@/pages/UserManagement.vue";
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
// import { preloaderFinished } from "./config/preloader";

// import { includes } from "lodash";

// import VueScrollTo from "vue-scrollto";

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
        path: "class-management",
        name: "ClassManagement",
        component: ClassManagement
      },
      {
        path: "user-management",
        name: "UserManagement",
        component: UserManagement
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
    // scrollBehavior: to => {
    //   if (to.hash) {
    //     return { selector: to.hash };
    //   } else {
    //     return { x: 0, y: 0 };
    //   }
    // },
  }
];

// const notAuthRoutes = ["login", "Forbidden", "NotFound"];
// router.afterEach(to => {
//   preloaderFinished();

//   // return
//   if (to) {
//     const token = Vue.$cookies.isKey("accessToken");
//     if (!includes(notAuthRoutes, to.name) && !token) {
//       // return window.location.href = `${CONFIG.LOGIN_URL}${encodeURIComponent(window.location.href)}`;
//       return (window.location.href = `${CONFIG.LOGIN_URL}/#/login`);
//     } else if (to.name === "login" && token && to.params.shareId) {
//       return (window.location.href = `${
//         CONFIG.CLIENT_URL
//       }/#/article-detail/${window.atob(to.params.shareId)}`);

//       // return router.push('/article-detail'+to.params.shareId);
//     } else if (to.name === "login" && token) {
//       return router.push({ name: "landing" });
//     }

//     if (CONFIG["PERMISSION_SCREEN_MAP"][to.name]) {
//       const objectTypeCode = CONFIG["PERMISSION_SCREEN_MAP"][to.name];
//       if (!checkPermission(objectTypeCode, "VIEW")) {
//         return router.push({ name: "Forbidden" });
//       }
//     }
//   }
//   VueScrollTo.scrollTo("body");
// });

export default router;
