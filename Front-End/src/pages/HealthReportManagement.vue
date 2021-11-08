<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <h4 class="card-title">Quản lý khai báo</h4>
          </template>
          <div class="table-responsive text-left">
            <a-table
              :columns="columns"
              :data-source="listUser"
              :pagination="false"
              :scroll="{ x: 1500, y: 800 }"
            >
              <template #action="item">
                <a-dropdown>
                  <a>Tùy chọn</a>
                  <a-menu slot="overlay">
                    <a-menu-item @click="showModal(item)">
                      <a href="javascript:;">Lịch sử khai báo</a>
                    </a-menu-item>
                  </a-menu>
                </a-dropdown>
              </template>
            </a-table>
            <div class="gutter-example pt-md pagnigation-custom">
              <a-pagination
                v-model="current"
                show-quick-jumper
                :default-current="1"
                :total="total"
                @change="paginate"
              />
            </div>
          </div>
        </card>
      </div>
    </div>
    <a-modal
      v-model="visibleHealthReport"
      :title="`Danh sách khai báo : ${currentUser.fullName}`"
      :width="1080"
      on-ok="handleOk"
    >
      <template slot="footer">
        <a-button key="back" @click="handleCancelHealthReport">
          Trở lại
        </a-button>
      </template>
      <a-layout id="components-layout-demo-fixed-sider">
        <a-layout-sider
          :style="{
            overflow: 'auto',
            height: '100vh',
            left: 0,
            background: '#fff',
            color: '000',
          }"
        >
          <div class="logo" />
          <a-menu theme="light" mode="inline" :default-selected-keys="['4']">
            <a-menu-item
              v-for="item in listHealthReport"
              :key="item.id"
              @click="currentHealthReport = item"
            >
              <a-icon type="safety-certificate" />
              <span class="nav-text">{{
                formatDateTime(item.createdTime, "DD-MM-YYYY hh:mm:ss")
              }}</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
        <a-layout>
          <a-layout-content
            :style="{ margin: '24px 16px 0', overflow: 'initial' }"
          >
            <div
              :style="{
                padding: '24px',
                background: '#fff',
                textAlign: 'center',
              }"
            >
              <a-descriptions
                title="Chi tiết khai báo"
                layout="vertical"
                bordered
              >
                <a-descriptions-item label="Họ và tên">
                  {{ currentUser.fullName }}
                </a-descriptions-item>
                <a-descriptions-item label="Giới tính">
                  {{ formatGender(currentHealthReport.gender) }}
                </a-descriptions-item>
                <a-descriptions-item label="Ngày sinh">
                  {{
                    formatDateTime(
                      currentHealthReport.dateOfBirth,
                      "DD-MM-YYYY"
                    )
                  }}
                </a-descriptions-item>
                <a-descriptions-item label="Điện thoại">
                  {{ currentHealthReport.phoneNumber }}
                </a-descriptions-item>
                <a-descriptions-item label="Số thẻ bảo hiểm y tế">
                  {{ currentHealthReport.healthInsuranceId }}
                </a-descriptions-item>
                <a-descriptions-item label="Email">
                  {{ currentHealthReport.email }}
                  <!-- <a-badge status="processing" text="Running" /> -->
                </a-descriptions-item>
                <a-descriptions-item label="Tỉnh thành">
                  {{ currentHealthReport.provinceName }}
                </a-descriptions-item>
                <a-descriptions-item label="Quận huyện">
                  {{ currentHealthReport.districtName }}
                </a-descriptions-item>
                <a-descriptions-item label="Phường xã">
                  {{ currentHealthReport.wardName }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  :span="3"
                >
                  {{ currentHealthReport.addressDetail }}
                </a-descriptions-item>
               <a-descriptions-item
                  label="Trong vòng 14 ngày qua, Anh/Chị có đến khu vực tỉnh thành phố, quốc gia/vùng lãnh thổ nào (Có thể đi nhiều nơi)"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.contactToPlace) }}
                </a-descriptions-item>
                   <a-descriptions-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.sicking) }}
                </a-descriptions-item>
                 <a-descriptions-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.closeToRiskingPeople) }}
                </a-descriptions-item>
              </a-descriptions>
            </div>
          </a-layout-content>
          <a-layout-footer :style="{ textAlign: 'center' }">
            Ant Design ©2018 Created by Ant UED
          </a-layout-footer>
        </a-layout>
      </a-layout>
    </a-modal>
  </div>
