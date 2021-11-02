<template>
  <div class="content">
    <card>
      <template slot="header">
        <h4 class="card-title">THÔNG TIN KHAI BÁO Y TẾ</h4>
      </template>
      <div class="table-responsive text-left">
        <a-form :form="form" @submit="handleSubmit">
          <a-row type="flex">
            <a-col class="gutter-box" flex="1 1 200px">
              <a-form-item
                label="Họ và tên"
                has-feedback
              >
                <a-input
                  v-decorator="[
                    'studentName',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bạn chưa nhập họ và tên',
                        },
                      ],
                    },
                  ]"
                  placeholder="Nhập..."
                >
                </a-input>
              </a-form-item>
            </a-col>
                <a-col class="gutter-box" flex="1 1 200px">
              <a-form-item
                label="CMND/CCCD"
                has-feedback
              >
                <a-input
                  v-decorator="[
                    'verificationId',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bạn chưa nhập CMND/CCCD',
                        },
                      ],
                    },
                  ]"
                  placeholder="Nhập..."
                >
                </a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row type="flex">
            <a-col class="gutter-box" flex="1 1 200px">
              <a-form-item label="Tỉnh thành" has-feedback>
                <a-select
                  @change="onChangeProvince"
                  v-decorator="[
                    'provinceCode',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bạn chưa chọn nơi đi!',
                        },
                      ],
                    },
                  ]"
                  placeholder="Chọn tỉnh"
                >
                  <a-select-option
                    v-for="item in fetchedData.listProvince"
                    :key="item.id"
                    :value="item.code"
                  >
                    {{ item.name }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="gutter-box" flex="1 1 200px">
              <a-form-item label="Quận huyện" has-feedback>
                <a-select
                  @change="onChangeDistrict"
                  v-decorator="[
                    'districtCode',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bạn chưa chọn điểm đi(Quận huyện)',
                        },
                      ],
                    },
                  ]"
                  placeholder="Chọn quận huyện"
                >
                  <a-select-option
                    v-for="item in fetchedData.listDistrict"
                    :key="item.id"
                    :value="item.districtCode"
                  >
                    {{ item.districtName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="gutter-box" flex="1 1 200px">
              <a-form-item label="Phường xã" has-feedback>
                <a-select
                  v-decorator="[
                    'wardCode',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bạn chưa chọn điểm đi(Phường xã)',
                        },
                      ],
                    },
                  ]"
                  placeholder="Chọn phường xã"
                >
                  <a-select-option
                    v-for="item in fetchedData.listWard"
                    :key="item.id"
                    :value="item.wardCode"
                  >
                    {{ item.wardName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="gutter-box" flex="1 1 200px">
              <a-form-item
                label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                has-feedback
              >
                <a-input
                  v-decorator="[
                    'addressDetail',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bạn chưa nhập địa chỉ',
                        },
                      ],
                    },
                  ]"
                  placeholder="Nhập..."
                >
                </a-input>
              </a-form-item>
            </a-col>
          </a-row>

          <a-form-item v-bind="tailFormItemLayout">
            <a-checkbox
              v-decorator="['agreement', { valuePropName: 'checked' }]"
            >
              I have read the
              <a href=""> agreement </a>
            </a-checkbox>
          </a-form-item>
          <a-form-item v-bind="tailFormItemLayout">
            <a-button type="primary" html-type="submit"> Register </a-button>
          </a-form-item>
        </a-form>
      </div>
    </card>
  </div>
</template>
<script>
import { Card } from "@/components/index";
import LocationRepository from "../api/location.js";

export default {
  components: {
    Card,
  },
  data() {
    return {
      fetchedData: {
        listProvince: [],
        listDistrict: [],
        listWard: [],
      },
      confirmDirty: false,
      autoCompleteResult: [],
      // formItemLayout: {
      //   labelCol: {
      //     xs: { span: 24 },
      //     sm: { span: 8 },
      //   },
      //   wrapperCol: {
      //     xs: { span: 24 },
      //     sm: { span: 16 },
      //   },
      // },
      tailFormItemLayout: {
        wrapperCol: {
          xs: {
            span: 24,
            offset: 0,
          },
          sm: {
            span: 16,
            offset: 8,
          },
        },
      },
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "register" });
  },
  created() {
    this.fetchProvince();
  },
  mounted: {},
  methods: {
    onChangeProvince(value) {
      this.fetchedData.listDistrict = [];
      this.fetchedData.listWard = [];
      this.form.resetFields("districtCode");
      this.form.resetFields("wardCode");
      this.fetchDistrict(value);
    },
    onChangeDistrict(value) {
      this.fetchedData.listWard = [];
      this.form.resetFields("wardCode");
      this.fetchWard(value);
    },
    fetchProvince() {
      LocationRepository.getAllProvince().then((res) => {
        this.fetchedData.listProvince = res.data.data;
      });
    },
    fetchDistrict(value) {
      LocationRepository.getDistrictInProvince(value, "").then((res) => {
        this.fetchedData.listDistrict = res.data.data;
      });
    },
    fetchWard(value) {
      LocationRepository.getWardInDistrict(value, "").then((res) => {
        this.fetchedData.listWard = res.data.data;
      });
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
        }
      });
    },
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    compareToFirstPassword(rule, value, callback) {
      const form = this.form;
      if (value && value !== form.getFieldValue("password")) {
        callback("Two passwords that you enter is inconsistent!");
      } else {
        callback();
      }
    },
    validateToNextPassword(rule, value, callback) {
      const form = this.form;
      if (value && this.confirmDirty) {
        form.validateFields(["confirm"], { force: true });
      }
      callback();
    },
    handleWebsiteChange(value) {
      let autoCompleteResult;
      if (!value) {
        autoCompleteResult = [];
      } else {
        autoCompleteResult = [".com", ".org", ".net"].map(
          (domain) => `${value}${domain}`
        );
      }
      this.autoCompleteResult = autoCompleteResult;
    },
  },
};
</script>
<style scoped>
#components-form-demo-validate-other .dropbox {
  height: 180px;
  line-height: 1.5;
}
.gutter-box {
  padding-right: 10px;
}
</style>