</template>
<script>
import { Card } from "@/components/index";
import HealthReportRepository from "../api/healthReport";
import moment from "moment";
const columns = [
  {
    title: "Stt",
    dataIndex: "index",
    width: 80,
    key: "index",
  },
  {
    title: "Chức vụ",
    dataIndex: "roleName",
    width: 100,
    key: "roleName",
  },
  {
    title: "Họ và Tên",
    dataIndex: "fullName",
    width: 200,
    key: "fullName",
  },
  {
    title: "Lớp",
    dataIndex: "className",
    width: 100,
    key: "className",
  },
  {
    title: "Giới tính",
    dataIndex: "gender",
    width: 140,
    key: "gender",
  },
  {
    title: "Ngày sinh",
    width: 150,
    key: "dob",
    scopedSlots: {
      customRender: "dobCustom",
    },
  },
  {
    title: "Tài khoản",
    dataIndex: "userName",
    width: 150,
    key: "userName",
  },
  {
    title: "Địa chỉ chi tiết",
    dataIndex: "addressDetail",
    width: 200,
    key: "addressDetail",
  },
  {
    title: "Phường / Xã",
    dataIndex: "wardName",
    width: 150,
    key: "wardName",
  },
  {
    title: "Quận / Huyện",
    dataIndex: "districtName",
    width: 150,
    key: "districtName",
  },
  {
    title: "Tỉnh thành",
    dataIndex: "provinceName",
    width: 150,
    key: "provinceName",
  },
  {
    title: "Số điện thoại",
    dataIndex: "phoneNumber",
    width: 150,
    key: "phoneNumber",
  },
  {
    title: "Sđt nguời thân",
    dataIndex: "parentPhoneNumber",
    width: 150,
    key: "parentPhoneNumber",
  },
  {
    title: "Tùy chọn",
    key: "operation",
    fixed: "right",
    width: 100,
    scopedSlots: { customRender: "action" },
  },
];
const columnsHealthReport = [];
const defaultSearchForm = {
  id: undefined,
  fullName: "",
  dob: "2000-01-01",
  gender: 1,
  phoneNumber: "",
  parentPhoneNumber: "",
  provinceCode: undefined,
  districtCode: undefined,
  wardCode: undefined,
  addressDetail: "",
  classID: undefined,
};
export default {
  components: {
    Card,
  },
  data() {
    return {
      columns: columns,
      searchForm: { ...defaultSearchForm },
      listHealthReport: [],
      listUser: [],
      current: 1,
      total: 0,
      totalHealthReport: 0,
      loading: false,
      visibleHealthReport: false,
      currentUser: {},
      currentHealthReport: {},
      columnsHealthReport: columnsHealthReport,
    };
  },
  created() {
    this.getListUser();
  },
  methods: {
    formatYesNo(item){
      if(item){
        return 'Có'
      }
      return 'Không'
    },
    formatGender(gender) {
      if (gender) {
        return "Nam";
      }
      return "Nữ";
    },
    formatDateTime(date, formatter) {
      return moment(date).format(formatter);
    },
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      this.getListUser();
    },
    getListUser() {
      HealthReportRepository.searchUserReport(
        this.searchForm,
        this.current
      ).then((res) => {
        console.log(res, "res");
        this.listUser = res.data.items;
        this.total = res.data.total;
      });
    },
    getListReportByUsername(username) {
      HealthReportRepository.getReportsByUsername(username).then((res) => {
        this.listHealthReport = res.data.data.items;
      });
    },
    showModal(item) {
      this.visibleHealthReport = true;
      this.currentUser = { ...item };
      this.getListReportByUsername(item.userName);
    },
    handleOk(e) {
      this.loading = true;
      setTimeout(() => {
        this.visibleHealthReport = false;
        this.loading = false;
      }, 3000);
    },
    handleCancelHealthReport(e) {
      this.visibleHealthReport = false;
    },
  },
};
</script>
<style>
</style>
